package iust.ac.ir.nlp.jhazm.utils;

import org.apache.log4j.*;

import java.io.File;
import java.io.IOException;

/**
 * Created by majid on 12/20/14.
 */
public class LoggerUtil {

    private static boolean initiated = false;

    public static void initLogger() {
        try {
            Logger.getRootLogger().setLevel(Level.ALL);
            PatternLayout LOG_PATTERN = new PatternLayout("%5p %d{HH:mm:ss} - %m%n");
            Logger.getRootLogger().getAppender("stdout");

            //This is the root logger provided by log4j
            Logger rootLogger = Logger.getRootLogger();
            //Add console appender to root logger
            rootLogger.addAppender(new ConsoleAppender(LOG_PATTERN));

            //add file appender
            String filePath = System.getProperty("user.dir") + File.separator + "jhazm.log";
            RollingFileAppender appender = new RollingFileAppender(LOG_PATTERN, filePath);
            appender.setName("JHazm Log");
            appender.setMaxFileSize("1MB");
            appender.activateOptions();
            Logger.getRootLogger().addAppender(appender);
            initiated = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Logger getLogger(Class clazz) {
        if (!initiated) initLogger();
        return Logger.getLogger(clazz);
    }
}
