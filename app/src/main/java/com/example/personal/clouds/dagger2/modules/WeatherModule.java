package com.example.personal.clouds.dagger2.modules;

import com.example.personal.clouds.model.pojo.Weather;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by personal on 2/2/2018.
 */

@Module(includes = OkHttpClient.class)
public class WeatherModule {

    String mBaseUrl;

    public WeatherModule(String baseUrl)
    {
        mBaseUrl = baseUrl;
    }

    @Provides
    public Weather weather(Retrofit retrofit)
    {
        return retrofit.create(Weather.class);
    }

    @Provides
    public Retrofit retrofit(OkHttpClient okHttpClient,
                             GsonConverterFactory gsonConverterFactory, Gson gson)
    {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(mBaseUrl)
                .addConverterFactory(gsonConverterFactory)
                .build();
    }

    @Provides
    public Gson gson()
    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @Provides
    public GsonConverterFactory gsonConverterFactory(Gson gson)
    {
        return GsonConverterFactory.create(gson);
    }


}
