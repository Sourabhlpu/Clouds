package com.example.personal.clouds.ui.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

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
    private LiveData<List<WeatherEntity>> mData;
    @Inject WeatherRepository mRepository;
    Date mDate;
    public ListActivityViewModel(Date date)
    {
        Clouds.getWeatherRepositoryComponent().injectListActivityViewModel(this);
        mDate = date;
        Log.d("ListActivityViewModel", "date : " + mDate);
        mWeatherList = mRepository.getWeatherList(mDate);

        mData = mRepository.loadAllData();
    }

    public LiveData<List<WeatherEntity>> getWeatherList()
    {
        return mWeatherList;
    }
    public LiveData<List<WeatherEntity>> getData()
    {
        return mData;
    }

}
