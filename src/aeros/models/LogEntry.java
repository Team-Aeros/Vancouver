package aeros.models;

import aeros.main.Util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LogEntry {

    private static final String HTTP_LOG_LOCATION = "/home/robert/EpicServer/logs/http.txt";

    public enum LogType {
        HTTP
    }

    public static void create(String message, LogType type) {
        BufferedWriter bufferedWriter;
        String fileLocation;

        switch (type) {
            case HTTP:
                fileLocation = HTTP_LOG_LOCATION;
                break;
            default:
                Util.throwError("Invalid log type");
                return;
        }

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(fileLocation, true));
            bufferedWriter.append(message);
            bufferedWriter.append("\n");
            bufferedWriter.close();
        }

        catch (IOException e) {
            Util.throwError("Could not open log file");
        }

        Util.printStatus("Added error to log file");
    }

    public static void create(String message) {
        create(message, LogType.HTTP);
    }
}
