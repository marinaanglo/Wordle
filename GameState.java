package wordleHw;

/**
 * a simple enumeration that lets us
 * keep track of the game as it progresses,
 * which is derived entirely from the UML state diagram.
 */
public enum GameState {
    NEW_GAME,
    GAME_IN_PROGRESS,
    GAME_WINNER,
    GAME_LOSER;
}
