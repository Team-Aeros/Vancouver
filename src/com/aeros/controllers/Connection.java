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


public class Connection implements Runnable {

    private BufferedReader _bufferedReader;
    private InputStream _inputStream;

    public Connection(Socket socket) {
        try {
            _inputStream = socket.getInputStream();
            _bufferedReader = new BufferedReader(new InputStreamReader(_inputStream));
        }

        catch (IOException e) {
            Util.throwError("Could not get input stream from socket");
        }
    }

    public void run() {
        Integer length;

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