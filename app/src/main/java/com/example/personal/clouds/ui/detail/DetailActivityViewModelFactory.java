package com.example.personal.clouds.ui.detail;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by personal on 12/25/2017.
 */

public class DetailActivityViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Date mDate;

    public DetailActivityViewModelFactory(Date date)
    {
        mDate = date;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        return (T) new DetailActivityViewModel(mDate);
    }
}
