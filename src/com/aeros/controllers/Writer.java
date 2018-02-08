package com.aeros.controllers;

import com.aeros.main.Util;
import com.aeros.models.Measurement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes measurements to a .json file.
 * @since 1.0 Beta 1
 * @author Aeros Development
 */
class Writer {

    /**
     * This ArrayList contains the measurements that should be written to file
     */
    private ArrayList<Measurement> _measurements;

    /**
     * The .json files are saved in this directory
     */
    private static final String SAVE_LOCATION = "/mnt"; // Replace this if your webdav folder isn't located here

    /**
     * What time (minute) did the last reading return?
     */
    private String _lastMinute;

    /**
     * What time (hour) did the last reading return?
     */
    private String _lastHour;

    /**
     * What date did the last reading return?
     */
    private String _lastDate;

    /**
     * The id of the station
     */
    private int _stationId;

    /**
     * Creates a new instance of the Writer class
     * @param stationId The station id
     */
    Writer(int stationId) {
        _measurements = new ArrayList<>();
        _stationId = stationId;
    }

    /**
     * Writers all measurements of the last minute to file
     */
    private void write() {
        try {
            List<String> lines = new ArrayList<>();

            for (Measurement measurement : _measurements) {
                String _flags = measurement.getFlags();

                String freeze = _flags.charAt(0) == '0' ? "false" : "true";
                String rain = _flags.charAt(1) == '0' ? "false" : "true";
                String snow = _flags.charAt(2) == '0' ? "false" : "true";
                String hail = _flags.charAt(3) == '0' ? "false" : "true";
                String thunder = _flags.charAt(4) == '0' ? "false" : "true";
                String tornado = _flags.charAt(5) == '0' ? "false" : "true";

                // If the following code makes you vomit: you're not the only one. It works, though.
                lines.add("\t{\n" +
                    "\t\t\"station\": " + measurement.getStation() + ",\n" +
                    "\t\t\"date\": \"" + measurement.getDate() + "\",\n" +
                    "\t\t\"time\": \"" + measurement.getTime() + "\",\n" +
                    "\t\t\"temperature\": " + measurement.getTemperature() + ",\n" +
                    "\t\t\"dew_point\": " + measurement.getDewPoint() + ",\n" +
                    "\t\t\"station_pressure\": " + measurement.getStationPressure() + ",\n" +
                    "\t\t\"sea_level_pressure\": " + measurement.getSeaLevelPressure() + ",\n" +
                    "\t\t\"visibility\": " + measurement.getVisibility() + ",\n" +
                    "\t\t\"wind_speed\": " + measurement.getWindSpeed() + ",\n" +
                    "\t\t\"precipitation\": " + measurement.getPrecipitation() + ",\n" +
                    "\t\t\"snowfall\": " + measurement.getSnowfall() + ",\n" +
                    "\t\t\"flags\": {\n" +
                    "\t\t\t\"froze\": \"" + freeze + "\",\n" +
                    "\t\t\t\"rained\": \"" + rain + "\",\n" +
                    "\t\t\t\"snowed\": \"" + snow + "\",\n" +
                    "\t\t\t\"hailed\": \"" + hail + "\",\n" +
                    "\t\t\t\"thunder\": \"" + thunder + "\",\n" +
                    "\t\t\t\"tornado\": \"" + tornado + "\"\n" +
                    "\t\t},\n" +
                    "\t\t\"clouds\": " + measurement.getClouds() + ",\n" +
                    "\t\t\"wind_direction\": " + measurement.getWindDirection() + "\n" +
                    "\t}");
            }

            String saveLocation = SAVE_LOCATION + "/" + _stationId + "/" + _lastDate + "/" + _lastHour + "_" + _lastMinute + ".json";
            Path file = Paths.get(saveLocation);

            Files.createDirectories(file.getParent());
            Files.createFile(file);
            FileWriter fileWriter = new FileWriter(saveLocation);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write("[\n");
            bufferedWriter.write(String.join(",\n", lines));
            bufferedWriter.write("\n]");
            bufferedWriter.flush();
            bufferedWriter.close();
        }
        catch (IOException | StringIndexOutOfBoundsException e) {
            if (e instanceof StringIndexOutOfBoundsException)
                Util.throwError("Unable to accept request");
            else
                Util.throwError("Unable to save file");

            e.printStackTrace();
        }
    }

    /**
     * Adds a measurement to the _measurements array. This method also triggers the write() method if needed.
     * @param measurement The measurement
     */
    void addMeasurement(Measurement measurement) {
        String[] parsedTime = measurement.getTime().split(":");

        String lastHour = parsedTime[0];
        String lastMinute = parsedTime[1];

        if (_lastMinute == null || _lastHour == null || _lastDate == null) {
            _lastMinute = lastMinute;
            _lastHour = lastHour;
            _lastDate = measurement.getDate();
        }

        if (!_lastMinute.equals(lastMinute)) {
            write();
            _lastMinute = lastMinute;
            _lastHour = lastHour;
            _lastDate = measurement.getDate();
            _measurements = new ArrayList<>();
        }

        _measurements.add(measurement);
    }
}
