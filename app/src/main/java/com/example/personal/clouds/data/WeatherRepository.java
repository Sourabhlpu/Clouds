package com.example.personal.clouds.data;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.personal.clouds.AppExecutors;
import com.example.personal.clouds.data.database.WeatherDao;
import com.example.personal.clouds.data.database.WeatherEntity;
import com.example.personal.clouds.data.network.WeatherNetworkDataSource;
import com.example.personal.clouds.di.components.Clouds;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by personal on 12/11/2017.
 */

public class WeatherRepository {



    private static final String LOG_TAG = WeatherRepository.class.getName();


    @Inject
    public WeatherDao mWeatherDao;
    @Inject
    public WeatherNetworkDataSource mWeatherNetworkDataSource;
    @Inject
    public AppExecutors mExecutors;
    private boolean mInitialized = false;

    public WeatherRepository() {

        Clouds.getNetComponent().inject(this);

        LiveData<List<WeatherEntity>> networkData = mWeatherNetworkDataSource.getCurrentWeatherForecast();
        networkData.observeForever(newForecastsFromNetwork -> {
            mExecutors.diskIO().execute(() -> {
                // Deletes old historical data
                deleteOldData();
                Log.d(LOG_TAG, "Old weather deleted");
                // Insert our new weather data into Sunshine's database
                mWeatherDao.bulkInsert(newForecastsFromNetwork);
                Log.d(LOG_TAG, "New values inserted");
            });
        });

    }


    public synchronized void initializeData()
    {
        if(mInitialized) return;
        mInitialized = true;

        startFetchWeatherService();
    }

    private void deleteOldData()
    {

    }

    private boolean isFetchNeeded()
    {
        return true;
    }

    private void startFetchWeatherService()
    {
        mWeatherNetworkDataSource.startFetchWeatherService();
    }

    private LiveData<WeatherEntity> getWeatherByDate(Date date)
    {
        initializeData();
        return mWeatherDao.getWeatherByDate(date);
    }




}
