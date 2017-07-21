package moduleManager;


import gameObjectsModule.GameObjectsModule;
import stateData.ClientState;
import stateData.GameState;
import stateData.PlayerState;

import java.net.Socket;
import java.util.List;

public interface ModuleManagerInterface {
    public void transferClientState(int playerID, ClientState clientState);
    public void transferGameState(GameState gameState);
    public void transferException(Exception exception);
    public void transferLogMessage(String message);

    public int initNewPlayer();
    public void deletePlayer(int playerID);
    public List<PlayerState> getPlayerStates();
    public GameState.Player getPlayer(int playerID);
    public void setPlayer(int playerID, GameState.Player player);
    public ClientState getClientState(int playerID);

    public int createNewGameObject(int playerID, int playerX, int playerY, int mouse[]);
    public void destroyGameObject(int objectID);
    public void setGameObjectAccelirationDirection(int objectID, double angle);
    public List<GameObjectsModule.ServerGameObject> getServerGameObjects();

}
