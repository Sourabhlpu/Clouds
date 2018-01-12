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
 * This class extends ViewModel. Now this class can handle the data when the config changes.
 * This ViewModel will store the data for the DetailActivity even when the device rotates.
 * Although when the activity is destroyed view models are also destroyed.
 */

public class DetailActivityViewModel extends ViewModel {

    @Inject
    WeatherRepository repository;


    /**
     * LiveData is a place holder. So whenever the livedata's value changes all the registered observers
     * which are in active state are notified and the UI is updated.
     * Here we are storing detail activity's weather details in the live data.
     *
     */
    private LiveData<WeatherEntity> mWeather;
    private Date mDate;

    /**
     * the constructor here gets the weather details using the date from the repository
     * @param date the date for which we want to get the details
     */
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
