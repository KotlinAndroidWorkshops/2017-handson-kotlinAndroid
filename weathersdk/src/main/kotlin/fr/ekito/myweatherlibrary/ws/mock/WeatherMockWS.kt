package fr.ekito.myweatherlibrary.ws.mock

import fr.ekito.myweatherlibrary.json.JsonReader
import fr.ekito.myweatherlibrary.json.geocode.Geocode
import fr.ekito.myweatherlibrary.json.geocode.Location
import fr.ekito.myweatherlibrary.json.weather.Weather
import fr.ekito.myweatherlibrary.ws.WeatherWS
import io.reactivex.Single

/**
 * Created by arnaud on 14/02/2017.
 */


class WeatherMockWS(val jsonReader: JsonReader) : WeatherWS {
    private val cities = HashMap<Location, String>()
    private val default_city = "toulouse"

    init {
        cities += jsonReader.getAllLocations()
    }

    private fun isKnownCity(adrs: String): Boolean {
        return cities.values.contains(adrs)
    }

    private fun cityFromLocation(lat: Double?, lng: Double?): String {
        var city = "toulouse"
        cities.keys
                .filter { it.lat == lat && it.lng == lng }
                .forEach { city = cities[it]!! }

        return city
    }

    override fun geocode(address: String): Single<Geocode> {
        return Single.create { e ->
            val adrs = address.toLowerCase()
            val geocode: Geocode
            if (isKnownCity(adrs)) {
                geocode = jsonReader.getGeocode(adrs)
            } else {
                geocode = jsonReader.getGeocode(default_city)
            }
            e.onSuccess(geocode)
        }
    }

    override fun weather(lat: Double?, lon: Double?, lang: String): Single<Weather> {
        return Single.create { e ->
            val city = cityFromLocation(lat, lon)
            val weather = jsonReader.getWeather(city)
            e.onSuccess(weather)
        }
    }
}
