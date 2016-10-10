package poli.pcs.redes.webserver.utils;

import poli.pcs.redes.webserver.components.Config;

import java.io.*;
import java.time.LocalDateTime;


public class Logger {

    private FileWriter fileWriter;
    private static Logger logger;
    private Config config;

    public Logger(String logPath) throws IOException {
        this.config = Config.getDefaultConfig();
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
        if (shouldLog(logLevel)) {
            LocalDateTime now = LocalDateTime.now();
            String logString = now.toString() + " :: [" + logLevel.toString() + "] - " + logText;
            if (logLevel == LogLevel.ERROR) {
                StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
                logString = logString + " at " + stackTraceElement.getClassName() + ":" + stackTraceElement.getMethodName() + "(" + stackTraceElement.getLineNumber() + ")";
            }
            System.out.println(logString);
            try {
                fileWriter.write(logString + "\n");
                fileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    private boolean shouldLog(LogLevel logLevel) {
        switch (LogLevel.fromString(config.get("logLevel"))) {
            case DEBUG: return true;
            case INFO: return (logLevel != LogLevel.DEBUG);
            case WARN: return (logLevel == LogLevel.WARN || logLevel == LogLevel.ERROR);
            case ERROR: return (logLevel == LogLevel.ERROR);
            default: return true;
        }
    }

}
