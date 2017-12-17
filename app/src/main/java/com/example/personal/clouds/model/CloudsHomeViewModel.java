package com.example.personal.clouds.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.personal.clouds.model.pojo.Weather;
import com.example.personal.clouds.utilities.network.WeatherRepository;

import javax.inject.Inject;

/**
 * Created by personal on 12/10/2017.
 */

public class CloudsHomeViewModel extends ViewModel {


    private LiveData<Weather.Forecast> mWeather;
    private WeatherRepository mRepository;

    @Inject
    public CloudsHomeViewModel(WeatherRepository repository) {

        mRepository = repository;
    }

    public void init()
    {
        mWeather = mRepository.getWeatherForecast();
    }

    public LiveData<Weather.Forecast> getWeather()
    {
        return mWeather;
    }
}
