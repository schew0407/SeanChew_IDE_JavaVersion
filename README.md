# Tetris Refactoring & Extension Project

**Student Name:** \[Sean Chew Jiet Lun\]\
**Student ID:** \[20505065\]

## GitHub Repository

\[https://github.com/schew0407/SeanChew_IDE_JavaVersion]\
*(Note: Per assignment instructions, this link is required for grading
the Git component.)*

## Compilation Instructions

This project uses Maven and Java 23. To compile and run the application:

1.  Ensure **Java JDK 23** and **Maven** are correctly installed and
    configured.
2.  Open a terminal (or Command Prompt) in the root directory of the
    project.
3.  Execute the following command to clean, compile, and run the game:
    `bash     ./mvnw clean javafx:run` *(On Windows Command Prompt, use
    `.\mvnw clean javafx:run`)*

## Implemented and Working Properly

The following features have been successfully implemented and tested:

-   **Next Brick Preview:** The side panel now correctly displays the
    upcoming Tetromino, allowing the player to plan their moves
    strategically. This fixes the issue where the `NextShapeInfo`
    existed in the logic but was not visualized.
-   **Pause Functionality:** A pause feature has been added. Pressing
    the **'P'** key toggles the game state between paused and running,
    freezing the timeline and input handling.
-   **Code Refactoring (Configuration):** A new `Config` class was
    introduced to centralize "magic numbers" (such as screen dimensions,
    colors, and animation speeds), improving code readability and
    maintainability.
-   **Legacy Code Modernization:** Removed strict dependencies on
    `java.awt.Point` and replaced them with primitive coordinates to
    decouple the logic from the AWT library and prevent potential
    conflicts with JavaFX.

## Implemented but Not Working Properly

-   **N/A:** All features currently implemented in the codebase are
    functioning as expected without known crashing bugs.

## Features Not Implemented

-   **High Score Persistence:** While the scoring system works during a
    session, writing high scores to a persistent file/database was not
    implemented due to time constraints.
-   **Advanced Leveling:** The game currently runs at a constant speed
    defined in `Config`. An adaptive difficulty system (speed increasing
    with score) was considered but not fully implemented.

## New Java Classes

-   **`com.comp2042.Config`**:
    -   **Location:** `src/main/java/com/comp2042/Config.java`
    -   **Purpose:** Stores global constants for the game (resolution,
        grid size, color mappings, game speed). This eliminates
        hardcoded values scattered throughout the application.

## Modified Java Classes

-   **`com.comp2042.GuiController`**:
    -   **Changes:**
        -   Updated `initGameView` to initialize the "Next Brick" grid.
        -   Added `drawNextBrick` method to render the preview.
        -   Modified `handleKeyPressed` to include the Pause ('P')
            toggle.
        -   Replaced hardcoded color switches with calls to
            `Config.getPaintForId()`.
    -   **Reason:** To support new gameplay features and clean up the
        view logic.
-   **`com.comp2042.SimpleBoard`**:
    -   **Changes:**
        -   Removed `import java.awt.Point`.
        -   Replaced `Point` objects with primitive `int x, int y`
            variables for tracking the current brick position.
        -   Updated `getViewData` to merge the falling brick into the
            board matrix before sending it to the view.
    -   **Reason:** To remove legacy AWT dependencies and ensure the
        View receives the complete game state for rendering.
-   **`com.comp2042.ViewData`**:
    -   **Changes:** Added `boardMatrix` to the constructor and a getter
        method.
    -   **Reason:** The GUI needed access to the full merged state of
        the board to render the game correctly without complex overlay
        logic.

## Unexpected Problems

-   **AWT vs. JavaFX Conflicts:** The legacy codebase relied on
    `java.awt.Point` for coordinate management. This is generally bad
    practice in a JavaFX application (mixing UI toolkits).
    -   **Resolution:** I refactored `SimpleBoard` to use primitive
        integers for coordinates, completely removing the AWT
        dependency.
