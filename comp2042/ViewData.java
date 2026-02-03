package com.comp2042;
 
/**
 * Immutable Data Transfer Object (DTO) containing a snapshot of the game state
 * required for rendering the view.
 */
public final class ViewData {

    private final int[][] brickData;
    private final int xPosition;
    private final int yPosition;
    private final int[][] nextBrickData;
    private final int[][] boardMatrix;

    /**
     * Constructs a new ViewData snapshot.
     *
     * @param brickData     Matrix of the current falling brick.
     * @param xPosition     Current X coordinate of the brick.
     * @param yPosition     Current Y coordinate of the brick.
     * @param nextBrickData Matrix of the next brick in the queue.
     * @param boardMatrix   The full state of the board grid.
     */
    public ViewData(int[][] brickData, int xPosition, int yPosition, int[][] nextBrickData, int[][] boardMatrix) {
        this.brickData = brickData;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.nextBrickData = nextBrickData;
        this.boardMatrix = boardMatrix;
    }

    /**
     * Returns a copy of the current brick matrix.
     * @return 2D integer array.
     */
    public int[][] getBrickData() { return MatrixOperations.copy(brickData); }

    /**
     * Returns the X position of the current brick.
     * @return X coordinate.
     */
    public int getxPosition() { return xPosition; }

    /**
     * Returns the Y position of the current brick.
     * @return Y coordinate.
     */
    public int getyPosition() { return yPosition; }

    /**
     * Returns a copy of the next brick's matrix (for preview).
     * @return 2D integer array.
     */
    public int[][] getNextBrickData() { return MatrixOperations.copy(nextBrickData); }

    /**
     * Returns a copy of the complete board matrix.
     * @return 2D integer array.
     */
    public int[][] getBoardMatrix() { return MatrixOperations.copy(boardMatrix); }
}
