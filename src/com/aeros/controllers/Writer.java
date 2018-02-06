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

class Writer {

    private Measurement _measurement;

    private static final String SAVE_LOCATION = "/srv/http/webdav"; // Replace this if your webdav folder isn't located here

    Writer(Measurement measurement) {
        _measurement = measurement;
    }

    void write() {
        try {
            String _flags = _measurement.getFlags();

            String freeze = _flags.charAt(0) == '0' ? "false" : "true";
            String rain = _flags.charAt(1) == '0' ? "false" : "true";
            String snow = _flags.charAt(2) == '0' ? "false" : "true";
            String hail = _flags.charAt(3) == '0' ? "false" : "true";
            String thunder = _flags.charAt(4) == '0' ? "false" : "true";
            String tornado = _flags.charAt(5) == '0' ? "false" : "true";

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
                "   \"snowfall\"" + ":" + _measurement.getSnowfall() + ",",
                "   \"flags\"" + ":" + "{",
                "       \"froze\"" + ":" + "\"" + freeze + "\"" + ",",
                "       \"rained\"" + ":" + "\"" + rain + "\"" + ",",
                "       \"snowed\"" + ":" + "\"" + snow + "\"" + ",",
                "       \"hailed\"" + ":" + "\"" + hail + "\"" + ",",
                "       \"thunder\"" + ":" + "\"" + thunder + "\"" + ",",
                "       \"tornado\"" + ":" + "\"" + tornado + "\"",
                "   },",
                "   \"clouds\"" + ":" + _measurement.getClouds() + ",",
                "   \"wind_direction\"" + ":" + _measurement.getWindDirection(),
                "}");

            Path file = Paths.get(SAVE_LOCATION + "/" + _measurement.getStation() + "/" + _measurement.getDate() + "/" + _measurement.getTime().replace(":" , "-") + ".json");
            Files.createDirectories(file.getParent());
            Files.createFile(file);
            Files.write(file, lines, Charset.forName("UTF-8"));
        }
        catch (IOException | StringIndexOutOfBoundsException e) {
            Util.throwError("Unable to accept request", e.getMessage());
            e.printStackTrace();
        }
    }
}
