package fr.ekito.myweatherlibrary

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.util.Log
import fr.ekito.myweatherlibrary.di.Inject
import fr.ekito.myweatherlibrary.di.module.MainModule
import fr.ekito.myweatherlibrary.json.geocode.Geocode
import fr.ekito.myweatherlibrary.json.weather.Weather
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by arnaud on 04/08/2016.
 */
object WeatherSDK {

    private val TAG = WeatherSDK::class.java.simpleName

    fun init(context: Application) {
        Inject.add(context, Application::class.java)
        Inject.load(MainModule::class.java)

        // connect to service
        Log.i(TAG, "connect to service ...")
        val serviceConnection: ServiceConnection = Inject.get()!!
        val serviceIntent = Intent(context, WeatherService::class.java)
        context.bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    fun close() {
        Log.i(TAG, "shutdown ...")
        val applicationContext = Inject.applicationContext()!!
        // unbind service
        val serviceConnection: ServiceConnection = Inject.get()!!
        applicationContext.unbindService(serviceConnection)
        Inject.clear()
    }

    fun getGeocode(address: String): Single<Geocode> {
        return Inject.get<WeatherService>()!!.geocode(address)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getWeather(lat: Double?, lng: Double?): Single<Weather> {
        return Inject.get<WeatherService>()!!.weather(lat, lng, "EN")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
