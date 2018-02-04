package com.example.personal.clouds.dagger2.components;

import android.support.v7.widget.RecyclerView;

import com.example.personal.clouds.dagger2.Scopes.ListActivityScope;
import com.example.personal.clouds.ui.list.ForecastAdapter;
import com.example.personal.clouds.ui.list.ListActivityViewModelFactory;

import dagger.Component;

/**
 * Created by personal on 2/4/2018.
 */

@Component(dependencies = WeatherRepositoryComponent.class)
@ListActivityScope
public interface ListActivityComponent {

    ForecastAdapter getForecastAdapter();
    RecyclerView.LayoutManager getLayoutManager();
    ListActivityViewModelFactory getListActivityViewModelFactory();

}
