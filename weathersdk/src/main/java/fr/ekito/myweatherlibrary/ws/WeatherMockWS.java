package fr.ekito.myweatherlibrary.ws;

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import fr.ekito.myweatherlibrary.di.Inject;
import fr.ekito.myweatherlibrary.json.geocode.Geocode;
import fr.ekito.myweatherlibrary.json.geocode.Location;
import fr.ekito.myweatherlibrary.json.weather.Weather;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by arnaud on 14/02/2017.
 */

public class WeatherMockWS implements WeatherWS {

    private static final String TAG = WeatherMockWS.class.getSimpleName();
    private Gson gson = new Gson();
    private Map<Location, String> cities = new HashMap<>();

    public WeatherMockWS() {
        try {
            String[] list = Inject.getApplicationContext().getAssets().list("json");
            for (String fn : list) {
                if (fn.startsWith("geocode_")) {
                    Geocode geocode = gson.fromJson(readJsonFile(fn), Geocode.class);
                    String name = fn.replace("geocode_", "").replace(".json", "");
                    cities.put(geocode.getResults().get(0).getGeometry().getLocation(), name);
                }
            }
            Log.i(TAG, "Can respond for cities : " + cities);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String readJsonFile(String jsonFile) {
        StringBuilder buf = new StringBuilder();
        try {
            InputStream json = Inject.getApplicationContext().getAssets().open("json/" + jsonFile);
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(json, "UTF-8"));
            String str;
            while ((str = in.readLine()) != null) {
                buf.append(str);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buf.toString();
    }

    @Override
    public Observable<Geocode> geocode(final String address) {
        return Observable.create(new Observable.OnSubscribe<Geocode>() {
            @Override
            public void call(Subscriber<? super Geocode> subscriber) {
                String adrs = address.toLowerCase();
                Geocode geocode;
                if (isKnownCity(adrs)) {
                    geocode = gson.fromJson(readJsonFile("geocode_" + adrs + ".json"), Geocode.class);
                } else {
                    geocode = gson.fromJson(readJsonFile("geocode_toulouse.json"), Geocode.class);
                }
                if (geocode != null) {
                    subscriber.onNext(geocode);
                }
                subscriber.onCompleted();
            }
        });
    }

    private boolean isKnownCity(String adrs) {
        return cities.values().contains(adrs);
    }

    private String cityFromLocation(final Double lat, final Double lng) {
        String city = "toulouse";
        for (Location location : cities.keySet()) {
            if (location.getLat().equals(lat) && location.getLng().equals(lng)) {
                city = cities.get(location);
            }
        }
        return city;
    }

    @Override
    public Observable<Weather> weather(final Double lat, final Double lng, String lang) {
        return Observable.create(new Observable.OnSubscribe<Weather>() {
            @Override
            public void call(Subscriber<? super Weather> subscriber) {
                String city = cityFromLocation(lat, lng);
                Weather weather = gson.fromJson(readJsonFile("weather_" + city + ".json"), Weather.class);
                if (weather != null) {
                    subscriber.onNext(weather);
                }
                subscriber.onCompleted();
            }
        });
    }
}
