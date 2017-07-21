package moduleManager;

import GameObjects.GameObjects;
import exceptionModule.ExceptionModuleInterface;
import gameObjectsModule.GameObjectsModule;
import gameObjectsModule.GameObjectsModuleInterface;
import ingameModule.IngameModuleInterface;
import loggerModule.LoggerModuleInterface;
import networkModule.NetworkModuleInterface;
import org.springframework.stereotype.Component;
import playerModule.PlayerModuleInterface;
import stateData.ClientState;
import stateData.GameState;
import stateData.PlayerState;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ModuleManager implements ModuleManagerInterface {

    @Resource
    ExceptionModuleInterface exceptionModule;
    @Resource
    IngameModuleInterface ingameModule;
    @Resource
    LoggerModuleInterface loggerModule;
    @Resource
    NetworkModuleInterface networkModule;
    @Resource
    PlayerModuleInterface playerModule;
    @Resource
    GameObjectsModuleInterface gameObjectsModule;


    public void transferGameState(GameState gameState) {
        networkModule.sendGameState(gameState);
    }

    public void transferException(Exception exception) {
        exceptionModule.logException(exception);
    }

    public void transferLogMessage(String message) {
        loggerModule.logMessage(message);
    }

    public void transferClientState(int playerId, ClientState clientState) {
        playerModule.setClientState(playerId, clientState);
    }




    public int initNewPlayer() {
        return playerModule.createNewPlayer();
    }

    public void deletePlayer(int playerID) {
        playerModule.deletePlayer(playerID);
    }

    public List<PlayerState> getPlayerStates() {
        return playerModule.getPlayerStates();
    }

    public GameState.Player getPlayer(int playerID) {
        return playerModule.getPlayer(playerID);
    }

    public void setPlayer(int playerID, GameState.Player player) {
        playerModule.setPlayer(playerID, player);
    }

    public ClientState getClientState(int playerID) {
        return playerModule.getClientState(playerID);
    }



    public int createNewGameObject(int playerID, int playerX, int playerY, int[] mouse) {
        return gameObjectsModule.createNewGameObject(playerID, playerX, playerY, mouse);
    }

    public void destroyGameObject(int objectID) {
        gameObjectsModule.destroyGameObject(objectID);
    }

    public void setGameObjectAccelirationDirection(int objectID, double angle) {
        gameObjectsModule.setGameObjectAccelirationDirection(objectID, angle);
    }

    public List<GameObjectsModule.ServerGameObject> getServerGameObjects() {
        return gameObjectsModule.getServerGameObjects();
    }
}
