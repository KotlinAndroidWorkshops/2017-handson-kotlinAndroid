package fr.ekito.myweatherapp;

import android.app.Application;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.WeathericonsModule;

import fr.ekito.myweatherlibrary.WeatherSDK;

public class MainApplication extends Application {

    private static MainApplication instance;

    public static Application get() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        WeatherSDK.init(this);
        Iconify.with(new WeathericonsModule());
        instance = this;
    }
}
