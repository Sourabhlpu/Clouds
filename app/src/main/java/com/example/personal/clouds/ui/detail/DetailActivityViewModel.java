package com.example.personal.clouds.ui.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.personal.clouds.data.WeatherRepository;
import com.example.personal.clouds.data.database.WeatherEntity;
import com.example.personal.clouds.di.components.Clouds;

import java.util.Date;

import javax.inject.Inject;

/**
 * Created by personal on 12/21/2017.
 */

public class DetailActivityViewModel extends ViewModel {

    @Inject
    WeatherRepository repository;

    private LiveData<WeatherEntity> mWeather;
    private Date mDate;

    public DetailActivityViewModel(Date date)
    {
        Clouds.getNetComponent().inject(this);
        mDate = date;
        mWeather = repository.getWeatherByDate(mDate);
    }

    public LiveData<WeatherEntity>getWeather()
    {
        return mWeather;
    }

}
