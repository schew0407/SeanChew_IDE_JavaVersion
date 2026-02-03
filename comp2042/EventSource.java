package com.comp2042;

/**
 * Enumeration representing the source of a game event.
 * Used to distinguish between user actions and automated system events.
 */
public enum EventSource {
    /** Event triggered by player input (e.g., keyboard press). */
    USER,

    /** Event triggered by the game loop thread (e.g., automatic gravity). */
    THREAD
}
