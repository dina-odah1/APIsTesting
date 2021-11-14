package listeners;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class TestLogger {
    private static final Logger LOGGER = LogManager.getLogger(TestLogger.class);
    public static void info( String message ) {
        LOGGER.info( message );
    }
}