package com.comp2042;

/**
 * Data Transfer Object containing results after a row clearing operation.
 * Used to pass information from the Logic layer to the Controller/View.
 */
public final class ClearRow {

    private final int linesRemoved;
    private final int[][] newMatrix;
    private final int scoreBonus;

    /**
     * Constructs a new ClearRow result.
     *
     * @param linesRemoved The number of full lines cleared.
     * @param newMatrix The updated board matrix after lines were removed.
     * @param scoreBonus The calculated score to award for this action.
     */
    public ClearRow(int linesRemoved, int[][] newMatrix, int scoreBonus) {
        this.linesRemoved = linesRemoved;
        this.newMatrix = newMatrix;
        this.scoreBonus = scoreBonus;
    }

    /**
     * Gets the count of lines removed.
     * @return The number of lines cleared.
     */
    public int getLinesRemoved() {
        return linesRemoved;
    }

    /**
     * Gets the updated grid matrix.
     * @return A copy of the 2D integer array representing the board.
     */
    public int[][] getNewMatrix() {
        return MatrixOperations.copy(newMatrix);
    }

    /**
     * Gets the score bonus earned from this clear operation.
     * @return The score bonus.
     */
    public int getScoreBonus() {
        return scoreBonus;
    }
}
