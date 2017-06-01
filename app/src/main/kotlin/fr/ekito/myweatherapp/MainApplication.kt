package fr.ekito.myweatherapp

import android.app.Application

import com.joanzapata.iconify.Iconify
import com.joanzapata.iconify.fonts.WeathericonsModule

import fr.ekito.myweatherlibrary.WeatherSDK

class MainApplication : Application() {

    lateinit var weatherSDK: WeatherSDK

    override fun onCreate() {
        super.onCreate()

        weatherSDK = WeatherSDK()
        weatherSDK.init(this)

        Iconify.with(WeathericonsModule())
        instance = this
    }

    companion object {

        private lateinit var instance: MainApplication

        fun get(): MainApplication {
            return instance
        }
    }
}
