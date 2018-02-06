package com.example.personal.clouds.dagger2.modules;

import com.example.personal.clouds.dagger2.Scopes.DetailActivityScope;
import com.example.personal.clouds.ui.detail.DetailActivity;
import com.example.personal.clouds.ui.detail.DetailActivityViewModelFactory;

import java.util.Date;

import dagger.Module;
import dagger.Provides;

/**
 * Created by personal on 2/6/2018.
 */

@Module
public class DetailActivityModule {

    private final DetailActivity detailActivity;
    private final Date mDate;

    public DetailActivityModule(DetailActivity detailActivity, Date date)
    {
        this.detailActivity = detailActivity;
        mDate = date;
    }

    @Provides
    @DetailActivityScope
    public DetailActivityViewModelFactory detailActivityViewModelFactory()
    {
        return new DetailActivityViewModelFactory(mDate);
    }
}
