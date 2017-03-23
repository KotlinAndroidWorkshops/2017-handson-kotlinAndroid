package fr.ekito.myweatherapp

import fr.ekito.myweatherlibrary.json.geocode.Geocode
import fr.ekito.myweatherlibrary.json.geocode.Location
import fr.ekito.myweatherlibrary.json.weather.Weather
import java.util.*

object WeatherSDKUtil {

    private val PREFIX = "nt_"

    private val CHANCE_FLURRIES = "chanceflurries"  // wi-snow-wind
    private val CHANCE_RAIN = "chancerain"          // wi-rain
    private val CHANCE_SLEET = "chancesleet"        // wi-rain-mix
    private val CHANCE_SNOW = "chancesnow"          // wi-snow
    private val CHANCE_STORMS = "chancestorms"      // wi-thunderstorm

    private val CLEAR = "clear"                     // wi-day-sunny
    private val CLOUDY = "cloudy"                   // wi-cloudy
    private val FLURRIES = "flurries"               // wi-snow-wind
    private val FOG = "fog"                         // wi-fog
    private val HAZY = "hazy"                       // wi-fog

    private val MOSTLY_CLOUDY = "mostlycloudy"      // wi-day-cloudy
    private val MOSTLY_SUNNY = "mostlysunny"        // wi-day-cloudy
    private val PARTLY_CLOUDY = "partlycloudy"      // wi-day-cloudy
    private val PARTLY_SUNNY = "partlysunny"        // wi-day-cloudy

    private val RAIN = "rain"                       // wi-rain
    private val SLEET = "sleet"                     // wi-rain-mix
    private val SNOW = "snow"                       // wi-snow
    private val SUNNY = "sunny"                     // wi-day-sunny
    private val TSTORMS = "tstorms"                 // wi-thunderstorm

    private val WI_SNOW_WIND = "{wi_snow_wind}"
    private val WI_RAIN = "{wi_rain}"
    private val WI_RAIN_MIX = "{wi_rain_mix}"
    private val WI_SNOW = "{wi_snow}"
    private val WI_THUNDERSTORM = "{wi_thunderstorm}"
    private val WI_DAY_SUNNY = "{wi_day_sunny}"
    private val WI_CLOUDY = "{wi_cloudy}"
    private val WI_FOG = "{wi_fog}"
    private val WI_DAY_CLOUDY = "{wi_day_cloudy}"


    fun extractLocation(geocode: Geocode): Location? {
        val results = geocode.results
        if (results != null && results.size > 0) {
            return results[0].geometry.location
        } else {
            return null
        }
    }

    fun getDailyForecasts(weather: Weather?): List<DailyForecastModel> {
        if (weather != null) {
            val forecastdayList = weather.forecast.simpleforecast.forecastday
            val models = ArrayList<DailyForecastModel>()

            //            return forecastdayList.stream()
            //                    .mapToObj(f -> new DailyForecastModel(f.getConditions(), getWeatherCode(f.getIcon()),
            //                                                          f.getLow().getCelsius(), f.getHigh().getCelsius()))
            //                    .filter(f -> !f.getIcon().startsWith(PREFIX))
            //                    .limit(4)
            //                    .collect(Collectors.toList());

            for (f in forecastdayList) {
                models.add(
                        DailyForecastModel(f.conditions,
                                getWeatherCode(f.icon),
                                f.low.celsius,
                                f.high.celsius))
            }

            return filterForecast(models)
        } else {
            return ArrayList()
        }
    }

    private fun filterForecast(forecastList: List<DailyForecastModel>): List<DailyForecastModel> {

        val filtered = ArrayList<DailyForecastModel>()

        //        return forecastList
        //                .stream()
        //                .filter(f -> !f.getIcon().startsWith(PREFIX))
        //                .limit(4)
        //                .collect(Collectors.toList());

        for (f in forecastList) {
            if (!f.icon.startsWith(PREFIX)) {
                filtered.add(f)
                if (filtered.size == 4) {
                    break
                }
            }
        }
        return filtered
    }

    private fun getWeatherCode(icon: String): String {
        when (icon) {
            CHANCE_STORMS, PREFIX + CHANCE_STORMS, TSTORMS, PREFIX + TSTORMS -> return WI_THUNDERSTORM

            CHANCE_SNOW, PREFIX + CHANCE_SNOW, SNOW, PREFIX + SNOW -> return WI_SNOW

            CHANCE_FLURRIES, PREFIX + CHANCE_FLURRIES, FLURRIES, PREFIX + FLURRIES -> return WI_SNOW_WIND

            CHANCE_RAIN, PREFIX + CHANCE_RAIN, RAIN, PREFIX + RAIN -> return WI_RAIN

            CHANCE_SLEET, PREFIX + CHANCE_SLEET, SLEET, PREFIX + SLEET -> return WI_RAIN_MIX

            FOG, PREFIX + FOG, HAZY, PREFIX + HAZY -> return WI_FOG

            CLOUDY, PREFIX + CLOUDY -> return WI_CLOUDY

            MOSTLY_CLOUDY, PREFIX + MOSTLY_CLOUDY, MOSTLY_SUNNY, PREFIX + MOSTLY_SUNNY, PARTLY_CLOUDY, PREFIX + PARTLY_CLOUDY, PARTLY_SUNNY, PREFIX + PARTLY_SUNNY -> return WI_DAY_CLOUDY

            CLEAR, PREFIX + CLEAR, SUNNY, PREFIX + SUNNY -> return WI_DAY_SUNNY

            else -> return WI_DAY_CLOUDY
        }
    }
}
