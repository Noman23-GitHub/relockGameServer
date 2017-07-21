package playerModule;

import moduleManager.ModuleManagerInterface;
import org.omg.CORBA.INTERNAL;
import org.springframework.stereotype.*;
import stateData.ClientState;
import stateData.GameState;
import stateData.PlayerState;

import javax.annotation.Resource;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@org.springframework.stereotype.Component
public class PlayerModule implements PlayerModuleInterface {

    @Resource
    ModuleManagerInterface moduleManager;

    Map<Integer, ClientState> clientStateMap = new HashMap<Integer, ClientState>();
    Map<Integer, GameState.Player> playerMap = new HashMap<Integer, GameState.Player>();
    Map<Integer, PhModel> phModelMap = new HashMap<Integer, PhModel>();
    
    public class PhModel {
        double speed_x = 0;
        double speed_y = 0;

        public int getSpeedX() {
            return (int)Math.round(speed_x);
        }

        public int getSpeedY() {
            return (int)Math.round(speed_y);
        }

        double grav=.2;
        
        double force = 10;
        
        public void calcModel(){
            if (speed_x!=0) {
                if (speed_x>0) speed_x-=grav;
                if (speed_x<0) speed_x+=grav;
                //if (speed_x<=.2 || speed_x>=-.2) speed_x = 0;
            }
            if (speed_y!=0) {
                if (speed_y>0) speed_y-=grav;
                if (speed_y<0) speed_y+=grav;
                //if (speed_y<=.2 || speed_y>=-.2) speed_y = 0;
            }
        }

        public void setVector(int x, int y, int ox, int oy) {

           double angle = Math.atan2(oy - y, ox - x);
           speed_x += Math.cos(angle)*force;
           speed_y += Math.sin(angle)*force;
        }
        
    }
    
    
    private int getFreeId() {
        int i = 0;
        while (playerMap.get(i) != null) i++;
        return i;
    }

    public int createNewPlayer() {

        int playerId = getFreeId();
        PhModel phModel = new PhModel();
        ClientState clientState = new ClientState((byte) 0, ClientState.CmdTypeEnum.CMD_NONE, new int[]{0, 0}, "Unknow", new Color(255, 217, 0));
        GameState.Player player = new GameState.Player(playerId, clientState.getName(), clientState.getColor(), 0, 400, 300);

        phModelMap.put(playerId, phModel);
        clientStateMap.put(playerId, clientState);
        playerMap.put(playerId, player);

        return playerId;
    }

    public void deletePlayer(int id) {
        phModelMap.remove(id);
        clientStateMap.remove(id);
        playerMap.remove(id);
    }

    public List<PlayerState> getPlayerStates() {
        ArrayList<PlayerState> playerStates = new ArrayList<PlayerState>();

        for (Integer integer : clientStateMap.keySet()) {
            playerStates.add(new PlayerState(integer, playerMap.get(integer), clientStateMap.get(integer), phModelMap.get(integer)));
        }

        return playerStates;
    }

    public GameState.Player getPlayer(int id) {
        return playerMap.get(id);
    }

    public ClientState getClientState(int id) {
        return clientStateMap.get(id);
    }

    public void setClientState(int id, ClientState clientState) {

        if (!clientStateMap.get(id).getName().equals(clientState.getName())) {

            for (Integer integer : clientStateMap.keySet()) {
                if (integer.intValue()!=id) {
                    if (clientStateMap.get(integer).getName().equals(clientState.getName())) {
                        clientState.setName(clientState.getName() + " ("+id+")");
                        break;
                    }
                }
            }
            if (!clientStateMap.get(id).getName().equals(clientState.getName()))
                System.out.println("Клиент с id ["+ id +"] сменил имя с \"" + clientStateMap.get(id).getName() + "\" на \"" + clientState.getName() + "\"");
        }

        if (clientState.getCmd()==ClientState.CmdTypeEnum.CMD_SPELL_1) {
            System.out.println("Клиент "+clientState.getName()+" ["+ id +"] кастует скилл 1 {"+clientState.getM_pos()[0] +", " + clientState.getM_pos()[1] +"}");
        }
        if (clientState.getCmd()==ClientState.CmdTypeEnum.CMD_SPELL_2) {
            System.out.println("Клиент "+clientState.getName()+" ["+ id +"] кастует скилл 2 {"+clientState.getM_pos()[0] +", " + clientState.getM_pos()[1] +"}");
        }

        if (clientState.getCmd() == ClientState.CmdTypeEnum.CMD_SPELL_1) {
            moduleManager.createNewGameObject(id, playerMap.get(id).getX(), playerMap.get(id).getY(), clientState.getM_pos());
        }


        clientStateMap.put(id, clientState);
        GameState.Player player = getPlayer(id);
        player.setName(clientState.getName());
        player.setColor(clientState.getColor());
        playerMap.put(id, player);
    }

    public void setPlayer(int playerID, GameState.Player player) {
        playerMap.put(playerID, player);
    }
}
