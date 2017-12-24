package com.example.personal.clouds.utilities;

import android.content.Context;

import com.example.personal.clouds.AppExecutors;
import com.example.personal.clouds.data.database.CloudsDatabase;
import com.example.personal.clouds.data.network.WeatherNetworkDataSource;
import com.example.personal.clouds.data.WeatherRepository;

/**
 * Created by personal on 12/24/2017.
 */

public class InjectorUtils {

    public static WeatherRepository providesRepository(Context context)
    {
        CloudsDatabase database = CloudsDatabase.getInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        WeatherNetworkDataSource networkDataSource = new WeatherNetworkDataSource(context,executors);
        return WeatherRepository.getInstance(database.weatherDao(),networkDataSource,executors);
    }

    public static WeatherNetworkDataSource providesNetworkDataSource(Context context)
    {
        AppExecutors executors = AppExecutors.getInstance();
        return WeatherNetworkDataSource.getInstance(context.getApplicationContext(),executors);
    }
}
