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

    private List<Measurement> _measurements;
    private Measurement _measurement;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName.toLowerCase()) {
            case "measurement":
                _measurement = new Measurement();
                break;
            default:
                Util.printStatus(String.format("Unknown element: %s", qName));
        }
    }

    public List<Measurement> getMeasurements() {
        return _measurements;
    }
}
