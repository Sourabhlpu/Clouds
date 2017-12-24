package com.example.personal.clouds.ui.detail;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.personal.clouds.data.database.WeatherEntity;

/**
 * Created by personal on 12/21/2017.
 */

public class DetailActivityViewModel extends ViewModel {

    private MutableLiveData<WeatherEntity> mWeather;

    public DetailActivityViewModel()
    {
        mWeather = new MutableLiveData<>();
    }

    public MutableLiveData<WeatherEntity>getWeather()
    {
        return mWeather;
    }

    public void setWeather(WeatherEntity weather)
    {
        mWeather.postValue(weather);
    }


}
