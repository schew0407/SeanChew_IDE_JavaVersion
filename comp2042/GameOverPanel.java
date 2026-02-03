package com.comp2042;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * A custom UI component representing the Game Over screen.
 * Displays a "GAME OVER" label in the center of the pane.
 */
public class GameOverPanel extends BorderPane {

    /**
     * Constructs the Game Over panel and sets up the label styling.
     */
    public GameOverPanel() {
        final Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.getStyleClass().add("gameOverStyle");
        setCenter(gameOverLabel);
    }
}