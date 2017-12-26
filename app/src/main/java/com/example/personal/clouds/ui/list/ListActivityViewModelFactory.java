package com.example.personal.clouds.ui.list;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by personal on 12/26/2017.
 */

public class ListActivityViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Date mDate;

    public ListActivityViewModelFactory(Date date)
    {
        mDate = date;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return  (T) new ListActivityViewModel(mDate);
    }
}
