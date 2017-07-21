package stateData;

import playerModule.PlayerModule;

/**
 * Created by Student on 12-Jul-17.
 */
public class PlayerState {
    int id;
    GameState.Player playerState;
    ClientState clientState;
    PlayerModule.PhModel phModel;

    public void setId(int id) {
        this.id = id;
    }

    public GameState.Player getPlayer() {
        return playerState;
    }

    public void setPlayer(GameState.Player playerState) {
        this.playerState = playerState;
    }

    public ClientState getClientState() {
        return clientState;
    }

    public void setClientState(ClientState clientState) {
        this.clientState = clientState;
    }

    public PlayerModule.PhModel getPhModel() {
        return phModel;
    }

    public void setPhModel(PlayerModule.PhModel phModel) {
        this.phModel = phModel;
    }

    public PlayerState(int id) {
        this.id = id;
    }

    public PlayerState(int id, GameState.Player playerState, ClientState clientState, PlayerModule.PhModel phModel) {
        this.id = id;
        this.playerState = playerState;
        this.clientState = clientState;
        this.phModel = phModel;
    }

    public int getId() {
        return id;
    }
}
