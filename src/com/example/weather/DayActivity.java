package com.example.weather;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;


public class DayActivity extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dayfull);


        TextView textViewDate = (TextView) findViewById(R.id.textViewDateFull);
        TextView textViewHumidity = (TextView) findViewById(R.id.textViewHumidity);
        TextView textViewPressure = (TextView) findViewById(R.id.textViewPressure);
        TextView textViewWindDirection = (TextView) findViewById(R.id.textViewWindDirection);
        TextView textViewWindFull = (TextView) findViewById(R.id.textViewWindFull);
        TextView textViewLowFull = (TextView) findViewById(R.id.textViewLowFull);
        TextView textViewHighFull = (TextView) findViewById(R.id.textViewHighFull);
        TextView textViewCloudsFull = (TextView) findViewById(R.id.textViewCloudsFull);
        TextView textViewNameFull = (TextView) findViewById(R.id.textViewNameFull);
        ImageView imageView = (ImageView) findViewById(R.id.imageViewFull);

        textViewDate.setText(getIntent().getStringExtra(Day.DATE));
        textViewHumidity.setText(getIntent().getStringExtra(Day.HUMIDITY));
        textViewPressure.setText(getIntent().getStringExtra(Day.PRESSURE));
        textViewWindDirection.setText(getIntent().getStringExtra(Day.WIND_DIRECTION));
        textViewWindFull.setText(getIntent().getStringExtra(Day.WIND_SPEED));
        textViewLowFull.setText(getIntent().getStringExtra(Day.LOW_TEMPERATURE));
        textViewHighFull.setText(getIntent().getStringExtra(Day.HIGH_TEMPERATURE));
        textViewCloudsFull.setText(getIntent().getStringExtra(Day.CLOUDS));
        textViewNameFull.setText(getIntent().getStringExtra(Town.NAME));

        try {
            Field field = R.drawable.class.getField("w" + getIntent().getIntExtra(Day.IMAGE, 3200));
            imageView.setImageResource(field.getInt(null));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            imageView.setImageResource(R.drawable.w3200);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            imageView.setImageResource(R.drawable.w3200);
        }
    }
}