package aeros.main;

public abstract class Util {

    public static void throwError(String message) {
        System.out.printf("A fatal error occurred: %s\n", message);
    }

    public static void printStatus(String status) {
        System.out.printf("==> %s", status);
    }
}