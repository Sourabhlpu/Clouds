package com.example.personal.clouds.ui.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.personal.clouds.dagger2.components.Clouds;
import com.example.personal.clouds.data.WeatherRepository;
import com.example.personal.clouds.data.database.WeatherEntity;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by personal on 12/10/2017.
 */

public class ListActivityViewModel extends ViewModel {


    private LiveData<List<WeatherEntity>> mWeatherList;
    @Inject WeatherRepository mRepository;
    private Date mDate;
    public ListActivityViewModel(Date date)
    {
        Clouds.getWeatherRepositoryComponent().injectListActivityViewModel(this);
        mDate = date;
        mWeatherList = mRepository.getWeatherList(mDate);
    }

    public LiveData<List<WeatherEntity>> getWeatherList()
    {
        return mWeatherList;
    }
}
