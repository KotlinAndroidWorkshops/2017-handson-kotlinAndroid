package fr.ekito.myweatherlibrary.module

import fr.ekito.koin.module.Module
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
class NetworkModule : Module() {

    private val TAG = NetworkModule::class.java.simpleName

    override fun onLoad() {
        declareContext {
            provide { retrofitWS(getProperty<String>("server_url")!!) }
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
}
