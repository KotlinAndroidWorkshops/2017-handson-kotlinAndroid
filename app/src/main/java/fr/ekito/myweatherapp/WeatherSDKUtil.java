package fr.ekito.myweatherapp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import fr.ekito.myweatherlibrary.json.geocode.Geocode;
import fr.ekito.myweatherlibrary.json.geocode.Location;
import fr.ekito.myweatherlibrary.json.geocode.Result;
import fr.ekito.myweatherlibrary.json.weather.Forecastday_;
import fr.ekito.myweatherlibrary.json.weather.Weather;

public class WeatherSDKUtil {

    private static final String PREFIX = "nt_";

    private static final String CHANCE_FLURRIES = "chanceflurries";  // wi-snow-wind
    private static final String CHANCE_RAIN = "chancerain";          // wi-rain
    private static final String CHANCE_SLEET = "chancesleet";        // wi-rain-mix
    private static final String CHANCE_SNOW = "chancesnow";          // wi-snow
    private static final String CHANCE_STORMS = "chancestorms";      // wi-thunderstorm

    private static final String CLEAR = "clear";                     // wi-day-sunny
    private static final String CLOUDY = "cloudy";                   // wi-cloudy
    private static final String FLURRIES = "flurries";               // wi-snow-wind
    private static final String FOG = "fog";                         // wi-fog
    private static final String HAZY = "hazy";                       // wi-fog

    private static final String MOSTLY_CLOUDY = "mostlycloudy";      // wi-day-cloudy
    private static final String MOSTLY_SUNNY = "mostlysunny";        // wi-day-cloudy
    private static final String PARTLY_CLOUDY = "partlycloudy";      // wi-day-cloudy
    private static final String PARTLY_SUNNY = "partlysunny";        // wi-day-cloudy

    private static final String RAIN = "rain";                       // wi-rain
    private static final String SLEET = "sleet";                     // wi-rain-mix
    private static final String SNOW = "snow";                       // wi-snow
    private static final String SUNNY = "sunny";                     // wi-day-sunny
    private static final String TSTORMS = "tstorms";                 // wi-thunderstorm

    private static final String WI_SNOW_WIND = "{wi_snow_wind}";
    private static final String WI_RAIN = "{wi_rain}";
    private static final String WI_RAIN_MIX = "{wi_rain_mix}";
    private static final String WI_SNOW = "{wi_snow}";
    private static final String WI_THUNDERSTORM = "{wi_thunderstorm}";
    private static final String WI_DAY_SUNNY = "{wi_day_sunny}";
    private static final String WI_CLOUDY = "{wi_cloudy}";
    private static final String WI_FOG = "{wi_fog}";
    private static final String WI_DAY_CLOUDY = "{wi_day_cloudy}";


    @Nullable
    public static Location extractLocation(Geocode geocode) {
        List<Result> results = geocode.getResults();
        if (results != null && results.size() > 0) {
            return results.get(0).getGeometry().getLocation();
        } else {
            return null;
        }
    }

    @NonNull
    public static List<DailyForecastModel> getDailyForecasts(Weather weather) {
        if (weather != null) {
            List<Forecastday_> forecastdayList = weather.getForecast().getSimpleforecast().getForecastday();
            List<DailyForecastModel> models = new ArrayList<>();

//            return forecastdayList.stream()
//                    .mapToObj(f -> new DailyForecastModel(f.getConditions(), getWeatherCode(f.getIcon()),
//                                                          f.getLow().getCelsius(), f.getHigh().getCelsius()))
//                    .filter(f -> !f.getIcon().startsWith(PREFIX))
//                    .limit(4)
//                    .collect(Collectors.toList());

            for (Forecastday_ f : forecastdayList) {
                models.add(
                        new DailyForecastModel(f.getConditions(),
                                getWeatherCode(f.getIcon()),
                                f.getLow().getCelsius(),
                                f.getHigh().getCelsius()));
            }

            return filterForecast(models);
        } else {
            return new ArrayList<>();
        }
    }

    private static List<DailyForecastModel> filterForecast(List<DailyForecastModel> forecastList) {

        List<DailyForecastModel> filtered = new ArrayList<>();

//        return forecastList
//                .stream()
//                .filter(f -> !f.getIcon().startsWith(PREFIX))
//                .limit(4)
//                .collect(Collectors.toList());

        for (DailyForecastModel f : forecastList) {
            if (!f.getIcon().startsWith(PREFIX)) {
                filtered.add(f);
                if (filtered.size() == 4) {
                    break;
                }
            }
        }
        return filtered;
    }

    @NonNull
    private static String getWeatherCode(final String icon) {
        switch (icon) {
            case CHANCE_STORMS:
            case PREFIX + CHANCE_STORMS:
            case TSTORMS:
            case PREFIX + TSTORMS:
                return WI_THUNDERSTORM;

            case CHANCE_SNOW:
            case PREFIX + CHANCE_SNOW:
            case SNOW:
            case PREFIX + SNOW:
                return WI_SNOW;

            case CHANCE_FLURRIES:
            case PREFIX + CHANCE_FLURRIES:
            case FLURRIES:
            case PREFIX + FLURRIES:
                return WI_SNOW_WIND;

            case CHANCE_RAIN:
            case PREFIX + CHANCE_RAIN:
            case RAIN:
            case PREFIX + RAIN:
                return WI_RAIN;

            case CHANCE_SLEET:
            case PREFIX + CHANCE_SLEET:
            case SLEET:
            case PREFIX + SLEET:
                return WI_RAIN_MIX;

            case FOG:
            case PREFIX + FOG:
            case HAZY:
            case PREFIX + HAZY:
                return WI_FOG;

            case CLOUDY:
            case PREFIX + CLOUDY:
                return WI_CLOUDY;

            case MOSTLY_CLOUDY:
            case PREFIX + MOSTLY_CLOUDY:
            case MOSTLY_SUNNY:
            case PREFIX + MOSTLY_SUNNY:
            case PARTLY_CLOUDY:
            case PREFIX + PARTLY_CLOUDY:
            case PARTLY_SUNNY:
            case PREFIX + PARTLY_SUNNY:
                return WI_DAY_CLOUDY;

            case CLEAR:
            case PREFIX + CLEAR:
            case SUNNY:
            case PREFIX + SUNNY:
                return WI_DAY_SUNNY;

            default:
                return WI_DAY_CLOUDY;
        }
    }
}
