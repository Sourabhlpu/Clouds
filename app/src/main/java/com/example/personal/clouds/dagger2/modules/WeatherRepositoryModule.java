package com.example.personal.clouds.dagger2.modules;

import com.example.personal.clouds.AppExecutors;
import com.example.personal.clouds.data.WeatherRepository;
import com.example.personal.clouds.data.database.WeatherDao;
import com.example.personal.clouds.data.network.WeatherNetworkDataSource;

import dagger.Module;
import dagger.Provides;

/**
 * Created by personal on 2/2/2018.
 */

@Module
public class WeatherRepositoryModule {

    @Provides
    public WeatherRepository weatherRepository(WeatherDao weatherDao,
                                               WeatherNetworkDataSource networkDataSource,
                                               AppExecutors appExecutors)
    {
        return new WeatherRepository(weatherDao,networkDataSource,appExecutors);
    }


}
