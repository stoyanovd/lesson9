package com.example.weather;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class TownUpdater {

    Town town;
    Context context;

    public TownUpdater(Town _town, Context _context) {
        town = _town;
        context = _context;
    }

    public boolean update() throws ParserConfigurationException, XmlPullParserException, SAXException, IOException {
        if (town.fakeTown())
            return true;

        SAXParserWeather saxParserWeather = new SAXParserWeather();
        Downloader downloader = new Downloader(Downloader.weatherRequest(town.woeid), saxParserWeather);

        boolean res = false;

        MyDataBaseTownHelper myDataBaseTownHelper = new MyDataBaseTownHelper(context, town.id);
        SQLiteDatabase sqLiteDatabase = myDataBaseTownHelper.getWritableDatabase();

        try {
            sqLiteDatabase.execSQL(MyDataBaseTownHelper.DROP_DATABASE_NO_ID + town.id);
            sqLiteDatabase.execSQL(MyDataBaseTownHelper.CREATE_DATABASE_BEFORE_ID + town.id + MyDataBaseTownHelper.CREATE_DATABASE_AFTER_ID);

            for (int i = 0; i < saxParserWeather.array.size(); i++) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(MyDataBaseTownHelper._ID, i + 1);                       //   from one!!!!
                contentValues.put(MyDataBaseTownHelper.DATE, ((Day) saxParserWeather.array.get(i)).date);
                contentValues.put(MyDataBaseTownHelper.HUMIDITY, ((Day) saxParserWeather.array.get(i)).humidity);
                contentValues.put(MyDataBaseTownHelper.PRESSURE, ((Day) saxParserWeather.array.get(i)).pressure);
                contentValues.put(MyDataBaseTownHelper.WIND_DIRECTION, ((Day) saxParserWeather.array.get(i)).wind_direction);
                contentValues.put(MyDataBaseTownHelper.WIND_SPEED, ((Day) saxParserWeather.array.get(i)).wind_speed);
                contentValues.put(MyDataBaseTownHelper.LOW_TEMPERATURE, ((Day) saxParserWeather.array.get(i)).low_temperature);
                contentValues.put(MyDataBaseTownHelper.HIGH_TEMPERATURE, ((Day) saxParserWeather.array.get(i)).high_temperature);
                contentValues.put(MyDataBaseTownHelper.CLOUDS, ((Day) saxParserWeather.array.get(i)).clouds);
                contentValues.put(MyDataBaseTownHelper.IMAGE, ((Day) saxParserWeather.array.get(i)).image);
                sqLiteDatabase.insert(MyDataBaseTownHelper.DATABASE_NAME_NO_ID + town.id, null, contentValues);
            }
            res = true;
        } catch (Exception e) {
            res = false;
        } finally {
            sqLiteDatabase.close();
            myDataBaseTownHelper.close();
        }
        return res;
    }

}