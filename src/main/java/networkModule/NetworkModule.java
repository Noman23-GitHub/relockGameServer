package networkModule;

import moduleManager.ModuleManagerInterface;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import stateData.ClientState;
import stateData.GameState;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class NetworkModule implements NetworkModuleInterface {

    @Resource
    ModuleManagerInterface moduleManager;

    Map<Integer, ObjectOutputStream> objectOutputStreamMap = new HashMap<Integer, ObjectOutputStream>();
    Map<Integer, ObjectInputStream> objectInputStreamMap = new HashMap<Integer, ObjectInputStream>();

    public void sendGameState(GameState gameState) {
        for (Integer playerID : objectOutputStreamMap.keySet()) {
            try {
                gameState.setPlayerID(playerID);
                objectOutputStreamMap.get(playerID).writeObject(gameState);
            } catch (IOException e) {
                objectInputStreamMap.remove(playerID);
                objectOutputStreamMap.remove(playerID);
                moduleManager.deletePlayer(playerID);
                moduleManager.transferException(e);
            }
        }
    }

    @Component
    @Scope("prototype")
    public class ClientThread extends Thread {

        int playerID;

        public ClientThread(int playerID) {
            this.playerID = playerID;
        }

        @Override
        public void run() {
            try {
                while (objectInputStreamMap.get(playerID) != null) {
                    ClientState cs = (ClientState) objectInputStreamMap.get(playerID).readObject();
                    moduleManager.transferClientState(playerID, cs);
                }
            } catch (Exception e) {
                if (e instanceof SocketException) {
                    System.out.println("Клиент " +moduleManager.getPlayer(playerID).getName()+ " [" + playerID + "] дисконнектед");
                } else {
                    moduleManager.transferException(e);
                }

                objectInputStreamMap.remove(playerID);
                objectOutputStreamMap.remove(playerID);
                moduleManager.deletePlayer(playerID);

            }
        }
    }


    public void clientMonitor() throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(2323);
        System.out.println("Сервер запущен...");
        while (true) {
            Socket clientSocket = serverSocket.accept();

            int playerID = moduleManager.initNewPlayer();

            System.out.println("Клиент с id "+ playerID + " подключен.");

            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            objectOutputStreamMap.put(playerID, outputStream);
            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
            objectInputStreamMap.put(playerID, inputStream);

            new ClientThread(playerID).start();
        }
    }
}