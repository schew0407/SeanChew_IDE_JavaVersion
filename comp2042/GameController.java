package com.comp2042;

/**
 * The main controller for the Game.
 * Bridges the Game Logic (Board) and the UI (GuiController).
 * Implements {@link InputEventListener} to handle game events triggered by the UI.
 */
public class GameController implements InputEventListener {

    private Board board = new SimpleBoard(25, 10);
    private final GuiController viewGuiController;

    /**
     * Constructs a new GameController and initializes the game link between Logic and View.
     *
     * @param c The {@link GuiController} instance managing the JavaFX view.
     */
    public GameController(GuiController c) {
        viewGuiController = c;
        board.createNewBrick();
        viewGuiController.setEventListener(this);
        viewGuiController.initGameView(board.getBoardMatrix(), board.getViewData());
        viewGuiController.bindScore(board.getScore().scoreProperty());
    }

    /**
     * Handles the logic when a 'Down' event occurs (either by user or game loop).
     * Checks if the brick can move, merges it if not, checks for cleared rows,
     * updates score, and checks for Game Over conditions.
     *
     * @param event The move event details.
     * @return A {@link DownData} object containing the result of the move.
     */
    @Override
    public DownData onDownEvent(MoveEvent event) {
        boolean canMove = board.moveBrickDown();
        ClearRow clearRow = null;
        if (!canMove) {
            board.mergeBrickToBackground();
            clearRow = board.clearRows();
            if (clearRow.getLinesRemoved() > 0) {
                board.getScore().add(clearRow.getScoreBonus());
            }
            // Try to create a new brick; if false, board is full
            if (board.createNewBrick()) {
                viewGuiController.gameOver();
            }

            viewGuiController.refreshGameBackground(board.getBoardMatrix());

        } else {
            // Add tiny score for manual soft drop
            if (event.getEventSource() == EventSource.USER) {
                board.getScore().add(1);
            }
        }
        return new DownData(clearRow, board.getViewData());
    }

    /**
     * Handles the logic for a 'Left' movement request.
     *
     * @param event The move event details.
     * @return The updated {@link ViewData}.
     */
    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        board.moveBrickLeft();
        return board.getViewData();
    }

    /**
     * Handles the logic for a 'Right' movement request.
     *
     * @param event The move event details.
     * @return The updated {@link ViewData}.
     */
    @Override
    public ViewData onRightEvent(MoveEvent event) {
        board.moveBrickRight();
        return board.getViewData();
    }

    /**
     * Handles the logic for a 'Rotate' movement request.
     *
     * @param event The move event details.
     * @return The updated {@link ViewData}.
     */
    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateLeftBrick();
        return board.getViewData();
    }

    /**
     * Resets the game state to start a new game session.
     */
    @Override
    public void createNewGame() {
        board.newGame();
        viewGuiController.refreshGameBackground(board.getBoardMatrix());
    }
}