package fr.ekito.myweatherlibrary.ws

import android.util.Log
import com.google.gson.Gson
import fr.ekito.myweatherlibrary.di.Inject
import fr.ekito.myweatherlibrary.json.geocode.Geocode
import fr.ekito.myweatherlibrary.json.geocode.Location
import fr.ekito.myweatherlibrary.json.weather.Weather
import io.reactivex.Single
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

/**
 * Created by arnaud on 14/02/2017.
 */

val TAG = WeatherMockWS::class.java.simpleName

fun readJsonFile(jsonFile: String): String {
    val buf = StringBuilder()
    val json = Inject.applicationContext()!!.assets.open("json/" + jsonFile)
    // closable resource
    BufferedReader(InputStreamReader(json, "UTF-8")).use { br ->
        buf.append(br.lineSequence().joinToString(separator = "\n"))
    }
    return buf.toString()
}

class WeatherMockWS : WeatherWS {
    private val gson = Gson()
    private val cities = HashMap<Location, String>()

    init {
        val list = Inject.applicationContext()!!.assets.list("json")

        cities += list.filter { it.startsWith("geocode_") }.map {
            val geocode = gson.fromJson<Geocode>(readJsonFile(it), Geocode::class.java)
            val name = it.replace("geocode_", "").replace(".json", "")
            // pair result
            Pair(geocode.results[0].geometry!!.location!!, name)
        }.toMap() // direct to map

        Log.i(TAG, "Can respond for cities : " + cities)
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
        return Single.create { subscriber ->
            val adrs = address.toLowerCase()
            val geocode: Geocode
            if (isKnownCity(adrs)) {
                geocode = gson.fromJson<Geocode>(readJsonFile("geocode_$adrs.json"), Geocode::class.java)
            } else {
                geocode = gson.fromJson<Geocode>(readJsonFile("geocode_toulouse.json"), Geocode::class.java)
            }
            subscriber.onSuccess(geocode)
        }
    }

    override fun weather(lat: Double?, lon: Double?, lang: String): Single<Weather> {
        return Single.create { subscriber ->
            val city = cityFromLocation(lat, lon)
            val weather: Weather = gson.fromJson<Weather>(readJsonFile("weather_$city.json"), Weather::class.java)
            subscriber.onSuccess(weather)
        }
    }
}
