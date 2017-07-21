package playerModule;

import stateData.ClientState;
import stateData.GameState;
import stateData.PlayerState;

import java.net.Socket;
import java.util.List;

public interface PlayerModuleInterface {
    public int createNewPlayer();
    public void deletePlayer(int playerID);
    public List<PlayerState> getPlayerStates();
    public GameState.Player getPlayer(int playerID);
    public ClientState getClientState(int playerID);
    public void setClientState(int playerID, ClientState clientState);
    public  void setPlayer(int playerID, GameState.Player player);
}
