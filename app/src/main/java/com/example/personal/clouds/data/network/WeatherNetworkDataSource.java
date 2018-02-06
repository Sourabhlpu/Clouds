package com.example.personal.clouds.data.network;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.personal.clouds.R;
import com.example.personal.clouds.dagger2.components.Clouds;
import com.example.personal.clouds.data.database.WeatherEntity;
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
 * This class is responsible for fetching the data from the server. The data that is retrieved from
 * the server is stored as MutableLiveData. This MutableLiveData is observed by the repository.
 * This class also has methods for starting the service to load data from server and scheduling a
 * firebase job.
 */

public class WeatherNetworkDataSource {

    private static final String LOG_TAG = WeatherNetworkDataSource.class.getSimpleName();


    WeatherClient client;
    Application mContext;

    // Interval at which to sync with the weather. Use TimeUnit for convenience, rather than
    // writing out a bunch of multiplication ourselves and risk making a silly mistake.
    private static final int SYNC_INTERVAL_HOURS = 3;
    private static final int SYNC_INTERVAL_SECONDS = (int) TimeUnit.HOURS.toSeconds(SYNC_INTERVAL_HOURS);
    private static final int SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS / 3;
    private static final String SUNSHINE_SYNC_TAG = "sunshine-sync";
    public static final int NUM_DAYS = 14;


    // this variable stores the list of data that is fetched from the database.
    private final MutableLiveData<List<WeatherEntity>> mDownloadedWeatherForecast;

    /**
     * Implement this constructor later so that we can implement dagger 2.

     * @param client
     * @param context
     */
    @Inject
    public WeatherNetworkDataSource(WeatherClient client, Context context) {
        mDownloadedWeatherForecast = new MutableLiveData<>();
        Clouds.getWeatherRepositoryComponent().injectWeatherNetworkDataSource(this);
    }


    public MutableLiveData<List<WeatherEntity>> getCurrentWeatherForecast()
    {
        return mDownloadedWeatherForecast;
    }

    /**
     * This method is responsible for calling the WeatherClient and making a network call.
     * After the call is made we set the value of the MutableLiveData. As the repository is observing
     * this LiveData it causes the local database to update itself.
     */

    void fetchWeather()
    {
        /**
         * the client is created in the dependency injection's NetModule.
         * We pass in the correct query parameters and get a Call<Weather>.
         */



        Call<Weather> call = client.forecastForDays("201301" + ",in"
                ,String.valueOf(R.string.api_key)
                ,String.valueOf(R.string.days_forecast)
                ,String.valueOf(R.string.response_mode)
                ,String.valueOf(R.string.units));

        /**
         * call.enque makes an asynchronous request with to callback methods as below.
         */

        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {

                //now from the response we get the list of the weather forecast.
                List<Weather.Forecast> forecasts = response.body().getList();

                //as we want this data to be inserted into the database we use getWeatherEntity method
                // to convert Weather.Forecast into WeatherEntity.
                List<WeatherEntity> weatherEntities = getWeatherEntity(forecasts);

                //we use postValue when the call occurs off the main thread
                mDownloadedWeatherForecast.postValue(weatherEntities);
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {

            }
        });



    }

    /**
     * This method maps the server response i.e. Weather.Forecast into WeatherEntity
     * @param forecasts is the list of forecast data that we receive from the server.
     *                  We create a Weather class that maps exactly like the server response.
     * @return The returned value is an object of the type WeatherEntity.
     */

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

    /**
     * this method just start off the service to fetch data data from server
     */

    public void startFetchWeatherService()
    {
        Intent intentToFetch = new Intent(mContext, CloudsSyncIntentService.class);
        mContext.startService(intentToFetch);
        Log.d(LOG_TAG,"Service Created");

    }


    /**
     * We here use firebase job dispatcher to schedule a recurring task
     */
    public void scheduleRecurringFetchWeatherSync()
    {
        Driver driver = new GooglePlayDriver(mContext);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job syncCloudsJob = dispatcher.newJobBuilder()
                .setService(CloudsFirebaseJobService.class)
                .setTag(SUNSHINE_SYNC_TAG)
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
