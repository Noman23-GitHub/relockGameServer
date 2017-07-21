package ingameModule;

import gameObjectsModule.GameObjectsModule;
import moduleManager.ModuleManagerInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import playerModule.PlayerModule;
import stateData.ClientState;
import stateData.GameState;
import stateData.GameState.Player;
import stateData.PlayerState;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class IngameModule implements IngameModuleInterface {

    @Resource
    ModuleManagerInterface moduleManager;
    @Autowired
    IngameSettings ingameSettings;


    @Scheduled(fixedDelay = 1000 / 60)
    public void refreshGameState() {

        List<PlayerState> playerStates = moduleManager.getPlayerStates();

        int playerSpeed = ingameSettings.getPlayerSpeed();

        List<GameState.GameObject> gameObjectList = new ArrayList<GameState.GameObject>();
        List<GameObjectsModule.ServerGameObject> serverGameObjects = moduleManager.getServerGameObjects();


        for (GameObjectsModule.ServerGameObject serverGameObject : serverGameObjects) {

            serverGameObject.calcSpeedXY();
            serverGameObject.setX(serverGameObject.getX() + serverGameObject.getSpeedX());
            serverGameObject.setY(serverGameObject.getY() + serverGameObject.getSpeedY());
            if (serverGameObject.getY() > 1050 ||
                    serverGameObject.getY() <-50 ||
                    serverGameObject.getX() > 1050 ||
                    serverGameObject.getX() <-50) {
                moduleManager.destroyGameObject(serverGameObject.getId());
            }
            gameObjectList.add(serverGameObject.getGameObject());
        }

        //gameObjectList.add(new GameState.GameObject(GameState.GameObject.ObjectTypeEnum.TYPE_WALL, 140, 340, 200, 50));
        //gameObjectList.add(new GameState.GameObject(GameState.GameObject.ObjectTypeEnum.TYPE_WALL, 140, 340, 50, 200));
        gameObjectList.add(new GameState.GameObject(GameState.GameObject.ObjectTypeEnum.TYPE_WALL, 140, 540, 200, 50));

        List<Player> playerList = new ArrayList<Player>();

        for (PlayerState playerState : playerStates) {
            ClientState clientState = playerState.getClientState();
            Player player = playerState.getPlayer();
            PlayerModule.PhModel phModel = playerState.getPhModel();

            int x_speed = 0;
            int y_speed = 0;

            x_speed += (((clientState.getMove() & ClientState.MoveType.MOVE_RIGHT) != 0) ? playerSpeed : 0);
            x_speed -= (((clientState.getMove() & ClientState.MoveType.MOVE_LEFT) != 0) ? playerSpeed : 0);
            y_speed += (((clientState.getMove() & ClientState.MoveType.MOVE_DOWN) != 0) ? playerSpeed : 0);
            y_speed -= (((clientState.getMove() & ClientState.MoveType.MOVE_UP) != 0) ? playerSpeed : 0);

            x_speed += phModel.getSpeedX();
            y_speed += phModel.getSpeedY();
            phModel.calcModel();





            Player playerNew = new Player(player.getPlayerID(), player.getName(), player.getColor(), player.getAngle(), player.getX(), player.getY());

            playerNew.setX(player.getX() + x_speed);
            playerNew.setY(player.getY() + y_speed);

            for (PlayerState collidePlayerState : playerStates) {
                if (collidePlayerState.getId() != playerState.getId()) {
                    if (playerNew.isCollide(collidePlayerState.getPlayer())) {
                        playerNew.setX(player.getX());
                        playerNew.setY(player.getY());
                        break;
                    }
                }
            }

            for (GameState.GameObject gameObject : gameObjectList) {
                if (gameObject.getType() == GameState.GameObject.ObjectTypeEnum.TYPE_WALL) {
                    if (playerNew.isCollide(gameObject)) {
                        playerNew.setX(player.getX());
                        playerNew.setY(player.getY());
                        break;
                    }
                } else if (gameObject.getType() == GameState.GameObject.ObjectTypeEnum.TYPE_SPEEL) {
                    if (playerNew.getPlayerID() != gameObject.getOwner()) {
                        if (playerNew.isCollide(gameObject)) {
                            phModel.setVector(gameObject.getX(), gameObject.getY(), playerNew.getX(), playerNew.getY());
                            moduleManager.destroyGameObject(gameObject.getId());
                            break;
                        }
                    }

                    for (GameState.GameObject object : gameObjectList) {
                        if (object.getType() == GameState.GameObject.ObjectTypeEnum.TYPE_SPEEL) {
                            if (gameObject.getId() != object.getId()) {
                                if (gameObject.isCollide(object)) {
                                    moduleManager.destroyGameObject(gameObject.getId());
                                }
                            }
                        } else {
                            if (gameObject.isCollide(object)) {
                                moduleManager.destroyGameObject(gameObject.getId());
                            }
                        }
                    }
                }
            }


            if (playerNew.getX() == player.getX() && playerNew.getY() == player.getY()) {
                playerList.add(new Player(player.getPlayerID(), player.getName(), player.getColor(), player.getAngle(), player.getX(), player.getY()));
            } else {
                playerList.add(new Player(playerNew.getPlayerID(), playerNew.getName(), playerNew.getColor(), playerNew.getAngle(), playerNew.getX(), playerNew.getY()));
                moduleManager.setPlayer(playerState.getId(), playerNew);
            }
        }

        moduleManager.transferGameState(new GameState(playerList, gameObjectList, null));
    }
}
