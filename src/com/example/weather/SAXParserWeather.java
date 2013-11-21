package com.example.weather;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.ArrayList;

public class SAXParserWeather extends MySAXParser {

    Day day;

    @Override
    public void startDocument() throws SAXException {
        array = new ArrayList<Object>();
        System.out.println("Start document weather");
        day = new Day();
    }

    @Override
    public void startElement(String uri, String local_name, String raw_name, Attributes amap) throws SAXException {

        System.out.println("Start element:" + local_name);
        if ("yweather:wind".equals(local_name) || "wind".equals(local_name)) {
            day.wind_speed = amap.getValue(amap.getIndex("speed"));
            day.wind_direction = amap.getValue(amap.getIndex("direction"));
        } else if ("yweather:atmosphere".equals(local_name) || "atmosphere".equals(local_name)) {
            day.humidity = amap.getValue(amap.getIndex("humidity"));
            day.pressure = amap.getValue(amap.getIndex("pressure"));
        } else if ("yweather:forecast".equals(local_name) || "forecast".equals(local_name)) {
            day.date = amap.getValue(amap.getIndex("date"));
            day.low_temperature = amap.getValue(amap.getIndex("low"));
            day.high_temperature = amap.getValue(amap.getIndex("high"));
            day.clouds = amap.getValue(amap.getIndex("text"));
            day.image = Integer.parseInt(amap.getValue(amap.getIndex("code")));
            array.add(day);
            day = new Day();
        }

    }

}
