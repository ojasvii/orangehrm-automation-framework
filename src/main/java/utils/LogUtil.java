package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtil {
    private static final Logger logger = LogManager.getLogger(LogUtil.class);

    public static void info(String message) {
        logger.info(message);
        System.out.println("[INFO] " + message);
    }

    public static void warn(String message) {
        logger.warn(message);
        System.out.println("[warn] " + message);
    }

    public static void error(String message, Throwable t) {
        logger.error(message,t);
        System.err.println("[ERROR] " + message);
    }

    public static void error(String message) {
        logger.error(message);
        System.err.println("[ERROR] " + message);
    }

    public static void debug(String message) {
        logger.debug(message);
    }
}
