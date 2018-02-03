/*
 * Vancouver
 *
 * @version     2.0 Alpha 1
 * @author      Aeros Development
 * @copyright   2017, Vancouver
 *
 * @license     Apache 2.0
 */

package com.aeros.main;

import com.aeros.models.LogEntry;

public abstract class Util {

    public static void throwError(String message) {
        System.out.printf("==> A fatal error occurred: %s\n", message);
        LogEntry.create(message);
    }

    public static void throwError(String message, String detailedError) {
        System.out.printf("==> A fatal error occurred: %s\n%s\n", message, detailedError);
        LogEntry.create(message);
    }

    public static void printStatus(String status) {
        System.out.printf("==> %s\n", status);
        LogEntry.create(status);
    }
}