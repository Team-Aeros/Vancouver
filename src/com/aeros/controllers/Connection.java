/*
 * Vancouver
 *
 * @version     1.0 RC2
 * @author      Aeros Development
 * @copyright   2017, Vancouver
 *
 * @license     Apache 2.0
 */

package com.aeros.controllers;

import com.aeros.main.Util;

import java.io.*;
import java.net.Socket;

/**
 * The connection class handles generator connections.
 * @since 1.0 Beta 1
 * @author Aeros Development
 */
public class Connection implements Runnable {

    /**
     * This reader is used for reading data
     */
    private BufferedReader _bufferedReader;

    /**
     * The buffered reader needs an input stream. This is said input stream.
     */
    private InputStream _inputStream;

    /**
     * Creates a new instance of the Connection class
     * @param socket A socket object
     */
    public Connection(Socket socket) {
        try {
            _inputStream = socket.getInputStream();
            _bufferedReader = new BufferedReader(new InputStreamReader(_inputStream));
        }

        catch (IOException e) {
            Util.throwError("Could not get input stream from socket");
        }
    }

    /**
     * Runs continuously and checks if there is more data we can parse.
     */
    public void run() {
        Integer length;

        while (true) {
            try {
                length = _inputStream.available();
                if (length != -1) {
                    // Yes, ideally this would be in a thread
                    new Parser(_bufferedReader).run();
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