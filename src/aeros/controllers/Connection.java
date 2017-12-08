package aeros.controllers;

import aeros.main.Util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Connection implements Runnable {

    private Socket _socket;
    private InputStreamReader _inputStreamReader;
    private String _transmission;

    public Connection(Socket socket) {
        _socket = socket;

        try {
            _inputStreamReader = new InputStreamReader(socket.getInputStream());
        }

        catch (IOException e) {
            Util.throwError("Could not get input stream from socket");
        }
    }

    public void run() {
        while (true) {
            _transmission = _inputStreamReader.toString();
        }
    }
}