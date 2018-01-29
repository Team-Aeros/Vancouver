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

import com.aeros.controllers.Connection;
import com.aeros.controllers.Parser;
import com.aeros.controllers.Queue;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static final int PORT = 53200;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Queue _queue = new Queue();
        int num = 0;

        System.out.println(":: Vancouver - Weather Data Parser Software");

        try {
            if (PORT < 0 || PORT > 65535)
                throw new NumberFormatException("Invalid port number");

            serverSocket = new ServerSocket(PORT);
            System.out.println("Connection opened on port: " + PORT);
        }

        catch (NumberFormatException | IOException e) {
            Util.throwError("Could not connect to remote server", e.getMessage());
            return;
        }

        while (num < 800) {
            try {
                Socket socket = serverSocket.accept();
                new Thread(new Connection(socket, _queue)).start();
                System.out.println("Started thread number: " + num);
                num++;
            }

            catch (IOException e) {
                Util.throwError("Unable to accept request", e.getMessage());
                return;
            }
        }
    }

}
