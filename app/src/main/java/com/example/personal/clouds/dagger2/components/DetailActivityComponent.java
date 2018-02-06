package com.example.personal.clouds.dagger2.components;

import com.example.personal.clouds.dagger2.Scopes.DetailActivityScope;
import com.example.personal.clouds.dagger2.modules.DetailActivityModule;
import com.example.personal.clouds.ui.detail.DetailActivity;

import dagger.Component;

/**
 * Created by personal on 2/6/2018.
 */

@Component(modules = DetailActivityModule.class, dependencies = WeatherRepositoryComponent.class)
@DetailActivityScope
public interface DetailActivityComponent {

    public void injectDetailActivity(DetailActivity detailActivity);
}
