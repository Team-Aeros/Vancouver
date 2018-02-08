/*
 * Vancouver
 *
 * @version     1.0 RC1
 * @author      Aeros Development
 * @copyright   2017, Vancouver
 *
 * @license     Apache 2.0
 */

package com.aeros.models;

import com.aeros.main.Util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LogEntry {

    private static String MAIN_LOG_LOCATION;

    public enum LogType {
        MAIN
    }

    public synchronized static void create(String message, LogType type) {
        BufferedWriter bufferedWriter;
        String fileLocation;

        switch (type) {
            case MAIN:
                fileLocation = MAIN_LOG_LOCATION;
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
            System.out.println("Could not open log file: " + e.getMessage());
        }
    }

    public synchronized static void create(String message) {
        create(message, LogType.MAIN);
    }

    public static void setPaths() {
        try {
            MAIN_LOG_LOCATION = LogEntry.class.getClassLoader().getResource("./logs/main.log").getPath();
        }

        catch (NullPointerException e) {
            System.out.println("Could not find log file");
            System.exit(-1);
        }
    }
}
