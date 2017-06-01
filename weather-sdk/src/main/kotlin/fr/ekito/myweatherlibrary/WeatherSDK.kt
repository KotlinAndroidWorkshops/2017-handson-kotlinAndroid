package fr.ekito.myweatherlibrary

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import fr.ekito.koin.Koin
import fr.ekito.myweatherlibrary.module.SDKModule

/**
 * Created by arnaud on 04/08/2016.
 */
class WeatherSDK {

    private val TAG = WeatherSDK::class.java.simpleName

    val context: fr.ekito.koin.Context = Koin().build()

    val service: WeatherService by lazy { context.get<WeatherService>() }

    fun init(app: Application) {
        context.provide { app }
        context.import(SDKModule::class)

        // connect to service
        Log.i(TAG, "connect to service ...")
        val serviceConnection: ServiceConnection = registerService(context)
        val serviceIntent = Intent(app, WeatherService::class.java)
        val binded = app.bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE)
        Log.i(TAG, "service binded : $binded")
    }

    private fun registerService(context: fr.ekito.koin.Context): ServiceConnection {
        return object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                Log.w(TAG, "WeatherService onServiceConnected")
                val ws: WeatherService = (service as WeatherService.LocalBinder).service
                ws.weatherWS = context.get()
                context.provide { ws }
            }

            override fun onServiceDisconnected(name: ComponentName) {
                Log.w(TAG, "WeatherService onServiceDisconnected")
                context.remove(WeatherService::class)
            }
        }
    }

    fun close() {
        Log.i(TAG, "shutdown ...")
        // unbind service
        val serviceConnection: ServiceConnection = context.get()
        context.get<Application>().unbindService(serviceConnection)
    }
}
