/*
 * Vancouver
 *
 * @version     1.0 Alpha 1
 * @author      Aeros Development
 * @copyright   2017, Vancouver
 *s
 * @license     Apache 2.0
 */

package com.aeros.models;

import com.aeros.main.Util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * This class is used for storing and validating measurements
 * @since 1.0 Beta 1
 * @author Aeros Development
 */
public class Measurement {

    /**
     * This object is needed for making sure floats have the right number of decimals
     */
    private DecimalFormat _decimalFormat;

    /**
     * The station id
     */
    private int _station;

    /**
     * The date this measurement was recorded
     */
    private String _date;

    /**
     * The time this measurement was recorded
     */
    private String _time;

    /**
     * The current temperature
     */
    private float _temperature;

    /**
     * The current dew point
     */
    private float _dewPoint;

    /**
     * The current station pressure
     */
    private float _stationPressure;

    /**
     * The current sea level pressure
     */
    private float _seaLevelPressure;

    /**
     * The current visibility in km
     */
    private float _visibility;

    /**
     * The current wind speed in km/h
     */
    private float _windSpeed;

    /**
     * The current precipitation level in mm
     */
    private float _precipitation;

    /**
     * The current snowfall
     */
    private float _snowfall;

    /**
     * Binary station flags (6 chars)
     */
    private String _flags;

    /**
     * Cloudiness at the time of measuring
     */
    private float _clouds;

    /**
     * The current wind direction
     */
    private int _windDirection;

    /**
     * If the data is corrupt and cannot be recovered, this is set to true
     */
    private boolean _isCorrupt = false;

    /**
     * Creates a new instance of the Measurement class
     */
    public Measurement() {
        _decimalFormat = new DecimalFormat("#.#");
        _decimalFormat.setRoundingMode(RoundingMode.CEILING);
    }

    /**
     * Checks if the reading wasn't corrupted and if we have a valid station id
     * @return Whether or not the measurement is valid
     */
    public boolean isValid() {
        return _station < 999999 && _station != 0 && !_isCorrupt;
    }

    /**
     * Rounds floats to one decimal
     * @param value The value that should be converted
     * @return The converted float (with one decimal)
     */
    private float withOneDecimal(float value) {
        Float result = 0.0f;

        try {
            result = Float.parseFloat(_decimalFormat.format(value));
        }

        catch (NumberFormatException e) {
            Util.throwError("Invalid number format.");
            e.printStackTrace();
            _isCorrupt = true;
        }

        return result;
    }

    /**
     * Checks the current reading and corrects it if needed
     * @param measurements The last 5 measurements
     */
    public void checkAndFixReadings(Measurement[] measurements) {
        float averageTemperature = 0;
        float averageDewPoint = 0;
        float averageStationPressure = 0;
        float averageSeaLevelPressure = 0;
        float averageVisibility = 0;
        float averageWindSpeed = 0;
        float averagePrecipitation = 0;
        float averageSnowFall = 0;

        int readings = 0;

        if (measurements == null)
            return;

        for (Measurement measurement : measurements) {
            if (measurement == null)
                continue;

            averageTemperature += measurement.getTemperature();
            averageDewPoint += measurement.getDewPoint();
            averageStationPressure += measurement.getStationPressure();
            averageSeaLevelPressure += measurement.getSeaLevelPressure();
            averageVisibility += measurement.getVisibility();
            averageWindSpeed += measurement.getWindSpeed();
            averagePrecipitation += measurement.getPrecipitation();
            averageSnowFall += measurement.getSnowfall();

            readings++;
        }

        averageTemperature /= readings;
        averageDewPoint /= readings;
        averageStationPressure /= readings;
        averageSeaLevelPressure /= readings;
        averageVisibility /= readings;
        averageWindSpeed /= readings;
        averagePrecipitation /= readings;
        averageSnowFall /= readings;

        if (_temperature < 0.8 * averageTemperature || _temperature > 1.2 * averageTemperature || _temperature < -9999.9 || _temperature > 9999.9)
            _temperature = averageTemperature;

        if (_dewPoint < 0.8 * averageDewPoint || _dewPoint > 1.2 * averageDewPoint || _dewPoint < -9999.9 || _dewPoint > 9999.9)
            _dewPoint = averageDewPoint;

        if (_stationPressure < 0.8 * averageStationPressure || _stationPressure > 1.2 * averageStationPressure || _stationPressure < 0 || _stationPressure > 9999.9)
            _stationPressure = averageStationPressure;

        if (_seaLevelPressure < 0.8 * averageSeaLevelPressure || _seaLevelPressure > 1.2 * averageSeaLevelPressure || _seaLevelPressure < 0 || _seaLevelPressure > 9999.9)
            _seaLevelPressure = averageSeaLevelPressure;

        if (_visibility < 0.8 * averageVisibility || _visibility > 1.2 * averageVisibility || _visibility < 0 || _visibility > 999.9)
            _visibility = averageVisibility;

        if (_windSpeed < 0.8 * averageWindSpeed || _windSpeed > 1.2 * averageWindSpeed || _windSpeed < 0 || _windSpeed > 999.9)
            _windSpeed = averageWindSpeed;

        if (_precipitation < 0.8 * averagePrecipitation || _precipitation > 1.2 * averagePrecipitation || _precipitation < 0 || _precipitation > 999.9)
            _precipitation = averagePrecipitation;

        if (_snowfall < 0.8 * averageSnowFall || _snowfall > 1.2 * averageSnowFall || _snowfall < -9999.9 || _snowfall > 9999.9)
            _snowfall = averageSnowFall;

        if (_flags.length() != 6)
            _flags = measurements[0] != null ? measurements[0].getFlags() : "000000";
    }

    /**
     * @return The current station id
     */
    public int getStation() {
        return _station;
    }

    /**
     * @return The current date
     */
    public String getDate() {
        return _date;
    }

    /**
     * @return The current time
     */
    public String getTime() {
        return _time;
    }

    /**
     * @return The current temperature
     */
    public float getTemperature() {
        return _temperature;
    }

    /**
     * @return The current dew point
     */
    public float getDewPoint() {
        return _dewPoint;
    }

    /**
     * @return The current station pressure
     */
    public float getStationPressure() {
        return _stationPressure;
    }

    /**
     * @return The current sea level pressure
     */
    public float getSeaLevelPressure() {
        return _seaLevelPressure;
    }

    /**
     * @return The current visibility
     */
    public float getVisibility() {
        return _visibility;
    }

    /**
     * @return The current wind speed
     */
    public float getWindSpeed() {
        return _windSpeed;
    }

    /**
     * @return The current precipitation level
     */
    public float getPrecipitation() {
        return _precipitation;
    }

    /**
     * @return The current snowfall in mm
     */
    public float getSnowfall() {
        return _snowfall;
    }

    /**
     * @return The (binary) flag string
     */
    public String getFlags() {
        return _flags;
    }

    /**
     * @return The current cloudiness
     */
    public float getClouds() {
        return _clouds;
    }

    /**
     * @return The current wind direction
     */
    public int getWindDirection() {
        return _windDirection;
    }

    /**
     * Sets the current station id
     * @param station A station id
     */
    public void setStation(int station) {
        _station = station;
    }

    /**
     * Sets the current date
     * @param date Get this: the date
     */
    public void setDate(String date) {
        _date = date;
    }

    /**
     * Sets the current time
     * @param time Believe it or not, but this should be the time
     */
    public void setTime(String time) {
        _time = time;
    }

    /**
     * Sets the current temperature
     * @param temperature The temperature
     */
    public void setTemperature(float temperature) {
        _temperature = withOneDecimal(temperature);
    }

    /**
     * Sets the current dew point
     * @param dewPoint The dew point
     */
    public void setDewPoint(float dewPoint) {
        _dewPoint = withOneDecimal(dewPoint);
    }

    /**
     * Sets the current station pressure
     * @param stationPressure The station pressure
     */
    public void setStationPressure(float stationPressure) {
        _stationPressure = withOneDecimal(stationPressure);
    }

    /**
     * Sets the current sea level pressure
     * @param seaLevelPressure The sea level pressure
     */
    public void setSeaLevelPressure(float seaLevelPressure) {
        _seaLevelPressure = withOneDecimal(seaLevelPressure);
    }

    /**
     * Sets the current visibility
     * @param visibility The visibility
     */
    public void setVisibility(float visibility) {
        _visibility = withOneDecimal(visibility);
    }

    /**
     * Sets the current wind speed
     * @param windSpeed The wind speed
     */
    public void setWindSpeed(float windSpeed) {
        _windSpeed = withOneDecimal(windSpeed);
    }

    /**
     * Sets the current precipitation level
     * @param precipitation The precipitation level
     */
    public void setPrecipitation(float precipitation) {
        _precipitation = withOneDecimal(precipitation);
    }

    /**
     * Sets the current level of snowfall
     * @param snowfall The level of snowfall
     */
    public void setSnowfall(float snowfall) {
        _snowfall = withOneDecimal(snowfall);
    }

    /**
     * Sets the current flags
     * @param flags The flags
     */
    public void setFlags(String flags) {
        _flags = flags;
    }

    /**
     * Sets the degree of cloudiness
     * @param clouds The degree of cloudiness
     */
    public void setClouds(float clouds) {
        _clouds = withOneDecimal(clouds);
    }

    /**
     * Sets the wind direction
     * @param windDirection The wind direction
     */
    public void setWindDirection(int windDirection) {
        _windDirection = windDirection;
    }
}
