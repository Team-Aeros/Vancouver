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
import com.aeros.models.Measurement;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private BufferedReader _bufferedReader;

    public Parser(BufferedReader bufferedReader) {
        _bufferedReader = bufferedReader;
    }

    public void parse() {
        Measurement measurement = new Measurement();
        String line;
        String easyLine;
        String measurementLine;

        Pattern pattern = Pattern.compile("^\\<([a-zA-Z_]*)\\>(.+)\\<\\/([a-zA-Z_]*)\\>$");
        Matcher matcher;
        boolean done;

        try {
            while ((line = _bufferedReader.readLine()) != null) {
                easyLine = line.trim();
                done = false;

                if (easyLine.equalsIgnoreCase("<measurement>")) {
                    while ((measurementLine = _bufferedReader.readLine()) != null && !done) {
                        measurementLine = measurementLine.trim();

                        if (measurementLine.equalsIgnoreCase("</measurement>")) {
                            if (measurement.isValid())
                                new Writer(measurement).write();
                            else {
                                Util.throwError("Invalid measurement");
                                measurement = new Measurement();
                                done = true;
                                continue;
                            }

                            measurement = new Measurement();
                            done = true;
                        }
                        else {
                            matcher = pattern.matcher(measurementLine);

                            if (matcher.find() && matcher.group(1).equalsIgnoreCase(matcher.group(3))) {
                                try {
                                    switch (matcher.group(1).toLowerCase()) {
                                        case "stn":
                                            measurement.setStation(Integer.parseInt(matcher.group(2)));
                                            break;
                                        case "date":
                                            measurement.setDate(matcher.group(2));
                                            break;
                                        case "time":
                                            measurement.setTime(matcher.group(2));
                                            break;
                                        case "temp":
                                            measurement.setTemperature(Float.parseFloat(matcher.group(2)));
                                            break;
                                        case "dewp":
                                            measurement.setDewPoint(Float.parseFloat(matcher.group(2)));
                                            break;
                                        case "stp":
                                            measurement.setStationPressure(Float.parseFloat(matcher.group(2)));
                                            break;
                                        case "slp":
                                            measurement.setSeaLevelPressure(Float.parseFloat(matcher.group(2)));
                                            break;
                                        case "visib":
                                            measurement.setVisibility(Float.parseFloat(matcher.group(2)));
                                            break;
                                        case "wdsp":
                                            measurement.setWindSpeed(Float.parseFloat(matcher.group(2)));
                                            break;
                                        case "prcp":
                                            measurement.setPrecipitation(Float.parseFloat(matcher.group(2)));
                                            break;
                                        case "sndp":
                                            measurement.setFallenSnow(Float.parseFloat(matcher.group(2)));
                                            break;
                                        case "frshtt":
                                            measurement.setFlags(matcher.group(2));
                                            break;
                                        case "cldc":
                                            measurement.setClouds(Float.parseFloat(matcher.group(2)));
                                            break;
                                        case "wnddir":
                                            measurement.setWindDirection(Integer.parseInt(matcher.group(2)));
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
}
