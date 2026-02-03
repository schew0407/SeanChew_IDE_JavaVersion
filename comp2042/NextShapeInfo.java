package com.comp2042;

/**
 * Data Transfer Object holding information about the next possible rotation state of a brick.
 * Used by the rotator to validate if a rotation is possible before applying it.
 */
public final class NextShapeInfo {

    private final int[][] shape;
    private final int position;

    /**
     * Constructs a NextShapeInfo object.
     *
     * @param shape    The matrix representing the shape of the brick in the next rotation.
     * @param position The index of this rotation state.
     */
    public NextShapeInfo(final int[][] shape, final int position) {
        this.shape = shape;
        this.position = position;
    }

    /**
     * Gets the shape matrix of the next rotation.
     * @return A copy of the 2D integer array.
     */
    public int[][] getShape() {
        return MatrixOperations.copy(shape);
    }

    /**
     * Gets the position index of the next rotation.
     * @return The integer index.
     */
    public int getPosition() {
        return position;
    }
}