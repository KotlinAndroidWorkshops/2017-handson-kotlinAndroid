package fr.ekito.myweatherapp;

import android.view.View;

public interface MainActivityWeatherCallback {
    void getWeatherData(View view, String location);
}
