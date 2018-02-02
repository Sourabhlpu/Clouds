package com.example.personal.clouds.dagger2.modules;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.personal.clouds.AppExecutors;
import com.example.personal.clouds.data.network.WeatherClient;
import com.example.personal.clouds.data.network.WeatherNetworkDataSource;

import java.util.concurrent.Executors;

import javax.inject.Singleton;

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

    @Singleton
    @Provides
    Handler providesHandler()
    {
        return new Handler(Looper.getMainLooper());
    }

    @Singleton
    @Provides
    AppExecutors.MainThreadExecutor getMainThreadExecutor()
    {
        return new AppExecutors.MainThreadExecutor();
    }

    @Singleton
    @Provides
    AppExecutors getAppExecutors(AppExecutors.MainThreadExecutor mainThreadExecutor)
    {
        return new AppExecutors(Executors.newSingleThreadExecutor(),
                Executors.newFixedThreadPool(3)
                ,mainThreadExecutor);
    }

}
