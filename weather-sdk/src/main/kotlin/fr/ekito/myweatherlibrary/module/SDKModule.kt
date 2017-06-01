package fr.ekito.myweatherlibrary.module

import android.app.Application
import android.util.Log
import fr.ekito.koin.android.AndroidModule
import fr.ekito.myweatherlibrary.R
import fr.ekito.myweatherlibrary.json.AndroidJsonReader
import fr.ekito.myweatherlibrary.ws.WeatherWS
import fr.ekito.myweatherlibrary.ws.mock.WeatherMockWS
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by agiuliani on 20/04/2016.
 */
class SDKModule : AndroidModule() {

    private val TAG = SDKModule::class.java.simpleName

    override fun onLoad() {
        declareContext {
            val isOffline = resources().getBoolean(R.bool.is_offline)
            Log.w(TAG, "is offline ? $isOffline")

            if (isOffline) {
                provide { mockWS(applicationContext()) }
            } else {
                setProperty("server_url",resources().getString(R.string.server_url))
                import(NetworkModule::class)
            }
        }
    }

    fun mockWS(application: Application): WeatherWS = WeatherMockWS(AndroidJsonReader(application))
}
