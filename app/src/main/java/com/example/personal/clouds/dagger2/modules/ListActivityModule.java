package com.example.personal.clouds.dagger2.modules;

import com.example.personal.clouds.dagger2.Scopes.ListActivityScope;
import com.example.personal.clouds.ui.list.ForecastAdapter;
import com.example.personal.clouds.ui.list.ListActivity;
import com.example.personal.clouds.ui.list.ListActivityViewModelFactory;

import java.util.Date;

import dagger.Module;
import dagger.Provides;

/**
 * Created by personal on 2/4/2018.
 */

@Module
public class ListActivityModule {

    private final ListActivity listActivity;
    private final Date mDate;

    public ListActivityModule(ListActivity listActivity, Date date)
    {
        this.listActivity = listActivity;
        mDate = date;
    }

    @Provides
    @ListActivityScope
    public ForecastAdapter forecastAdapter()
    {
        return new ForecastAdapter(listActivity,listActivity);
    }

    @Provides
    @ListActivityScope
    public ListActivityViewModelFactory listActivityViewModelFactory()
    {
        return new ListActivityViewModelFactory(mDate);
    }
}
