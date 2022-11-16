package Exceptions;
import java.util.logging.Level;
import java.util.logging.Logger;
import View.View;

public class LoggerUtil  {
    private static Logger LOGGER=Logger.getLogger(LoggerUtil.class.getName());
    public static void logError(String message){
        LOGGER.log(Level.SEVERE,message);
    }
}
