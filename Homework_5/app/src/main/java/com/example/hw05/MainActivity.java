package com.example.hw05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements CitiesFragment.CitiesListerner, WeatherFragment.WeatherListerner {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new CitiesFragment())
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void cityName(DataService.City city) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, WeatherFragment.newInstance(city))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void moreDetail(DataService.City city) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new ForecastFragment())
                .addToBackStack(null)
                .commit();
    }
}