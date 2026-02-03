package com.comp2042;

/**
 * Interface defining the core logic and state management of the Tetris board.
 * Implementing classes manage the grid matrix, brick movements, and game rules.
 */
public interface Board {

    /**
     * Attempts to move the current active brick down by one cell.
     * @return true if the move was successful, false if the brick was blocked.
     */
    boolean moveBrickDown();

    /**
     * Attempts to move the current active brick one cell to the left.
     * @return true if the move was successful.
     */
    boolean moveBrickLeft();

    /**
     * Attempts to move the current active brick one cell to the right.
     * @return true if the move was successful.
     */
    boolean moveBrickRight();

    /**
     * Attempts to rotate the current active brick 90 degrees.
     * @return true if the rotation was valid and successful.
     */
    boolean rotateLeftBrick();

    /**
     * Spawns a new active brick at the top of the board.
     * @return true if the brick was created successfully, false if there is no space (Game Over).
     */
    boolean createNewBrick();

    /**
     * Retrieves the current state of the board grid.
     * @return A 2D integer array representing the board layout.
     */
    int[][] getBoardMatrix();

    /**
     * Retrieves a snapshot of data required for the View to render the game.
     * @return A {@link ViewData} object containing view-relevant state.
     */
    ViewData getViewData();

    /**
     * Locks the current active brick into the background grid (called when it can no longer move).
     */
    void mergeBrickToBackground();

    /**
     * Scans the board for full rows, removes them, and shifts upper rows down.
     * @return A {@link ClearRow} object containing details about the operation (lines cleared, score, new matrix).
     */
    ClearRow clearRows();

    /**
     * Gets the score object associated with the current game.
     * @return The {@link Score} object.
     */
    Score getScore();

    /**
     * Resets the board state for a new game session.
     */
    void newGame();
}
