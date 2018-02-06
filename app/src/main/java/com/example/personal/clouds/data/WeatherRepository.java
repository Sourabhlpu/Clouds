package com.example.personal.clouds.data;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.personal.clouds.AppExecutors;
import com.example.personal.clouds.dagger2.components.Clouds;
import com.example.personal.clouds.data.database.WeatherDao;
import com.example.personal.clouds.data.database.WeatherEntity;
import com.example.personal.clouds.data.network.WeatherNetworkDataSource;
import com.example.personal.clouds.utilities.CloudsDateUtils;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by personal on 12/11/2017.
 * Repository is the class that is the single source of truth for the data. It acts as a layer between
 * the data source(Room or Server) and the view models. Repository is responsible for providing the
 * data to the view model.
 */

public class WeatherRepository {



    private static final String LOG_TAG = WeatherRepository.class.getName();



    public WeatherDao mWeatherDao;

    public WeatherNetworkDataSource mWeatherNetworkDataSource;

    public AppExecutors mExecutors;
    private boolean mInitialized = false;

    /**
     * Here we are creating another constructor to implement the dependency injection.
     * We will be using constructor injection here.
     * @param weatherDao
     * @param networkDataSource
     * @param executors
     */

    @Inject
    public WeatherRepository(WeatherDao weatherDao,
                             WeatherNetworkDataSource networkDataSource,
                             AppExecutors executors)
    {
        mWeatherDao = weatherDao;
        mWeatherNetworkDataSource = networkDataSource;
        mExecutors = executors;
    }

    public WeatherRepository() {

        Clouds.getWeatherRepositoryComponent().injectWeatherRepository(this);



        //We get the WeatherEntity from the network. We retrieve it here
        LiveData<List<WeatherEntity>> networkData = mWeatherNetworkDataSource.getCurrentWeatherForecast();
        //we obeserve it and when ever the WeatherEntity is changed. observeForever is used as this
        // class does not have a lifecycle and we can observe it forever(lifecycle of the app)
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



    /**
     * here we make the dicision if we need to start a service to fetch the data
     * First we check the value of mInitialized
     * Then we use a seperate thread to see if the fetch is needed by calling isFetchNeeded.
     * If true we start the service to fetch the data.
     */
    public synchronized void initializeData()
    {
        if(mInitialized) return;
        mInitialized = true;

        mExecutors.diskIO.execute(() -> {
            if(isFetchNeeded()) {
                startFetchWeatherService();
            }
        });
    }

    /**
     * This method just deletes the old data in the database.
     */
    private void deleteOldData()
    {
       Date date = CloudsDateUtils.getNormalizedUtcDateForToday();
       mWeatherDao.deleteOldWeather(date);
    }

    /**
     * This method is responsible to tell if we need to start the service to fetch the server data.
     * This desicion is made on the basis of number of count for the days of weateher data
     * @return
     */
    private boolean isFetchNeeded()
    {
        Date date = CloudsDateUtils.getNormalizedUtcDateForToday();

        //we get the number of days of data that is in the database after this date.
        int count = mWeatherDao.countAllFutureWeather(date);

        // if that count is less than 14 days return true.
        return count < WeatherNetworkDataSource.NUM_DAYS;
    }


    //Method to start the service for the server data load
    private void startFetchWeatherService()
    {
        mWeatherNetworkDataSource.startFetchWeatherService();
    }

    /**
     *
     * @param date a date object which represents a particular date
     * @return Room returns a WeatherEntity with that particular date.
     */
    public LiveData<WeatherEntity> getWeatherByDate(Date date)
    {
        initializeData();
        return mWeatherDao.getWeatherByDate(date);
    }

    /**this method loads all the data from the database.
     *
     * @param date is used by the room query to return all the weather data that is greater than this date.
     * @return returns all the weather data which is greater than @param date
     */
    public LiveData<List<WeatherEntity>> getWeatherList(Date date)
    {
        initializeData();
        return mWeatherDao.loadAll(date);
    }



}
