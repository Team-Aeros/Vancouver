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
import com.aeros.models.Measurement;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;

public class WeatherDataHandler extends DefaultHandler {

    private enum Operations {
        STATION, DATE, TIME, TEMPERATURE, DEWPOINT, STATION_PRESSURE,
        SEA_LEVEL_PRESSURE, VISIBILITY, WIND_SPEED, PRECIPITATION,
        FALLEN_SNOW, FLAGS, CLOUDS, WIND_DIRECTION, MEASUREMENT, NONE
    }

    private List<Measurement> _measurements;
    private Measurement _measurement;

    private static Queue<String> _queue;

    private Operations _currentOperation = Operations.NONE;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName.toLowerCase()) {
            case "weatherdata":
                _currentOperation = Operations.NONE;
                break;
            case "measurement":
                _measurement = new Measurement();
                _currentOperation = Operations.MEASUREMENT;
                break;
            case "stn":
                _currentOperation = Operations.STATION;
                break;
            case "date":
                _currentOperation = Operations.DATE;
                break;
            case "time":
                _currentOperation = Operations.TIME;
                break;
            case "temp":
                _currentOperation = Operations.TEMPERATURE;
                break;
            case "dewp":
                _currentOperation = Operations.DEWPOINT;
                break;
            case "stp":
                _currentOperation = Operations.STATION_PRESSURE;
                break;
            case "slp":
                _currentOperation = Operations.SEA_LEVEL_PRESSURE;
                break;
            case "visib":
                _currentOperation = Operations.VISIBILITY;
                break;
            case "wdsp":
                _currentOperation = Operations.WIND_SPEED;
                break;
            case "prcp":
                _currentOperation = Operations.PRECIPITATION;
                break;
            case "sndp":
                _currentOperation = Operations.FALLEN_SNOW;
                break;
            case "frshtt":
                _currentOperation = Operations.FLAGS;
                break;
            case "cldc":
                _currentOperation = Operations.CLOUDS;
                break;
            case "wnddir":
                _currentOperation = Operations.WIND_DIRECTION;
                break;
            default:
                Util.printStatus(String.format("Unknown element: %s", qName));
        }
    }

    @Override
    public void characters(char[] characters, int start, int length) {
        switch (_currentOperation) {
            case MEASUREMENT:
                addToQueue();
                break;
            case STATION:
                //_measurement.setStation(new String(characters, start, length));
                break;
            case DATE:
                //_measurement.setDate();
                break;
            case TIME:
                //_measurement.setTime();
                break;
            case TEMPERATURE:
            case DEWPOINT:
            case STATION_PRESSURE:
            case SEA_LEVEL_PRESSURE:
            case VISIBILITY:
            case WIND_SPEED:
            case PRECIPITATION:
            case FALLEN_SNOW:
            case FLAGS:
                _measurement.setFlags(new String(characters, start, length));
                break;
            case CLOUDS:
            case WIND_DIRECTION:
        }
    }

    private void addToQueue() {
        //_queue.addItem("INSERT INTO %s");
        _queue.addItem("test");
    }

    public List<Measurement> getMeasurements() {
        return _measurements;
    }

    public static void setQueue(Queue<String> queue) {
        _queue = queue;
    }
}
