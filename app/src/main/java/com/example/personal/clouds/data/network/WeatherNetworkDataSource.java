package com.example.personal.clouds.data.network;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.example.personal.clouds.AppExecutors;
import com.example.personal.clouds.data.database.WeatherEntity;

/**
 * Created by personal on 12/24/2017.
 */

public class WeatherNetworkDataSource {

    private static final String LOG_TAG = WeatherNetworkDataSource.class.getSimpleName();

    private static final Object LOCK = new Object();
    private static WeatherNetworkDataSource sInstance;
    //private final Context mContext;

    private final MutableLiveData<WeatherEntity[]> mDownloadedWeatherForecast;

    public WeatherNetworkDataSource(Context context, AppExecutors executors)
    {
        mDownloadedWeatherForecast = new MutableLiveData<>();
    }

    public static WeatherNetworkDataSource getInstance(Context context, AppExecutors executors) {
        Log.d(LOG_TAG, "Getting the network data source");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new WeatherNetworkDataSource(context.getApplicationContext(), executors);
                Log.d(LOG_TAG, "Made new network data source");
            }
        }
        return sInstance;
    }

    public MutableLiveData<WeatherEntity[]> getCurrentWeatherForecast()
    {
        return mDownloadedWeatherForecast;
    }
}
