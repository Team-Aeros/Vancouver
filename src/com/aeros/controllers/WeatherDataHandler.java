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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class WeatherDataHandler extends DefaultHandler {

    private enum Operations {
        STATION, DATE, TIME, TEMPERATURE, DEWPOINT, STATION_PRESSURE,
        SEA_LEVEL_PRESSURE, VISIBILITY, WIND_SPEED, PRECIPITATION,
        FALLEN_SNOW, FLAGS, CLOUDS, WIND_DIRECTION, MEASUREMENT, NONE
    }

    private Measurement _measurement;

    private Operations _currentOperation = Operations.NONE;
    private Operations _previousOperation = Operations.NONE;

    public WeatherDataHandler() {
        super();
    }

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
        String value = new String(characters, start, length);
        if (_currentOperation != _previousOperation) {
            _previousOperation = _currentOperation;

            try {
                switch (_currentOperation) {
                    case MEASUREMENT:

                        break;
                    case STATION:
                        _measurement.setStation(Integer.parseInt(value));
                        break;
                    case DATE:
                        _measurement.setDate(value);
                        break;
                    case TIME:
                        _measurement.setTime(value);
                        break;
                    case TEMPERATURE:
                        _measurement.setTemperature(Float.parseFloat(value));
                        break;
                    case DEWPOINT:
                        _measurement.setDewPoint(Float.parseFloat(value));
                        break;
                    case STATION_PRESSURE:
                        _measurement.setStationPressure(Float.parseFloat(value));
                        break;
                    case SEA_LEVEL_PRESSURE:
                        _measurement.setSeaLevelPressure(Float.parseFloat(value));
                        break;
                    case VISIBILITY:
                        _measurement.setVisibility(Float.parseFloat(value));
                        break;
                    case WIND_SPEED:
                        _measurement.setWindSpeed(Float.parseFloat(value));
                        break;
                    case PRECIPITATION:
                        _measurement.setPrecipitation(Float.parseFloat(value));
                        break;
                    case FALLEN_SNOW:
                        _measurement.setFallenSnow(Float.parseFloat(value));
                        break;
                    case FLAGS:
                        _measurement.setFlags(value);
                        break;
                    case CLOUDS:
                        _measurement.setClouds(Float.parseFloat(value));
                        break;
                    case WIND_DIRECTION:
                        _measurement.setWindDirection(Integer.parseInt(value));
                        new Writer(_measurement).write();
                }
            } catch (NumberFormatException e) {
                //Util.throwError("Could not convert value to integer/float", e.getMessage());
            } catch (Exception e) {
                //Util.throwError("Could not parse date/time", e.getMessage());
            }
        }
    }
}
