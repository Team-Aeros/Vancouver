package aeros.main;

import aeros.controllers.Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    private static final int DEFAULT_PORT = 80;
    private static final int MAX_PORT_VALUE = 65535;

    public static void main(String[] args) {
        System.out.println(":: Epic Server");

        if (args.length > 2) {
            Util.throwError("Invalid number of arguments");
            return;
        }

        ServerSocket serverSocket;

        try {
            int port = args.length == 2 ? Integer.parseInt(args[1]) : DEFAULT_PORT;

            if (port < 0 || port > MAX_PORT_VALUE)
                throw new NumberFormatException();

            serverSocket = new ServerSocket(port);
        }

        catch (NumberFormatException | IOException e) {
            Util.throwError("Invalid port number");
            return;
        }

        Socket socket = null;

        while (true) {
            try {
                socket = serverSocket.accept();
            }

            catch (IOException e) {
                Util.throwError("Unable to accept request");
            }

            new Thread(new Connection(socket)).start();
        }
    }
}