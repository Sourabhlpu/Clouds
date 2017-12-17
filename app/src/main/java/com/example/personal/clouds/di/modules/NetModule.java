package com.example.personal.clouds.di.modules;

import com.example.personal.clouds.utilities.network.WeatherClient;
import com.example.personal.clouds.utilities.network.WeatherRepository;

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

    @Provides
    @Singleton
    WeatherRepository getWeatherRepository()
    {
        return new WeatherRepository();
    }
}
