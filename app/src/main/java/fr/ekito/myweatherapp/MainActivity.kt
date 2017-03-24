package fr.ekito.myweatherapp

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.joanzapata.iconify.widget.IconTextView
import fr.ekito.myweatherlibrary.WeatherSDK
import fr.ekito.myweatherlibrary.json.geocode.Geocode
import fr.ekito.myweatherlibrary.json.geocode.getLocation
import fr.ekito.myweatherlibrary.json.weather.Weather
import fr.ekito.myweatherlibrary.json.weather.getDailyForecasts
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val now = Date()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        weather_main_layout.visibility = View.GONE
        weather_forecast_layout.visibility = View.GONE

        fab.setOnClickListener { view -> DialogHelper.locationDialog(view, {location -> getWeatherData(view, location)}) }
    }

    /**
     * Retrieve Weather Data from the REST API
     */
    fun getWeatherData(view: View, location: String) {

        Snackbar.make(view, "Getting your weather :)", Snackbar.LENGTH_SHORT).show()

        WeatherSDK.getGeocode(location)
                .map(Geocode::getLocation)
                .switchMap { location -> WeatherSDK.getWeather(location!!.lat, location.lng) }
                .timeout(40, TimeUnit.SECONDS)
                .subscribe(
                        { weather -> updateWeatherUI(weather, location) },
                        { error -> Snackbar.make(view, "Weather Error : " + error, Snackbar.LENGTH_LONG).show() })
    }

    fun updateWeatherUI(weather: Weather?, location: String) {

        if (weather != null) {
            weather_main_layout.visibility = View.VISIBLE
            weather_forecast_layout.visibility = View.VISIBLE

            val timeFormat = android.text.format.DateFormat.getTimeFormat(MainApplication.get())
            val dateFormat = android.text.format.DateFormat.getDateFormat(MainApplication.get())
            weather_title.text = getString(R.string.weather_title) + " " + location + "\n" + dateFormat.format(now) + " " + timeFormat.format(now)

            val forecasts = weather.getDailyForecasts()

            if (forecasts.size == 4) {
                setForecastForToday(forecasts[0], weather_main_text, weather_main_icon)
                setForecastForDayX(forecasts[1], 1, weather_forecast_day1, weather_icon_day1, weather_temp_day1)
                setForecastForDayX(forecasts[2], 2, weather_forecast_day2, weather_icon_day2, weather_temp_day2)
                setForecastForDayX(forecasts[3], 3, weather_forecast_day3, weather_icon_day3, weather_temp_day3)
            }

        }
    }

    private fun setForecastForToday(dayX: DailyForecastModel, forecastDayX: TextView, iconDayX: IconTextView) {
        forecastDayX.text = "Today : " + dayX.forecastString + "\n" + dayX.temperatureString
        iconDayX.text = dayX.icon
    }

    private fun setForecastForDayX(dayX: DailyForecastModel, idx: Int, forecastDayX: TextView, iconDayX: IconTextView, tempDayX: TextView?) {
        forecastDayX.text = "Day " + idx + ": " + dayX.forecastString
        if (tempDayX != null) {
            tempDayX.text = dayX.temperatureString
        }
        iconDayX.text = dayX.icon
    }
}
