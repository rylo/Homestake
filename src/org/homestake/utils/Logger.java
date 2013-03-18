package org.homestake.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {
    public String logDirectory = "log/";
    public String logFileName = logDirectory + "production.log";
    public PrintWriter logFile;

    public Logger() {}

    public Logger(String logFileName) {
        this.logFileName = logDirectory + logFileName;
    }

    public void writeMessage(String message) {
        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFileName,  true)));
            logFile.print(message + "\n");
            logFile.close();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void clear() throws IOException {
        FileWriter fileWriter = new FileWriter(logFileName, false);
        fileWriter.write("");
        fileWriter.close();
    }

}
