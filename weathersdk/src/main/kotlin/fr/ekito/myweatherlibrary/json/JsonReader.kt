package fr.ekito.myweatherlibrary.json

import fr.ekito.myweatherlibrary.json.geocode.Geocode
import fr.ekito.myweatherlibrary.json.geocode.Location
import fr.ekito.myweatherlibrary.json.weather.Weather

/**
 * Created by arnaud on 29/05/2017.
 */
interface JsonReader {
    fun getAllLocations(): Map<Location, String>
    fun getWeather(name: String): Weather
    fun getGeocode(name: String): Geocode
}