package networkModule;

import stateData.GameState;


public interface NetworkModuleInterface {
    public void sendGameState(GameState gameState);
}
