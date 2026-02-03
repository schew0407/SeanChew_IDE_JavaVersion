package com.comp2042;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Manages the game score using JavaFX Properties.
 * This allows the UI to automatically update whenever the score changes.
 */
public final class Score {

    private final IntegerProperty score = new SimpleIntegerProperty(0);

    /**
     * Retrieves the observable integer property for the score.
     * Used for data binding in the View.
     *
     * @return The {@link IntegerProperty} representing the score.
     */
    public IntegerProperty scoreProperty() {
        return score;
    }

    /**
     * Adds the specified amount to the current score.
     *
     * @param i The value to add.
     */
    public void add(int i){
        score.setValue(score.getValue() + i);
    }

    /**
     * Resets the score to zero.
     */
    public void reset() {
        score.setValue(0);
    }
}