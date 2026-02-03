package com.comp2042;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The JavaFX Controller class responsible for managing the Graphical User Interface.
 * It handles scene initialization, key input, the game loop timeline, and rendering the board.
 */
public class GuiController implements Initializable {

    @FXML private GridPane gamePanel;
    @FXML private GridPane brickPanel; // This is the "Next Brick" panel
    @FXML private Group groupNotification;
    @FXML private GameOverPanel gameOverPanel;
    @FXML private Text scoreText;

    private Rectangle[][] displayMatrix;
    private Rectangle[][] nextBrickRectangles; // For the preview

    private InputEventListener eventListener;
    private Timeline timeLine;

    private final BooleanProperty isPause = new SimpleBooleanProperty(false);
    private final BooleanProperty isGameOver = new SimpleBooleanProperty(false);

    /**
     * Initializes the controller class. Automatically called after the FXML file has been loaded.
     * Sets up focus, key listeners, and initial visibility states.
     *
     * @param location  The location used to resolve relative paths for the root object.
     * @param resources The resources used to localize the root object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Focus handling
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();

        // Key Listener
        gamePanel.setOnKeyPressed(this::handleKeyPressed);

        // Init Game Over Panel
        gameOverPanel.setVisible(false);
    }

    /**
     * Handles keyboard input from the user.
     * Maps keys (Arrows, WASD) to game actions (Move, Rotate) and system actions (Pause, New Game).
     *
     * @param keyEvent The key event triggered by the user.
     */
    private void handleKeyPressed(KeyEvent keyEvent) {
        if (isPause.get() || isGameOver.get()) {
            if (keyEvent.getCode() == KeyCode.N) {
                newGame(null);
            }
            return;
        }

        switch (keyEvent.getCode()) {
            case LEFT, A -> refreshBrick(eventListener.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER)));
            case RIGHT, D -> refreshBrick(eventListener.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER)));
            case UP, W -> refreshBrick(eventListener.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER)));
            case DOWN, S -> moveDown(new MoveEvent(EventType.DOWN, EventSource.USER));
            case P -> togglePause();
        }
        keyEvent.consume();
    }

    /**
     * Initializes the graphical representation of the game board and starts the game loop.
     *
     * @param boardMatrix The initial state of the logical board.
     * @param viewData    Initial view data for setup.
     */
    public void initGameView(int[][] boardMatrix, ViewData viewData) {
        // 1. Initialize Main Board
        gamePanel.getChildren().clear();
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];

        for (int i = 2; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle r = new Rectangle(Config.BRICK_SIZE, Config.BRICK_SIZE);
                r.setFill(Color.TRANSPARENT);
                displayMatrix[i][j] = r;
                gamePanel.add(r, j, i - 2);
            }
        }

        // 2. Initialize Next Brick Panel
        brickPanel.getChildren().clear();
        nextBrickRectangles = new Rectangle[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Rectangle r = new Rectangle(Config.BRICK_SIZE, Config.BRICK_SIZE);
                r.setFill(Color.TRANSPARENT);
                nextBrickRectangles[i][j] = r;
                brickPanel.add(r, j, i);
            }
        }

        // 3. Start Game Loop
        timeLine = new Timeline(new KeyFrame(
                Duration.millis(Config.MOVE_DOWN_SPEED),
                ae -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))
        ));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
    }

    /**
     * Updates the visuals for the active brick and next brick preview.
     * @param viewData The data needed to render the updates.
     */
    private void refreshBrick(ViewData viewData) {
        drawBoard(viewData);
        drawNextBrick(viewData);
    }

    /**
     * Placeholder for drawing the active falling brick.
     * @param viewData Current view data.
     */
    private void drawBoard(ViewData viewData) {
        // Logic to draw the falling piece could go here
    }

    /**
     * Renders the "Next Brick" preview in the side panel.
     * @param viewData Data containing the next brick's shape matrix.
     */
    private void drawNextBrick(ViewData viewData) {
        int[][] nextBrick = viewData.getNextBrickData();
        for (int i = 0; i < nextBrick.length; i++) {
            for (int j = 0; j < nextBrick[i].length; j++) {
                nextBrickRectangles[i][j].setFill(Config.getPaintForId(nextBrick[j][i])); // Transposed read
            }
        }
    }

    /**
     * Refreshes the main game board grid colors based on the logical matrix.
     * @param board The 2D integer array representing the board state.
     */
    public void refreshGameBackground(int[][] board) {
        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                displayMatrix[i][j].setFill(Config.getPaintForId(board[i][j]));
            }
        }
    }

    /**
     * Processes the "Move Down" action, updates the UI, handles scoring and notifications.
     * @param event The move event (User or Thread generated).
     */
    private void moveDown(MoveEvent event) {
        if (isPause.get() || isGameOver.get()) return;

        DownData downData = eventListener.onDownEvent(event);

        // Handle Line Clears
        if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0) {
            NotificationPanel notificationPanel = new NotificationPanel("+" + downData.getClearRow().getScoreBonus());
            groupNotification.getChildren().add(notificationPanel);
            notificationPanel.showScore(groupNotification.getChildren());
        }

        // Update UI
        refreshGameBackground(downData.getViewData().getBoardMatrix());
        drawNextBrick(downData.getViewData());
    }

    /**
     * Sets the event listener to handle input logic callbacks.
     * @param eventListener The listener implementation (usually the GameController).
     */
    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
    }

    /**
     * Binds the score property to the UI label.
     * @param integerProperty The observable score property.
     */
    public void bindScore(IntegerProperty integerProperty) {
        // scoreText.textProperty().bind(integerProperty.asString());
    }

    /**
     * Stops the game loop and displays the Game Over screen.
     */
    public void gameOver() {
        timeLine.stop();
        gameOverPanel.setVisible(true);
        isGameOver.set(true);
    }

    /**
     * Resets the UI and logic for a new game.
     * @param actionEvent The event that triggered the new game (can be null).
     */
    public void newGame(ActionEvent actionEvent) {
        timeLine.stop();
        gameOverPanel.setVisible(false);
        eventListener.createNewGame();
        gamePanel.requestFocus();
        timeLine.play();
        isPause.set(false);
        isGameOver.set(false);
    }

    /**
     * Toggles the game pause state. Stops or Resumes the timeline.
     */
    private void togglePause() {
        if (isPause.get()) {
            isPause.set(false);
            timeLine.play();
        } else {
            isPause.set(true);
            timeLine.stop();
        }
    }
}