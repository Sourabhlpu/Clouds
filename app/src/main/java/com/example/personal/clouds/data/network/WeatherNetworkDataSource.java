package com.example.personal.clouds.data.network;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.personal.clouds.AppExecutors;
import com.example.personal.clouds.R;
import com.example.personal.clouds.data.database.WeatherEntity;
import com.example.personal.clouds.di.components.Clouds;
import com.example.personal.clouds.model.pojo.Weather;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by personal on 12/24/2017.
 */

public class WeatherNetworkDataSource {

    private static final String LOG_TAG = WeatherNetworkDataSource.class.getSimpleName();

    @Inject
    WeatherClient client;

    // Interval at which to sync with the weather. Use TimeUnit for convenience, rather than
    // writing out a bunch of multiplication ourselves and risk making a silly mistake.
    private static final int SYNC_INTERVAL_HOURS = 3;
    private static final int SYNC_INTERVAL_SECONDS = (int) TimeUnit.HOURS.toSeconds(SYNC_INTERVAL_HOURS);
    private static final int SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS / 3;
    private static final String SUNSHINE_SYNC_TAG = "sunshine-sync";

    private static final Object LOCK = new Object();
    private static WeatherNetworkDataSource sInstance;
    private final Context mContext;

    private final MutableLiveData<List<WeatherEntity>> mDownloadedWeatherForecast;


    public WeatherNetworkDataSource(Context context, AppExecutors executors)
    {
        mContext = context;
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

    public MutableLiveData<List<WeatherEntity>> getCurrentWeatherForecast()
    {
        return mDownloadedWeatherForecast;
    }

    void fetchWeather()
    {
        Clouds.getNetComponent().inject(this);



        Call<Weather> call = client.forecastForDays("201301" + ",in"
                ,String.valueOf(R.string.api_key)
                ,String.valueOf(R.string.days_forecast)
                ,String.valueOf(R.string.response_mode)
                ,String.valueOf(R.string.units));

        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {

                List<Weather.Forecast> forecasts = response.body().getList();
                List<WeatherEntity> weatherEntities = getWeatherEntity(forecasts);
                mDownloadedWeatherForecast.postValue(weatherEntities);
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {

            }
        });



    }

    public List<WeatherEntity> getWeatherEntity(List<Weather.Forecast> forecasts)
    {
        List<WeatherEntity> weatherEntity = new ArrayList<WeatherEntity>();
        for(Weather.Forecast forecast : forecasts )
        {
            weatherEntity.add(new WeatherEntity(forecast.getWeatherList().get(0).getId(),
                    new Date(forecast.getDt())
                    ,forecast.getMain().getTemp_min()
                    ,forecast.getMain().getTemp_max()
                    ,forecast.getMain().getHumidity()
                    ,forecast.getWind().getSpeed()
                    ,forecast.getWind().getDeg()
                    ,forecast.getMain().getPressure()));
        }

        return weatherEntity;
    }

    public void startFetchWeatherService()
    {
        Intent intentToFetch = new Intent(mContext, CloudsSyncIntentService.class);
        mContext.startService(intentToFetch);
        Log.d(LOG_TAG,"Service Created");

    }

    public void scheduleRecurringFetchWeatherSync()
    {
        Driver driver = new GooglePlayDriver(mContext);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job syncCloudsJob = dispatcher.newJobBuilder()
                .setService(CloudsFirebaseJobService.class)
                .setTag("")
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                        SYNC_INTERVAL_SECONDS,
                        SYNC_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS
                ))
                .setReplaceCurrent(true)
                .build();

        dispatcher.schedule(syncCloudsJob);
        Log.d(LOG_TAG,"job scheduled");
    }
}
