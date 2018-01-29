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
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

public class Parser {

    private String _data;
    private String _buffer;
    private int num = 0;

    public Parser(String data) {
        //Util.printStatus("Started parsing");

        try {
            _data = data;
        }

        catch (Exception e) {
            Util.throwError("Could not get input stream from socket");
        }
    }

    public void parse() {
        //Util.printStatus("Entering Parser run() method");

        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

        while (true) {
            if (_data != null) {
                try {
                    SAXParser saxParser = saxParserFactory.newSAXParser();
                    WeatherDataHandler weatherDataHandler = new WeatherDataHandler();
                    saxParser.parse(new InputSource(new StringReader(_data)), weatherDataHandler);
                } catch (ParserConfigurationException | IOException | SAXException e) {
                    //Util.throwError("Could not parse XML data", e.getMessage());
                }
            }
        }
    }
}
