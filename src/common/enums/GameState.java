package common.enums;

public enum GameState {
    GAME_START("Game Start!"),
    GAME_END("Game ends!!"),
    GAME_TIED("Game is tied!");

    private final String gameState;

    GameState(String gameState) {
        this.gameState = gameState;
    }

    public String getGameState() {
        return gameState;
    }
}
