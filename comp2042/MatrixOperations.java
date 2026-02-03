package com.comp2042;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class providing static methods for 2D matrix manipulations.
 * Handles collision detection, grid merging, deep copying, and row clearing logic.
 */
public class MatrixOperations {

    // We don't want to instantiate this utility class
    private MatrixOperations(){
    }

    /**
     * Checks if a brick collides with existing blocks in the matrix or the board boundaries.
     *
     * @param matrix The background board matrix.
     * @param brick  The matrix representing the current brick shape.
     * @param x      The x-coordinate (column) of the brick's top-left corner.
     * @param y      The y-coordinate (row) of the brick's top-left corner.
     * @return true if a collision is detected or if the brick is out of bounds; false otherwise.
     */
    public static boolean intersect(final int[][] matrix, final int[][] brick, int x, int y) {
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
                int targetX = x + i;
                int targetY = y + j;
                if (brick[j][i] != 0 && (checkOutOfBound(matrix, targetX, targetY) || matrix[targetY][targetX] != 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Helper method to check if a specific coordinate is outside the matrix dimensions.
     *
     * @param matrix  The reference matrix.
     * @param targetX The target X coordinate.
     * @param targetY The target Y coordinate.
     * @return true if the coordinates are out of bounds.
     */
    private static boolean checkOutOfBound(int[][] matrix, int targetX, int targetY) {
        boolean returnValue = true;
        if (targetX >= 0 && targetY < matrix.length && targetX < matrix[targetY].length) {
            returnValue = false;
        }
        return returnValue;
    }

    /**
     * Creates a deep copy of a 2D integer array.
     *
     * @param original The array to copy.
     * @return A new 2D array containing the same values as the original.
     */
    public static int[][] copy(int[][] original) {
        int[][] myInt = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            int[] aMatrix = original[i];
            int aLength = aMatrix.length;
            myInt[i] = new int[aLength];
            System.arraycopy(aMatrix, 0, myInt[i], 0, aLength);
        }
        return myInt;
    }

    /**
     * Merges a brick onto the background matrix.
     * Used for rendering the current frame or locking a brick in place.
     *
     * @param filledFields The background matrix (static blocks).
     * @param brick        The matrix of the brick to merge.
     * @param x            The x-coordinate of the brick.
     * @param y            The y-coordinate of the brick.
     * @return A new 2D matrix representing the combination of the background and the brick.
     */
    public static int[][] merge(int[][] filledFields, int[][] brick, int x, int y) {
        int[][] copy = copy(filledFields);
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
                int targetX = x + i;
                int targetY = y + j;
                if (brick[j][i] != 0) {
                    copy[targetY][targetX] = brick[j][i];
                }
            }
        }
        return copy;
    }

    /**
     * Scans the matrix for full rows, removes them, and shifts the upper rows down.
     * Calculates the score bonus based on the number of cleared rows.
     *
     * @param matrix The board matrix to check.
     * @return A {@link ClearRow} object containing the number of lines removed, the new matrix, and the score.
     */
    public static ClearRow checkRemoving(final int[][] matrix) {
        int[][] tmp = new int[matrix.length][matrix[0].length];
        Deque<int[]> newRows = new ArrayDeque<>();
        List<Integer> clearedRows = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {
            int[] tmpRow = new int[matrix[i].length];
            boolean rowToClear = true;
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    rowToClear = false;
                }
                tmpRow[j] = matrix[i][j];
            }
            if (rowToClear) {
                clearedRows.add(i);
            } else {
                newRows.add(tmpRow);
            }
        }
        for (int i = matrix.length - 1; i >= 0; i--) {
            int[] row = newRows.pollLast();
            if (row != null) {
                tmp[i] = row;
            } else {
                break;
            }
        }
        int scoreBonus = 50 * clearedRows.size() * clearedRows.size();
        return new ClearRow(clearedRows.size(), tmp, scoreBonus);
    }

    /**
     * Creates a deep copy of a list of 2D arrays.
     *
     * @param list The list of matrices to copy.
     * @return A new list containing deep copies of the matrices.
     */
    public static List<int[][]> deepCopyList(List<int[][]> list){
        return list.stream().map(MatrixOperations::copy).collect(Collectors.toList());
    }
}