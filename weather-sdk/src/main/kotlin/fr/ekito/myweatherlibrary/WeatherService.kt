package fr.ekito.myweatherlibrary

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import fr.ekito.myweatherlibrary.ws.WeatherWS

class WeatherService : Service() {
    private val TAG = WeatherService::class.java.simpleName

    private val mBinder = LocalBinder()
    lateinit var weatherWS: WeatherWS

    fun getGeocode(address: String) = weatherWS.geocode(address)

    fun getWeather(lat: Double?, lon: Double?, lang: String = "EN") = weatherWS.weather(lat, lon, lang)

    override fun onBind(intent: Intent): IBinder? {
        // inject stuff
        return mBinder
    }

    override fun onUnbind(intent: Intent): Boolean {
        // close stuff
        return super.onUnbind(intent)
    }

    inner class LocalBinder : Binder() {
        val service: WeatherService = this@WeatherService
    }
}
