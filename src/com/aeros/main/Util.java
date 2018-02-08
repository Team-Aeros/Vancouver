/*
 * Vancouver
 *
 * @version     1.0 RC2
 * @author      Aeros Development
 * @copyright   2017, Vancouver
 *
 * @license     Apache 2.0
 */

package com.aeros.main;

import com.aeros.models.LogEntry;

/**
 * A class containing abstract methods, used for things like displaying error messages and
 * printing other status messages.
 * @since 1.0 Beta 1
 * @author Aeros Development
 */
public abstract class Util {

    /**
     * Displays an error message
     * @param message The error message
     */
    public static void throwError(String message) {
        System.out.printf("==> A fatal error occurred: %s\n", message);
        //LogEntry.create(message);
    }

    /**
     * Displays an error message with detailed information
     * @param message The error message
     * @param detailedError Detailed information about the error
     */
    public static void throwError(String message, String detailedError) {
        System.out.printf("==> A fatal error occurred: %s\n%s\n", message, detailedError);
        //LogEntry.create(message);
    }

    /**
     * Prints a status message to the screen.
     * @param status The status message
     */
    public static void printStatus(String status) {
        System.out.printf("==> %s\n", status);
        //LogEntry.create(status);
    }
}