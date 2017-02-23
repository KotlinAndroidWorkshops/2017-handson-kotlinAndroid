package fr.ekito.myweatherapp;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import fr.ekito.myweatherlibrary.WeatherSDK;
import fr.ekito.myweatherlibrary.json.geocode.Geocode;
import fr.ekito.myweatherlibrary.json.geocode.Location;
import fr.ekito.myweatherlibrary.json.weather.Weather;
import rx.Observable;
import rx.Observer;
import rx.functions.Func1;

import static fr.ekito.myweatherapp.R.id.weather_icon_day1;
import static fr.ekito.myweatherapp.R.id.weather_icon_day2;
import static fr.ekito.myweatherapp.R.id.weather_icon_day3;
import static fr.ekito.myweatherapp.R.id.weather_forecast_day1;
import static fr.ekito.myweatherapp.R.id.weather_forecast_day2;
import static fr.ekito.myweatherapp.R.id.weather_forecast_day3;
import static fr.ekito.myweatherapp.R.id.weather_loadlayout;
import static fr.ekito.myweatherapp.R.id.weather_forecast_today;
import static fr.ekito.myweatherapp.R.id.weather_icon_today;
import static fr.ekito.myweatherapp.R.id.weather_mainlayout;
import static fr.ekito.myweatherapp.R.id.weather_temp_day1;
import static fr.ekito.myweatherapp.R.id.weather_temp_day2;
import static fr.ekito.myweatherapp.R.id.weather_temp_day3;

public class MainActivity extends AppCompatActivity implements MainActivityWeatherCallback {

    private Toolbar toolbar;

    private TextView forecastToday;
    private IconTextView iconToday;

    private TextView tempDay1;
    private IconTextView iconDay1;
    private TextView forecastDay1;

    private TextView tempDay2;
    private IconTextView iconDay2;
    private TextView forecastDay2;

    private TextView tempDay3;
    private IconTextView iconDay3;
    private TextView forecastDay3;

    private TextView title;

    private ConstraintLayout loadLayout;
    private ConstraintLayout mainLayout;

    private Date now = new Date();

    private void findViewsById() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        title = (TextView) findViewById(R.id.weather_title);

        iconToday = (IconTextView) findViewById(weather_icon_today);
        forecastToday = (TextView) findViewById(weather_forecast_today);

        iconDay1 = (IconTextView) findViewById(weather_icon_day1);
        forecastDay1 = (TextView) findViewById(weather_forecast_day1);
        tempDay1 = (TextView) findViewById(weather_temp_day1);

        iconDay2 = (IconTextView) findViewById(weather_icon_day2);
        forecastDay2 = (TextView) findViewById(weather_forecast_day2);
        tempDay2 = (TextView) findViewById(weather_temp_day2);

        iconDay3 = (IconTextView) findViewById(weather_icon_day3);
        forecastDay3 = (TextView) findViewById(weather_forecast_day3);
        tempDay3 = (TextView) findViewById(weather_temp_day3);

        loadLayout = (ConstraintLayout) findViewById(weather_loadlayout);
        mainLayout = (ConstraintLayout) findViewById(weather_mainlayout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewsById();

        setSupportActionBar(toolbar);

        loadLayout.setVisibility(View.VISIBLE);
        mainLayout.setVisibility(View.GONE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                DialogHelper.locationDialog(view, MainActivity.this);
            }
        });
    }

    /**
     * Retrieve Weather Data from the REST API
     */
    @Override
    public void getWeatherData(final View view, final String location) {

        Snackbar.make(view, "Getting your weather :)", Snackbar.LENGTH_SHORT).show();

        WeatherSDK.getGeocode(location)
                .map(new Func1<Geocode, Location>() {
                    @Override
                    public Location call(Geocode geocode) {
                        return WeatherSDKUtil.extractLocation(geocode);
                    }
                })
                .switchMap(new Func1<Location, Observable<Weather>>() {
                    @Override
                    public Observable<Weather> call(Location location) {
                        return WeatherSDK.getWeather(location.getLat(), location.getLng());
                    }
                })
                .timeout(40, TimeUnit.SECONDS)
                .subscribe(new Observer<Weather>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable error) {
                        Snackbar.make(view, "Weather Error : " + error, Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(Weather weather) {
                        updateWeatherUI(weather, location);
                    }
                });

    }

    public void updateWeatherUI(Weather weather, String location) {

        if (weather != null) {
            loadLayout.setVisibility(View.GONE);
            mainLayout.setVisibility(View.VISIBLE);

            DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(MainApplication.get());
            DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(MainApplication.get());
            title.setText(getString(R.string.weather_title) + " " + location + "\n" + dateFormat.format(now) + " " + timeFormat.format(now));

            List<DailyForecastModel> forecasts = WeatherSDKUtil.getDailyForecasts(weather);

            if (forecasts.size() == 4) {
                setForecastForToday(forecasts.get(0), forecastToday, iconToday);
                setForecastForDayX(forecasts.get(1), 1, forecastDay1, iconDay1, tempDay1);
                setForecastForDayX(forecasts.get(2), 2, forecastDay2, iconDay2, tempDay2);
                setForecastForDayX(forecasts.get(3), 3, forecastDay3, iconDay3, tempDay3);
            }

        }
    }

    private void setForecastForToday(DailyForecastModel dayX, TextView forecastDayX, IconTextView iconDayX) {
        forecastDayX.setText("Today : " + dayX.getForecastString() + "\n" + dayX.getTemperatureString());
        iconDayX.setText(dayX.getIcon());
    }

    private void setForecastForDayX(DailyForecastModel dayX, int idx, TextView forecastDayX, IconTextView iconDayX, TextView tempDayX) {
        forecastDayX.setText("Day " + idx + ": " + dayX.getForecastString());
        if (tempDayX != null) {
            tempDayX.setText(dayX.getTemperatureString());
        }
        iconDayX.setText(dayX.getIcon());
    }
}
