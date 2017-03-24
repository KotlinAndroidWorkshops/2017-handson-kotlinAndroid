package fr.ekito.myweatherlibrary

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import fr.ekito.myweatherlibrary.di.Inject
import fr.ekito.myweatherlibrary.json.geocode.Geocode
import fr.ekito.myweatherlibrary.json.weather.Weather
import fr.ekito.myweatherlibrary.ws.WeatherWS
import io.reactivex.Single

class WeatherService : Service() {
    private val TAG = WeatherService::class.java.simpleName
    private val mBinder = LocalBinder()
    private var weatherWS: WeatherWS? = null

    fun geocode(address: String): Single<Geocode> {
        return weatherWS!!.geocode(address)
    }

    fun weather(lat: Double?, lon: Double?, lang: String): Single<Weather> {
        return weatherWS!!.weather(lat, lon, lang)
    }

    override fun onBind(intent: Intent): IBinder? {
        // inject stuff
        weatherWS = Inject.get()
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
