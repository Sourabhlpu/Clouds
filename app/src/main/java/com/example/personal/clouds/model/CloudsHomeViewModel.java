package com.example.personal.clouds.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.personal.clouds.model.pojo.Weather;

/**
 * Created by personal on 12/10/2017.
 */

public class CloudsHomeViewModel extends ViewModel {


    private LiveData<Weather.Forecast> weather;

    public LiveData<Weather.Forecast> getWeather()
    {
        return weather;
    }
}
