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

import java.util.Date;

public class Measurement {

    private int _station;
    private Date _date;
    private Date _time;
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

    public int getStation() {
        return _station;
    }

    public Date getDate() {
        return _date;
    }

    public Date getTime() {
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

    public float getfallenSnow() {
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

    public void setDate(Date date) {
        _date = date;
    }

    public void setTime(Date time) {
        _time = time;
    }

    public void setTemperature(float temperature) {
        _temperature = temperature;
    }

    public void setDewPoint(float dewPoint) {
        _dewPoint = dewPoint;
    }

    public void setStationPressure(float stationPressure) {
        _stationPressure = stationPressure;
    }

    public void setSeaLevelPressure(float seaLevelPressure) {
        _seaLevelPressure = seaLevelPressure;
    }

    public void setVsibility(float visibility) {
        _visibility = visibility;
    }

    public void setWindSpeed(float windSpeed) {
        _windSpeed = windSpeed;
    }

    public void setPrecipitation(float precipitation) {
        _precipitation = precipitation;
    }

    public void setFallenSnow(float fallenSnow) {
        _fallenSnow = fallenSnow;
    }

    public void setFlags(String flags) {
        _flags = flags;
    }

    public void setClouds(float clouds) {
        _clouds = clouds;
    }

    public void setWindDirection(int windDirection) {
        _windDirection = windDirection;
    }
}
