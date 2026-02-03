package com.comp2042;

/**
 * Interface defining the contract for handling game input events.
 * The View uses this to notify the Controller of user actions.
 */
public interface InputEventListener {

    /**
     * Called when a 'Down' action is triggered.
     * @param event The event details.
     * @return The result of the down movement (cleared rows, new view data).
     */
    DownData onDownEvent(MoveEvent event);

    /**
     * Called when a 'Left' action is triggered.
     * @param event The event details.
     * @return The updated view data.
     */
    ViewData onLeftEvent(MoveEvent event);

    /**
     * Called when a 'Right' action is triggered.
     * @param event The event details.
     * @return The updated view data.
     */
    ViewData onRightEvent(MoveEvent event);

    /**
     * Called when a 'Rotate' action is triggered.
     * @param event The event details.
     * @return The updated view data.
     */
    ViewData onRotateEvent(MoveEvent event);

    /**
     * Called when a request to start a new game is received.
     */
    void createNewGame();
}