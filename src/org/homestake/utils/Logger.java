package org.homestake.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public enum Logger {
    LOGGER;

    public static String logDirectory = "log/";
    public static String logFileName = logDirectory + "production.log";
    public static PrintWriter logFile;
    public static Map<Long, StringBuilder> messageQueue = new HashMap<Long, StringBuilder>();

    public static void writeQueuedMessages(Long id) {
        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFileName,  true)));
            logFile.print(messageQueue.get(id).toString() + "\n\n");
            logFile.close();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void setLogFile(String fileName) {
        logFileName = logDirectory + fileName;
    }

    public static void clearFile() throws IOException {
        FileWriter fileWriter = new FileWriter(logFileName, false);
        fileWriter.write("");
        fileWriter.close();
    }

    public static void newQueue(Long id) {
        messageQueue.put(id, new StringBuilder());
    }

    public static void addToQueue(Long id, String message) {
        if (messageQueue.get(id) == null) {
            newQueue(id);
        }
        messageQueue.get(id).append(message);
    }

    public static StringBuilder getQueue(Long id) {
        return messageQueue.get(id);
    }

    public static void destroyQueue(Long id) {
        messageQueue.remove(id);
    }

}
