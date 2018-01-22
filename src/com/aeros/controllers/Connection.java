/*
 * Vancouver
 *
 * @version     1.0 Alpha 1
 * @author      Aeros Development
 * @copyright   2017, Vancouver
 *
 * @license     Apache 2.0
 */

package com.aeros.controllers;

import com.aeros.main.Util;
import com.aeros.models.WeatherStationInfo;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection implements Runnable {

    private DataInputStream _inputStream;

    private volatile byte[] _buffer;

    public Connection(Socket socket) {
        Util.printStatus("Started new thread");

        try {
            _inputStream = new DataInputStream(socket.getInputStream());
        }

        catch (IOException e) {
            Util.throwError("Could not get input stream from socket");
        }
    }

    public void run() {
        Util.printStatus("Entering run() method");

        int length;

        while (true) {
            try {
                if ((length = _inputStream.available()) != -1) {
                    _buffer = new byte[length];
                    _inputStream.readFully(_buffer);

                    if (_buffer.length != 0)
                        new WeatherStationInfo(_buffer).parse();
                }
                else {
                    _inputStream.close();
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