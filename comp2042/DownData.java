package com.comp2042;

/**
 * Wrapper class containing data resulting from a 'Down' event.
 * It encapsulates row clearing information and the current view state.
 */
public final class DownData {
    private final ClearRow clearRow;
    private final ViewData viewData;

    /**
     * Constructs a new DownData object.
     *
     * @param clearRow Information regarding any rows cleared during the move (can be null).
     * @param viewData The current state of the view (matrix and next brick).
     */
    public DownData(ClearRow clearRow, ViewData viewData) {
        this.clearRow = clearRow;
        this.viewData = viewData;
    }

    /**
     * Returns the row clearing details.
     * @return The {@link ClearRow} object, or null if no clearing occurred.
     */
    public ClearRow getClearRow() {
        return clearRow;
    }

    /**
     * Returns the view data for rendering.
     * @return The {@link ViewData} object.
     */
    public ViewData getViewData() {
        return viewData;
    }
}