package fr.ekito.myweatherlibrary.di.module;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import fr.ekito.myweatherlibrary.R;
import fr.ekito.myweatherlibrary.WeatherService;
import fr.ekito.myweatherlibrary.di.Inject;
import fr.ekito.myweatherlibrary.di.Module;
import fr.ekito.myweatherlibrary.ws.WeatherMockWS;
import fr.ekito.myweatherlibrary.ws.WeatherWS;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by agiuliani on 20/04/2016.
 */
public class MainModule extends Module {

    private static final String TAG = MainModule.class.getSimpleName();

    @Override
    public void load() {
        provide(registerService(), ServiceConnection.class);

        Resources resources = getApplication().getResources();
        Boolean isOffline = resources.getBoolean(R.bool.is_offline);

        Log.w(TAG, "use offline strategy ? " + isOffline);

        if (!isOffline) {
            String url = resources.getString(R.string.server_url);
            provide(retrofitWS(url), WeatherWS.class);
        } else {
            provide(mockWS(), WeatherWS.class);
        }
    }

    ServiceConnection registerService() {
        return new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.w(TAG, "WeatherService onServiceConnected");
                WeatherService serviceConnection = ((WeatherService.LocalBinder) service).getService();
                Inject.add(serviceConnection, WeatherService.class);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.w(TAG, "WeatherService onServiceDisconnected");
                Inject.remove(WeatherService.class);
            }
        };
    }

    private OkHttpClient createClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .connectTimeout(60l, TimeUnit.SECONDS)
                .readTimeout(60l, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor).build();
    }

    WeatherWS retrofitWS(String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(createClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        return retrofit.create(WeatherWS.class);
    }

    WeatherWS mockWS() {
        return new WeatherMockWS();
    }
}
