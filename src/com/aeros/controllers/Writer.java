package com.aeros.controllers;

import com.aeros.main.Util;
import com.aeros.models.Measurement;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Writer {

    private Measurement _measurement;
    private String _flags;
    private int num;

    private String _freeze;
    private String _rain;
    private String _snow;
    private String _hail;
    private String _thunder;
    private String _tornado;

    public Writer(Measurement measurement) {
        _measurement = measurement;
    }

    public void write() {
        try {
            _flags = _measurement.getFlags();

            for (num = 0; num < 6; num++) {
                String state = _flags.substring(num,num+1);

                switch (num) {
                    case 0:
                        _freeze = (state.equals("0")) ? "false" : "true";
                        break;
                    case 1:
                        _rain = (state.equals("0")) ? "false" : "true";
                        break;
                    case 2:
                        _snow = (state.equals("0")) ? "false" : "true";
                        break;
                    case 3:
                        _hail = (state.equals("0")) ? "false" : "true";
                        break;
                    case 4:
                        _thunder = (state.equals("0")) ? "false" : "true";
                        break;
                    case 5:
                        _tornado = (state.equals("0")) ? "false" : "true";
                        break;
                }
            }

            List<String> lines = Arrays.asList( "{",
                                                "   \"station\"" + ":" + _measurement.getStation() + ",",
                                                "   \"date\"" + ":" + "\"" + _measurement.getDate() + "\"" + ",",
                                                "   \"time\"" + ":" + "\"" + _measurement.getTime() + "\"" + ",",
                                                "   \"temperature\"" + ":" + _measurement.getTemperature() + ",",
                                                "   \"dew_point\"" + ":" + _measurement.getDewPoint() + ",",
                                                "   \"station_pressure\"" + ":" + _measurement.getStationPressure() + ",",
                                                "   \"sea_level_pressure\"" + ":" + _measurement.getSeaLevelPressure() + ",",
                                                "   \"visibility\"" + ":" + _measurement.getVisibility() + ",",
                                                "   \"wind_speed\"" + ":" + _measurement.getWindSpeed() + ",",
                                                "   \"precipitation\"" + ":" + _measurement.getPrecipitation() + ",",
                                                "   \"snowfall\"" + ":" + _measurement.getFallenSnow() + ",",
                                                "   \"flags\"" + ":" + "{",
                                                "       \"froze\"" + ":" + "\"" + _freeze + "\"" + ",",
                                                "       \"rained\"" + ":" + "\"" + _rain + "\"" + ",",
                                                "       \"snowed\"" + ":" + "\"" + _snow + "\"" + ",",
                                                "       \"hailed\"" + ":" + "\"" + _hail + "\"" + ",",
                                                "       \"thunder\"" + ":" + "\"" + _thunder + "\"" + ",",
                                                "       \"tornado\"" + ":" + "\"" + _tornado + "\"",
                                                "   \"}\"" + ",",
                                                "   \"clouds\"" + ":" + _measurement.getClouds() + ",",
                                                "   \"wind_direction\"" + ":" + _measurement.getWindDirection() + ",",
                                                "}");

            Path file = Paths.get("/mnt/" + _measurement.getStation() + "/" + _measurement.getDate() + "/" + _measurement.getTime().replace(":" , "-") + ".json");
            Files.createDirectories(file.getParent());
            Files.createFile(file);
            Files.write(file, lines, Charset.forName("UTF-8"));
        }
        catch (IOException e) {
            //Util.throwError("Error: ", e.getMessage());
        }
    }
}
