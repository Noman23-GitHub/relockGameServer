package moduleManager;

import exceptionModule.ExceptionModule;
import exceptionModule.ExceptionModuleConfiguration;
import gameObjectsModule.GameObjectsConfiguration;
import ingameModule.IngameModule;
import ingameModule.IngameModuleConfiguration;
import loggerModule.LoggerModuleConfiguration;
import networkModule.NetworkModule;
import networkModule.NetworkModuleConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import playerModule.PlayerModuleConfiguration;
import stateData.ClientState;

import java.awt.*;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();

        context.register(ModuleManagerConfiguration.class);
        context.register(NetworkModuleConfiguration.class);
        context.register(IngameModuleConfiguration.class);
        context.register(LoggerModuleConfiguration.class);
        context.register(ExceptionModuleConfiguration.class);
        context.register(PlayerModuleConfiguration.class);
        context.register(GameObjectsConfiguration.class);
        context.refresh();


        context.getBean(NetworkModule.class).clientMonitor();
        //context.getBean(ModuleManager.class).initNewPlayer(23);
        //context.getBean(ModuleManager.class).initNewPlayer(24);
       // context.getBean(ModuleManager.class).transferClientState();
        //context.getBean(ModuleManager.class).transferClientState();
        //context.getBean(ModuleManager.class).transferClientState(24,new ClientState((byte)ClientState.MoveType.MOVE_DOWN, ClientState.CmdTypeEnum.CMD_NONE, new int[] {0,0}, "s", new Color(255, 217, 0)));
        //context.getBean(ModuleManager.class).transferClientState();
        //context.getBean(ModuleManager.class).transferClientState(23,new ClientState((byte)ClientState.MoveType.MOVE_DOWN, ClientState.CmdTypeEnum.CMD_NONE, new int[] {0,0}, "Noman23", new Color(255, 217, 0)));
        //context.getBean(ModuleManager.class).transferClientState();
        //context.getBean(ModuleManager.class).transferClientState();
        //context.getBean(ModuleManager.class).transferClientState();
    }
}
