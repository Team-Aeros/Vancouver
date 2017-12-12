/*
 * Vancouver
 *
 * @version     1.0 Alpha 1
 * @author      Aeros Development
 * @copyright   2017, Vancouver
 *
 * @license     Apache 2.0
 */

package com.aeros.main;

import com.aeros.controllers.Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    private static final int DEFAULT_PORT = 2000;
    private static final int MAX_PORT_VALUE = 65535;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;

        System.out.println(":: Vancouver - Epic Server Software");

        if (args.length > 2) {
            Util.throwError("Invalid number of arguments");
            return;
        }

        try {
            int port = args.length == 2 ? Integer.parseInt(args[1]) : DEFAULT_PORT;

            if (port < 0 || port > MAX_PORT_VALUE)
                throw new NumberFormatException("Invalid port number");

            serverSocket = new ServerSocket(port);
        }

        catch (NumberFormatException | IOException e) {
            Util.throwError("Could not connect to remote server", e.getMessage());
            return;
        }

        while (true) {
            try {
                new Thread(new Connection(serverSocket.accept())).start();
            }

            catch (IOException e) {
                Util.throwError("Unable to accept request", e.getMessage());
                return;
            }
        }
    }
}