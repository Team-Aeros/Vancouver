/*
 * Vancouver
 *
 * @version     1.0 Alpha 1
 * @author      Aeros Development
 * @copyright   2017, Vancouver
 *
 * @license     Apache 2.0
 */

package com.aeros.models;

import com.aeros.controllers.WeatherDataHandler;
import com.aeros.main.Util;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class WeatherStationInfo {

    private String _buffer;
    private ArrayList<Measurement> _measurements;

    public WeatherStationInfo(byte[] buffer) {
        _buffer = new String(buffer, StandardCharsets.UTF_8);
    }

    public void parse() {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            WeatherDataHandler weatherDataHandler = new WeatherDataHandler();
            saxParser.parse(_buffer, weatherDataHandler);
        }

        catch (ParserConfigurationException | IOException | SAXException e) {
            Util.throwError("Could not parse XML data", e.getMessage());
        }
    }
}
