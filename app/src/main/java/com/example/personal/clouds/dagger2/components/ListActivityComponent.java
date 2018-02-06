package com.example.personal.clouds.dagger2.components;

import com.example.personal.clouds.dagger2.Scopes.ListActivityScope;
import com.example.personal.clouds.dagger2.modules.ListActivityModule;
import com.example.personal.clouds.ui.list.ListActivity;

import dagger.Component;

/**
 * Created by personal on 2/4/2018.
 */

@Component(modules = ListActivityModule.class, dependencies = WeatherRepositoryComponent.class)
@ListActivityScope
public interface ListActivityComponent {

    void injectListActivity(ListActivity listActivity);

}
