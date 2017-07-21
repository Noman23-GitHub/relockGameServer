package loggerModule;

/**
 * Created by Student on 12-Jul-17.
 */
public class LoggerModule implements LoggerModuleInterface{
    public void logMessage(String message) {
        System.out.println("Logger: "+message);
    }
}
