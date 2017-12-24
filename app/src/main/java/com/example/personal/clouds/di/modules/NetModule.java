package com.example.personal.clouds.di.modules;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.os.Handler;
import android.os.Looper;

import com.example.personal.clouds.AppExecutors;
import com.example.personal.clouds.data.WeatherRepository;
import com.example.personal.clouds.data.database.CloudsDatabase;
import com.example.personal.clouds.data.database.WeatherDao;
import com.example.personal.clouds.data.network.WeatherClient;
import com.example.personal.clouds.data.network.WeatherNetworkDataSource;

import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by personal on 12/17/2017.
 */


@Module
public class NetModule {
    String mBaseUrl;

    public NetModule(String baseUrl)
    {
        mBaseUrl = baseUrl;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    WeatherClient provideWeatherClient(Retrofit retrofit)
    {
        return retrofit.create(WeatherClient.class);
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

    @Singleton
    @Provides
    WeatherNetworkDataSource getNetworkDataSource()
    {
        return new WeatherNetworkDataSource();
    }

    @Singleton
    @Provides
    CloudsDatabase getDatatbase(Application context)
    {
        return Room.databaseBuilder(context,CloudsDatabase.class,CloudsDatabase.DATABASE_NAME).build();
    }

    @Singleton
    @Provides
    WeatherDao getWeatherDao(CloudsDatabase database)
    {
        return database.weatherDao();
    }

    @Provides
    @Singleton
    WeatherRepository getWeatherRepository()
    {
        return new WeatherRepository();
    }

}
