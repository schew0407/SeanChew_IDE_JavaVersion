package com.comp2042;

import com.comp2042.logic.bricks.Brick;

/**
 * Helper class responsible for managing the rotation state of a Brick.
 * It handles the cycling through different shape orientations.
 */
public class BrickRotator {

    private Brick brick;
    private int currentShape = 0;

    /**
     * Previews the next rotation shape without altering the current state.
     *
     * @return A {@link NextShapeInfo} object containing the matrix of the next rotation and its index.
     */
    public NextShapeInfo getNextShape() {
        int nextShape = currentShape;
        nextShape = (++nextShape) % brick.getShapeMatrix().size();
        return new NextShapeInfo(brick.getShapeMatrix().get(nextShape), nextShape);
    }

    /**
     * Retrieves the matrix of the brick's current orientation.
     *
     * @return A 2D integer array representing the current shape.
     */
    public int[][] getCurrentShape() {
        return brick.getShapeMatrix().get(currentShape);
    }

    /**
     * Sets the current orientation index explicitly.
     *
     * @param currentShape The index of the shape orientation.
     */
    public void setCurrentShape(int currentShape) {
        this.currentShape = currentShape;
    }

    /**
     * Assigns a new Brick to this rotator and resets orientation to 0.
     *
     * @param brick The {@link Brick} entity to manage.
     */
    public void setBrick(Brick brick) {
        this.brick = brick;
        currentShape = 0;
    }
}