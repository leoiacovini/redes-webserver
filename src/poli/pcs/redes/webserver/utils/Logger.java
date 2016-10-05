package poli.pcs.redes.webserver.utils;

import java.io.*;
import java.time.LocalDateTime;


public class Logger {

    private FileWriter fileWriter;
    private static Logger logger;

    public Logger(String logPath) throws IOException {
        File logFile = new File(logPath);
        if (!logFile.exists()) {
            logFile.getParentFile().mkdirs();
        }
        fileWriter = new FileWriter(logFile, true);
    }

    public static Logger getLogger() {
        if (logger == null) {
            try {
                Logger.logger = new Logger("logs/server.log");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Logger.logger;
    }

    public void log(LogLevel logLevel, String logText) {
        LocalDateTime now = LocalDateTime.now();
        String logString = now.toString() + " :: [" + logLevel.toString() + "] - " + logText;
        System.out.println(logString);
        try {
            fileWriter.write(logString + "\n");
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void debug(String logText) {
        log(LogLevel.DEBUG, logText);
    }

    public void info(String logText) {
        log(LogLevel.INFO, logText);
    }

    public void warn(String logText) {
        log(LogLevel.WARN, logText);
    }

    public void error(String logText) {
        log(LogLevel.ERROR, logText);
    }

}
