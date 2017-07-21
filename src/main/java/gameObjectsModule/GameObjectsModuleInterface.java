package gameObjectsModule;

import java.util.List;

/**
 * Created by Student on 18-Jul-17.
 */
public interface GameObjectsModuleInterface {
    public int createNewGameObject(int playerID, int playerX, int playerY, int mouse[]);
    public void destroyGameObject(int objectID);
    public void setGameObjectAccelirationDirection(int objectID, double angle);
    public List<GameObjectsModule.ServerGameObject> getServerGameObjects();
}
