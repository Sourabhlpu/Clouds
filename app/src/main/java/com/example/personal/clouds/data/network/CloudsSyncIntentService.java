package com.example.personal.clouds.data.network;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.personal.clouds.di.components.Clouds;

import javax.inject.Inject;

/**
 * Created by personal on 12/24/2017.
 */

public class CloudsSyncIntentService extends IntentService {

    private static final String LOG_TAG = CloudsSyncIntentService.class.getSimpleName();

    @Inject
    WeatherNetworkDataSource networkDataSource;

    public CloudsSyncIntentService()
    {
        super("CloudsSyncIntentService");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Log.d(LOG_TAG, "Intent service started");

        Clouds.getNetComponent().inject(this);
        networkDataSource.fetchWeather();

    }
}
