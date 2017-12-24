package com.example.personal.clouds.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.personal.clouds.AppExecutors;
import com.example.personal.clouds.R;
import com.example.personal.clouds.data.database.WeatherDao;
import com.example.personal.clouds.data.database.WeatherEntity;
import com.example.personal.clouds.data.network.WeatherClient;
import com.example.personal.clouds.data.network.WeatherNetworkDataSource;
import com.example.personal.clouds.di.components.Clouds;
import com.example.personal.clouds.model.pojo.Weather;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by personal on 12/11/2017.
 */

public class WeatherRepository {

    @Inject
    WeatherClient client;

    private static final String LOG_TAG = WeatherRepository.class.getName();

    private static final Object lock = new Object();
    public static WeatherRepository sInstance;
    private final WeatherDao mWeatherDao;
    private final WeatherNetworkDataSource mWeatherNetworkDataSource;
    private final AppExecutors mExecutors;
    private boolean mInitialized = false;

    private WeatherRepository(WeatherDao weatherDao,
                              WeatherNetworkDataSource weatherNetworkDataSource,
                              AppExecutors executors) {

        mWeatherDao = weatherDao;
        mWeatherNetworkDataSource = weatherNetworkDataSource;
        mExecutors = executors;


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

    public synchronized static WeatherRepository getInstance(WeatherDao weatherDao,
                                                             WeatherNetworkDataSource weatherNetworkDataSource,
                                                             AppExecutors executors)
    {
        Log.d(LOG_TAG, "getting the repository");
        if(sInstance == null)
        {
            synchronized (lock){
                sInstance = new WeatherRepository(weatherDao,weatherNetworkDataSource,executors);

                Log.d(LOG_TAG,"made the new repository");
            }
        }

        return sInstance;
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
        //mWeatherNetworkDataSource.startFetchWeatherService();
    }



    public LiveData<Weather.Forecast> getWeatherForecast()
    {
        Clouds.getNetComponent().inject(this);

        final MutableLiveData<Weather.Forecast> data = new MutableLiveData<>();

        Call<Weather> call = client.forecastForDays("201301" + ",in"
                ,String.valueOf(R.string.api_key)
                ,String.valueOf(R.string.days_forecast)
                ,String.valueOf(R.string.response_mode)
                ,String.valueOf(R.string.units));

        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {

                List<Weather.Forecast> forecasts = response.body().getList();
                data.setValue((Weather.Forecast) forecasts);
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {

            }
        });

        return data;

    }
}
