package com.example.personal.clouds.dagger2.modules;

import android.os.Handler;
import android.os.Looper;

import com.example.personal.clouds.AppExecutors;
import com.example.personal.clouds.data.WeatherRepository;
import com.example.personal.clouds.data.database.WeatherDao;
import com.example.personal.clouds.data.network.WeatherNetworkDataSource;

import java.util.concurrent.Executors;

import dagger.Module;
import dagger.Provides;

/**
 * Created by personal on 2/2/2018.
 */

@Module(includes = {RoomDatabaseModule.class, WeatherNetworkDataSourceModule.class})
public class WeatherRepositoryModule {

    @Provides
    public WeatherRepository weatherRepository(WeatherDao weatherDao,
                                               WeatherNetworkDataSource networkDataSource,
                                               AppExecutors appExecutors)
    {
        return new WeatherRepository(weatherDao,networkDataSource,appExecutors);
    }

    @Provides
    AppExecutors getAppExecutors(AppExecutors.MainThreadExecutor mainThreadExecutor)
    {
        return new AppExecutors(Executors.newSingleThreadExecutor(),
                Executors.newFixedThreadPool(3)
                ,mainThreadExecutor);
    }

    @Provides
    AppExecutors.MainThreadExecutor getMainThreadExecutor()
    {
        return new AppExecutors.MainThreadExecutor();
    }

    @Provides
    Handler providesHandler()
    {
        return new Handler(Looper.getMainLooper());
    }






}
