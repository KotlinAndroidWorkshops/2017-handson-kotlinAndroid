package fr.ekito.myweatherlibrary.di.module

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import fr.ekito.myweatherlibrary.R
import fr.ekito.myweatherlibrary.WeatherService
import fr.ekito.myweatherlibrary.di.Inject
import fr.ekito.myweatherlibrary.di.Module
import fr.ekito.myweatherlibrary.ws.WeatherMockWS
import fr.ekito.myweatherlibrary.ws.WeatherWS
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by agiuliani on 20/04/2016.
 */
class MainModule : Module() {

    private val TAG = MainModule::class.java.simpleName

    override fun load() {
        provide(registerService(), ServiceConnection::class.java)

        val resources = application()!!.resources
        val isOffline = resources.getBoolean(R.bool.is_offline)

        Log.w(TAG, "use offline strategy ? $isOffline")

        if (!isOffline) {
            val url = resources.getString(R.string.server_url)
            provide(retrofitWS(url), WeatherWS::class.java)
        } else {
            provide(mockWS(), WeatherWS::class.java)
        }
    }

    private fun registerService(): ServiceConnection {
        return object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                Log.w(TAG, "WeatherService onServiceConnected")
                val serviceConnection = (service as WeatherService.LocalBinder).service
                Inject.add(serviceConnection, WeatherService::class.java)
            }

            override fun onServiceDisconnected(name: ComponentName) {
                Log.w(TAG, "WeatherService onServiceDisconnected")
                val weatherService: WeatherService = Inject.get()!!
                Inject.remove(weatherService)
            }
        }
    }

    private fun createClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .connectTimeout(60L, TimeUnit.SECONDS)
                .readTimeout(60L, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor).build()
    }

    private fun retrofitWS(url: String): WeatherWS {
        val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .client(createClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
        return retrofit.create(WeatherWS::class.java)
    }

    fun mockWS(): WeatherWS = WeatherMockWS()
}
