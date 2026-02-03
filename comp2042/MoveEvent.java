package com.comp2042;

/**
 * Represents a movement request event within the game.
 * Encapsulates the type of movement and the source that triggered it.
 */
public final class MoveEvent {
    private final EventType eventType;
    private final EventSource eventSource;

    /**
     * Constructs a new MoveEvent.
     *
     * @param eventType   The type of action (e.g., DOWN, LEFT).
     * @param eventSource The source of the action (e.g., USER, THREAD).
     */
    public MoveEvent(EventType eventType, EventSource eventSource) {
        this.eventType = eventType;
        this.eventSource = eventSource;
    }

    /**
     * Gets the type of the event.
     * @return {@link EventType}
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Gets the source of the event.
     * @return {@link EventSource}
     */
    public EventSource getEventSource() {
        return eventSource;
    }
}