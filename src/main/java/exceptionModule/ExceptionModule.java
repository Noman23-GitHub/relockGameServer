package exceptionModule;

import moduleManager.ModuleManagerInterface;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ExceptionModule implements ExceptionModuleInterface {

    @Resource
    ModuleManagerInterface moduleManager;

    public void logException(Exception exception) {
        moduleManager.transferLogMessage(exception.toString());
        exception.printStackTrace();
    }
}
