/*
 * Vancouver
 *
 * @version     1.0 RC2
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

/**
 * Adds a new entry to the log
 * @since 1.0 Beta 1
 * @author Aeros Development
 */
public class LogEntry {

    /**
     * Contains the location to the log
     */
    private static String MAIN_LOG_LOCATION;

    /**
     * An enum of possible log types
     */
    public enum LogType {
        MAIN
    }

    /**
     * Adds a new entry to a user-specified error log.
     * @param message The error message
     * @param type The log type (see the LogType enum)
     */
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

    /**
     * Adds an entry to the default error log (LogType.MAIN)
     * @param message The error message
     */
    public synchronized static void create(String message) {
        create(message, LogType.MAIN);
    }

    /**
     * Sets the log paths
     */
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
