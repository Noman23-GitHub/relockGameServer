package stateData;


import GameObjects.CollidableGameObject;

import java.awt.*;
import java.io.Serializable;
import java.util.List;


// Сервер-сайд гейм-стейт
public class GameState implements Serializable {

    //Поля////////////////////////////////////////////////////////////////////////////////////
    private static final long serialVersionUID = 23L;

    private int playerID;
    // Листы с объектами
    private List<Player> playerList;
    private List<GameObject> gameObjectList;

    // Эксепшин он сервера, т.е. ошибка на сервере, но переадрессована она клиенту.
    // Перенаправляется в обработчик ошибок, если не null офк.
    Exception serverException;
    //////////////////////////////////////////////////////////////////////////////////////////


    //Методы////////////////////////////////////////////////////////////////////////////////////
    public GameState(List<Player> playerList, List<GameObject> gameObjectList, Exception serverException) {
        this.playerList = playerList;
        this.gameObjectList = gameObjectList;
        this.serverException = serverException;
    }

    public GameState(int playerID, List<Player> playerList, List<GameObject> gameObjectList, Exception serverException) {
        this.playerID = playerID;
        this.playerList = playerList;
        this.gameObjectList = gameObjectList;
        this.serverException = serverException;
    }



    public void setServerException(Exception serverException) {
        this.serverException = serverException;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getPlayerID() {
        return playerID;
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    //Сабклассы///////////////////////////////////////////////////////////////////////////
    public static class Player extends CollidableGameObject implements Serializable{

        private static final long serialVersionUID = 25L;

        private int playerID;
        private String name;
        private Color color;
        private int angle;
        private int x;
        private int y;

        public int getPlayerID() {
            return playerID;
        }

        public void setPlayerID(int playerID) {
            this.playerID = playerID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public int getAngle() {
            return angle;
        }

        public void setAngle(int angle) {
            this.angle = angle;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            super.setX(x);
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            super.setY(y);
            this.y = y;
        }

        public Player(int playerID, String name, Color color, int angle, int x, int y) {
            super(x,y,30);
            this.playerID = playerID;
            this.name = name;
            this.color = color;
            this.angle = angle;
            this.x = x;
            this.y = y;
        }
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public List<GameObject> getGameObjectList() {
        return gameObjectList;
    }

    public Exception getServerException() {
        return serverException;
    }

    // Сабкласс для описания объектов на поле
    public static class GameObject extends CollidableGameObject implements Serializable{

        private static final long serialVersionUID = 26L;

        int id;
        int owner;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public GameObject(int id, int owner, ObjectTypeEnum typeSpeel, int x, int y, int radius) {
            super(x, y, radius);

            this.id = id;
            this.owner = owner;
            this.type = typeSpeel;
            this.x = x;
            this.y = y;
            this.width = width;
            this.heigth = heigth;
        }

        public int getOwner() {
            return owner;
        }

        // Перечисление типов игровых объектов
        public enum ObjectTypeEnum {
            TYPE_WALL,
            TYPE_SPEEL
        }

        private ObjectTypeEnum type;
        private int x;
        private int y;
        private int width;
        private int heigth;

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        @Override
        public int getHeigth() {
            return heigth;
        }

        @Override
        public void setHeigth(int heigth) {
            this.heigth = heigth;
        }

        public GameObject(ObjectTypeEnum type, int x, int y, int width, int heigth ) {
            super(x,y,width,heigth);
            this.type = type;
            this.x = x;
            this.y = y;
            this.width = width;
            this.heigth = heigth;
        }

        public ObjectTypeEnum getType() {
            return type;
        }

        public void setType(ObjectTypeEnum type) {
            this.type = type;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////
}
