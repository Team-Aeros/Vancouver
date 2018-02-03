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

public class Measurement {

    private DecimalFormat _decimalFormat;

    private int _station;
    private String _date;
    private String _time;
    private float _temperature;
    private float _dewPoint;

    private float _stationPressure;
    private float _seaLevelPressure;

    private float _visibility;
    private float _windSpeed;
    private float _precipitation;
    private float _fallenSnow;

    private String _flags;
    private float _clouds;
    private int _windDirection;

    private boolean _isCorrupt = false;

    public Measurement() {
        _decimalFormat = new DecimalFormat("#.#");
        _decimalFormat.setRoundingMode(RoundingMode.CEILING);
    }

    public boolean isValid() {
        return _station < 999999 && _station != 0;
    }

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
            averageSnowFall += measurement.getFallenSnow();

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

        if (_fallenSnow < 0.8 * averageSnowFall || _fallenSnow > 1.2 * averageSnowFall || _fallenSnow < -9999.9 || _fallenSnow > 9999.9)
            _fallenSnow = averageSnowFall;

        if (_flags.length() != 6)
            _flags = measurements[0] != null ? measurements[0].getFlags() : "000000";
    }

    public int getStation() {
        return _station;
    }

    public String getDate() {
        return _date;
    }

    public String getTime() {
        return _time;
    }

    public float getTemperature() {
        return _temperature;
    }

    public float getDewPoint() {
        return _dewPoint;
    }

    public float getStationPressure() {
        return _stationPressure;
    }

    public float getSeaLevelPressure() {
        return _seaLevelPressure;
    }

    public float getVisibility() {
        return _visibility;
    }

    public float getWindSpeed() {
        return _windSpeed;
    }

    public float getPrecipitation() {
        return _precipitation;
    }

    public float getFallenSnow() {
        return _fallenSnow;
    }

    public String getFlags() {
        return _flags;
    }

    public float getClouds() {
        return _clouds;
    }

    public int getWindDirection() {
        return _windDirection;
    }

    public void setStation(int station) {
        _station = station;
    }

    public void setDate(String date) {
        _date = date;
    }

    public void setTime(String time) {
        _time = time;
    }

    public void setTemperature(float temperature) {
        _temperature = withOneDecimal(temperature);
    }

    public void setDewPoint(float dewPoint) {
        _dewPoint = withOneDecimal(dewPoint);
    }

    public void setStationPressure(float stationPressure) {
        _stationPressure = withOneDecimal(stationPressure);
    }

    public void setSeaLevelPressure(float seaLevelPressure) {
        _seaLevelPressure = withOneDecimal(seaLevelPressure);
    }

    public void setVisibility(float visibility) {
        _visibility = withOneDecimal(visibility);
    }

    public void setWindSpeed(float windSpeed) {
        _windSpeed = withOneDecimal(windSpeed);
    }

    public void setPrecipitation(float precipitation) {
        _precipitation = withOneDecimal(precipitation);
    }

    public void setFallenSnow(float fallenSnow) {
        _fallenSnow = withOneDecimal(fallenSnow);
    }

    public void setFlags(String flags) {
        _flags = flags;
    }

    public void setClouds(float clouds) {
        _clouds = withOneDecimal(clouds);
    }

    public void setWindDirection(int windDirection) {
        _windDirection = windDirection;
    }
}
