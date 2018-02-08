/*
 * Vancouver
 *
 * @version     1.0 RC1
 * @author      Aeros Development
 * @copyright   2017, Vancouver
 *
 * @license     Apache 2.0
 */

package com.aeros.controllers;

import com.aeros.main.Util;
import com.aeros.models.Measurement;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private BufferedReader _bufferedReader;

    private static ConcurrentHashMap<Integer, Measurement[]> _measurements = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Integer, Writer> _writers = new ConcurrentHashMap<>();

    public Parser(BufferedReader bufferedReader) {
        _bufferedReader = bufferedReader;
    }

    public void run() {
        Measurement measurement = new Measurement();
        String line;
        String easyLine;
        String measurementLine;

        Pattern pattern = Pattern.compile("^\\<([a-zA-Z_]*)\\>(.+)\\<\\/([a-zA-Z_]*)\\>$");
        Pattern alternativePattern = Pattern.compile("^\\<([a-zA-Z_]*)\\>\\<\\/([a-zA-Z_]*)\\>$");
        Matcher matcher;
        boolean done;

        int stationId;
        boolean emptySlotFound;
        boolean missingValue = false;

        String match;

        boolean foundMatch;

        try {
            while ((line = _bufferedReader.readLine()) != null) {
                easyLine = line.trim();
                done = false;

                if (easyLine.equalsIgnoreCase("<measurement>")) {
                    while ((measurementLine = _bufferedReader.readLine()) != null && !done) {
                        measurementLine = measurementLine.trim();

                        if (measurementLine.equalsIgnoreCase("</measurement>")) {
                            if (measurement.isValid()) {
                                stationId = measurement.getStation();
                                emptySlotFound = false;

                                if (!_measurements.containsKey(stationId))
                                    addStation(stationId);

                                measurement.checkAndFixReadings(_measurements.get(stationId));

                                for (int i = 0; i < 4; i++) {
                                    if (_measurements.get(stationId)[i] != null)
                                        break;

                                    _measurements.get(stationId)[i] = _measurements.get(stationId)[i + 1];

                                    if (_measurements.get(stationId)[i] == null) {
                                        emptySlotFound = true;
                                        _measurements.get(stationId)[i] = measurement;
                                    }
                                }

                                if (!emptySlotFound)
                                    _measurements.get(stationId)[4] = measurement;

                                addMeasurementToWriter(stationId, measurement);
                            }
                            else {
                                Util.throwError("Invalid measurement");
                                measurement = new Measurement();
                                done = true;
                                continue;
                            }

                            measurement = new Measurement();
                            break;
                        }
                        else {
                            matcher = pattern.matcher(measurementLine);
                            foundMatch = matcher.find();

                            if (!foundMatch) {
                                matcher = alternativePattern.matcher(measurementLine);
                                foundMatch = matcher.find();
                                missingValue = true;
                            }

                            if (foundMatch && (missingValue ? matcher.group(1).equalsIgnoreCase(matcher.group(2)) : matcher.group(1).equalsIgnoreCase(matcher.group(3)))) {
                                try {
                                    match = missingValue ? "0" : matcher.group(2);
                                    missingValue = false;

                                    switch (matcher.group(1).toLowerCase()) {
                                        case "stn":
                                            measurement.setStation(Integer.parseInt(match));
                                            break;
                                        case "date":
                                            measurement.setDate(match);
                                            break;
                                        case "time":
                                            measurement.setTime(match);
                                            break;
                                        case "temp":
                                            measurement.setTemperature(Float.parseFloat(match));
                                            break;
                                        case "dewp":
                                            measurement.setDewPoint(Float.parseFloat(match));
                                            break;
                                        case "stp":
                                            measurement.setStationPressure(Float.parseFloat(match));
                                            break;
                                        case "slp":
                                            measurement.setSeaLevelPressure(Float.parseFloat(match));
                                            break;
                                        case "visib":
                                            measurement.setVisibility(Float.parseFloat(match));
                                            break;
                                        case "wdsp":
                                            measurement.setWindSpeed(Float.parseFloat(match));
                                            break;
                                        case "prcp":
                                            measurement.setPrecipitation(Float.parseFloat(match));
                                            break;
                                        case "sndp":
                                            measurement.setSnowfall(Float.parseFloat(match));
                                            break;
                                        case "frshtt":
                                            measurement.setFlags(match);
                                            break;
                                        case "cldc":
                                            measurement.setClouds(Float.parseFloat(match));
                                            break;
                                        case "wnddir":
                                            measurement.setWindDirection(Integer.parseInt(match));
                                            break;
                                        default:
                                            System.out.println("Unknown field");
                                            return;
                                    }
                                }
                                catch (NumberFormatException e) {
                                    System.out.println("Invalid number");
                                    e.printStackTrace();
                                    return;
                                }
                            }
                            else {
                                System.out.println("Invalid format: " + measurementLine);
                                return;
                            }
                        }
                    }
                }
            }
        }

        catch (IOException e) {
            System.out.println("An IOException occurred: " + e.getMessage());
        }
    }

    private synchronized static void addMeasurementToWriter(int stationId, Measurement measurement) {
        if (!_writers.containsKey(stationId))
            _writers.put(stationId, new Writer(stationId));

        _writers.get(stationId).addMeasurement(measurement);
    }

    private synchronized static void addStation(int stationId) {
        _measurements.put(stationId, new Measurement[5]);
    }
}
