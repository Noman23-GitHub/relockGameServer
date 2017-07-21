package gameObjectsModule;

import GameObjects.CollidableGameObject;
import moduleManager.ModuleManagerInterface;
import org.springframework.stereotype.Component;
import stateData.GameState;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GameObjectsModule implements GameObjectsModuleInterface {

    Map<Integer, ServerGameObject> serverGameObjectMap = new HashMap<Integer, ServerGameObject>();

    private int getFreeId() {
        int i = 0;
        while (serverGameObjectMap.get(i) != null) i++;
        return i;
    }

    public static class ServerGameObject extends CollidableGameObject {
        int owner;
        int id;

        public int getSpeedX() {

            return (int) Math.round(speedX);
        }

        public int getSpeedY() {
            return (int) Math.round(speedY);
        }

        double speedX = 1;
        double speedY = 1;

        double accelirate = .1;

        int speed = 6;

        double angle;

        public ServerGameObject(int id, int owner, int x, int y, int radius, int mouse[]) {
            super(x, y, radius);
            this.id = id;
            this.owner = owner;
            this.angle = Math.atan2(mouse[1] - y, mouse[0] - x);
            calcSpeedXY();

            System.out.println("Create fireball: [x,y] {" + x + "," + y + "}, mouse[x,y] {" + mouse[0] + "," + mouse[1] + "} angle="+angle);

        }

        public int getOwner() {
            return owner;
        }

        public int getId() {
            return id;
        }

        public void calcSpeedXY() {
            speedX = (speed * Math.cos(angle));
            speedY = (speed * Math.sin(angle));

        }

        public double getAngle() {
            return angle;
        }

        public void setAngle(double angle) {
            this.angle = angle;
        }

        public GameState.GameObject getGameObject() {
            return new GameState.GameObject(id, owner, GameState.GameObject.ObjectTypeEnum.TYPE_SPEEL, getX(), getY(), getRadius());
        }

    }

    public int createNewGameObject(int playerID, int playerX, int playerY, int mouse[]) {
        int objectId = getFreeId();
        ServerGameObject serverGameObject = new ServerGameObject(objectId, playerID, playerX, playerY, 20, mouse);

        serverGameObjectMap.put(objectId, serverGameObject);
        return objectId;
    }

    public void destroyGameObject(int objectID) {
        serverGameObjectMap.remove(objectID);
    }

    public void setGameObjectAccelirationDirection(int objectID, double angle) {
        serverGameObjectMap.get(objectID).setAngle(angle);
    }

    public List<ServerGameObject> getServerGameObjects() {
        List<ServerGameObject> ret = new ArrayList<ServerGameObject>();
        for (ServerGameObject object : serverGameObjectMap.values()) {
            ret.add(object);
        }
        return ret;
    }
}