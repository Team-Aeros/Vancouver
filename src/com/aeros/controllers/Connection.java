/*
 * Vancouver
 *
 * @version     2.0 Alpha 1
 * @author      Aeros Development
 * @copyright   2017, Vancouver
 *
 * @license     Apache 2.0
 */

package com.aeros.controllers;

import com.aeros.main.Util;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Connection implements Runnable {

    private DataInputStream _inputStream;
    private String _input;
    private String _oldInput = "";

    private int _tempId = 0;

    private volatile byte[] _buffer;

    public Connection(Socket socket) {
        //Util.printStatus("Started connection Thread");

        try {
            _inputStream = new DataInputStream(socket.getInputStream());
        }

        catch (IOException e) {
            Util.throwError("Could not get input stream from socket");
        }
    }

    public void run() {
        //Util.printStatus("Entering Connection run() method");

        Integer length;

        while (true) {
            try {
                length = _inputStream.available();
                if (length != -1) {
                    _buffer = new byte[length];
                    _inputStream.readFully(_buffer);

                    if (_buffer.length != 0) {
                        _input = new String(_buffer, StandardCharsets.UTF_8);
                        if (_input != null && !_oldInput.equals(_input)) {
                            new Parser(_input).parse();
                            _oldInput = _input;
                        }
                    }
                }
                else {
                    return;
                }
            }

            catch (IOException e) {
                Util.throwError("Could not read object", e.getMessage());
                return;
            }


        }
    }
}