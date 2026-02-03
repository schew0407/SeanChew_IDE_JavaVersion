package com.comp2042;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Global configuration settings for the Tetris game.
 * Contains constants for screen dimensions, board size, animation speed,
 * and visual styling logic.
 */
public class Config {

    /** Width of the game window in pixels. */
    public static final int WIDTH = 300;

    /** Height of the game window in pixels. */
    public static final int HEIGHT = 510;

    /** Size of each individual block (brick) in pixels. */
    public static final int BRICK_SIZE = 20;

    /** Width of the logical game board (number of columns). */
    public static final int BOARD_WIDTH = 10;

    /** Height of the logical game board (number of rows). */
    public static final int BOARD_HEIGHT = 20;

    /** Speed of the game loop tick (animation delay) in milliseconds. */
    public static final double MOVE_DOWN_SPEED = 400;

    /**
     * Maps a numerical Brick ID to a specific JavaFX Paint color.
     *
     * @param id The integer identifier of the brick type.
     * @return The {@link Paint} color associated with the given ID.
     */
    public static Paint getPaintForId(int id) {
        return switch (id) {
            case 0 -> Color.TRANSPARENT;
            case 1 -> Color.AQUA;       // IBrick
            case 2 -> Color.BLUEVIOLET; // JBrick
            case 3 -> Color.DARKGREEN;  // LBrick
            case 4 -> Color.YELLOW;     // OBrick
            case 5 -> Color.RED;        // SBrick
            case 6 -> Color.BEIGE;      // TBrick
            case 7 -> Color.BURLYWOOD;  // ZBrick
            default -> Color.WHITE;
        };
    }
}
