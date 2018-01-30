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
import java.util.ArrayList;


public class Connection implements Runnable {

    private BufferedReader _bufferedReader;
    private InputStream _inputStream;
    private String _input;
    private String _oldInput = "";

    private int _tempId = 0;

    private volatile byte[] _buffer;

    public Connection(Socket socket) {
        //Util.printStatus("Started connection Thread");

        try {
            _inputStream = socket.getInputStream();
            _bufferedReader = new BufferedReader(new InputStreamReader(_inputStream));
        }

        catch (IOException e) {
            Util.throwError("Could not get input stream from socket");
        }
    }

    public void run() {
        //Util.printStatus("Entering Connection run() method");

        Integer length;
        String line;

        while (true) {
            try {
                length = _inputStream.available();
                if (length != -1) {
                    new Parser(_bufferedReader).parse();
                }
                else
                    return;
            }

            catch (IOException e) {
                Util.throwError("Could not read object", e.getMessage());
                return;
            }
        }
    }
}