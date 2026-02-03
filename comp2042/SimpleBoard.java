package com.comp2042;

import com.comp2042.logic.bricks.Brick;
import com.comp2042.logic.bricks.BrickGenerator;
import com.comp2042.logic.bricks.RandomBrickGenerator;

/**
 * The concrete implementation of the {@link Board} interface.
 * Manages the game grid state, brick generation, movement logic, and scoring.
 */
public class SimpleBoard implements Board {

    private final int width;
    private final int height;
    private final BrickGenerator brickGenerator;
    private final BrickRotator brickRotator;
    private int[][] currentGameMatrix;

    // Coordinates of the current active brick
    private int currentX;
    private int currentY;

    private final Score score;

    /**
     * Constructs a new SimpleBoard with specific dimensions.
     *
     * @param width  The width of the board (number of columns).
     * @param height The height of the board (number of rows).
     */
    public SimpleBoard(int width, int height) {
        this.width = width;
        this.height = height;
        currentGameMatrix = new int[width][height];
        brickGenerator = new RandomBrickGenerator();
        brickRotator = new BrickRotator();
        score = new Score();
    }

    /**
     * Attempts to move the current brick down. Checks for collisions.
     *
     * @return true if the move was successful, false if blocked.
     */
    @Override
    public boolean moveBrickDown() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        int nextY = currentY + 1;

        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), currentX, nextY);
        if (conflict) {
            return false;
        } else {
            currentY = nextY;
            return true;
        }
    }

    /**
     * Attempts to move the current brick left. Checks for collisions.
     *
     * @return true if the move was successful.
     */
    @Override
    public boolean moveBrickLeft() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        int nextX = currentX - 1;

        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), nextX, currentY);
        if (conflict) {
            return false;
        } else {
            currentX = nextX;
            return true;
        }
    }

    /**
     * Attempts to move the current brick right. Checks for collisions.
     *
     * @return true if the move was successful.
     */
    @Override
    public boolean moveBrickRight() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        int nextX = currentX + 1;

        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), nextX, currentY);
        if (conflict) {
            return false;
        } else {
            currentX = nextX;
            return true;
        }
    }

    /**
     * Attempts to rotate the current brick. Checks for collisions with the new shape.
     *
     * @return true if rotation was successful.
     */
    @Override
    public boolean rotateLeftBrick() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        NextShapeInfo nextShape = brickRotator.getNextShape();
        boolean conflict = MatrixOperations.intersect(currentMatrix, nextShape.getShape(), currentX, currentY);
        if (conflict) {
            return false;
        } else {
            brickRotator.setCurrentShape(nextShape.getPosition());
            return true;
        }
    }

    /**
     * Spawns a new brick at the top of the board.
     *
     * @return true if the brick was placed successfully, false if immediate collision (Game Over).
     */
    @Override
    public boolean createNewBrick() {
        Brick currentBrick = brickGenerator.getBrick();
        brickRotator.setBrick(currentBrick);
        currentX = 4; // Reset Position
        currentY = 2; // Start slightly lower so it's visible
        return MatrixOperations.intersect(currentGameMatrix, brickRotator.getCurrentShape(), currentX, currentY);
    }

    /**
     * Returns the board matrix merged with the current floating brick.
     *
     * @return A snapshot of the 2D board array for display.
     */
    @Override
    public int[][] getBoardMatrix() {
        return MatrixOperations.merge(currentGameMatrix, brickRotator.getCurrentShape(), currentX, currentY);
    }

    /**
     * Generates a comprehensive view data object for the UI.
     *
     * @return {@link ViewData} containing all necessary state info.
     */
    @Override
    public ViewData getViewData() {
        int[][] combined = MatrixOperations.merge(currentGameMatrix, brickRotator.getCurrentShape(), currentX, currentY);

        return new ViewData(
                brickRotator.getCurrentShape(),
                currentX,
                currentY,
                brickGenerator.getNextBrick().getShapeMatrix().get(0),
                combined
        );
    }

    /**
     * Locks the current floating brick into the background matrix.
     */
    @Override
    public void mergeBrickToBackground() {
        currentGameMatrix = MatrixOperations.merge(currentGameMatrix, brickRotator.getCurrentShape(), currentX, currentY);
    }

    /**
     * Checks for full rows and clears them.
     *
     * @return {@link ClearRow} results containing the lines removed and score info.
     */
    @Override
    public ClearRow clearRows() {
        ClearRow clearRow = MatrixOperations.checkRemoving(currentGameMatrix);
        currentGameMatrix = clearRow.getNewMatrix();
        return clearRow;
    }

    /**
     * Gets the Score object for this board.
     * @return The {@link Score}.
     */
    @Override
    public Score getScore() {
        return score;
    }

    /**
     * Resets the board and score for a new game.
     */
    @Override
    public void newGame() {
        currentGameMatrix = new int[width][height];
        score.reset();
        createNewBrick();
    }
}
