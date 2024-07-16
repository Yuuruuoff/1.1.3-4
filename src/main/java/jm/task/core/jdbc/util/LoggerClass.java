package jm.task.core.jdbc.util;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerClass {

    private Logger logger;

    String path = new String("src/main/java/jm/task/core/jdbc/logs/logs.log");

    public LoggerClass() {
        logger = Logger.getLogger("DBLogger");
        try {
            FileHandler fh = new FileHandler(path);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Logger getLogger(){
        return logger;
    }
}
