package com.example.personal.clouds.dagger2.modules;

import android.content.Context;

import com.example.personal.clouds.data.network.WeatherClient;
import com.example.personal.clouds.data.network.WeatherNetworkDataSource;

import dagger.Module;
import dagger.Provides;

/**
 * Created by personal on 2/2/2018.
 */

@Module
public class WeatherNetworkDataSourceModule {



    @Provides
    public WeatherNetworkDataSource weatherNetworkDataSource(WeatherClient client, Context context)
    {
        return new WeatherNetworkDataSource(client,context);
    }


}
