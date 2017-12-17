package com.example.personal.clouds.di.components;

import com.example.personal.clouds.di.modules.AppModule;
import com.example.personal.clouds.di.modules.NetModule;
import com.example.personal.clouds.ui.MainActivity;
import com.example.personal.clouds.utilities.network.WeatherRepository;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by personal on 12/17/2017.
 */

@Singleton
@Component(modules = {AppModule.class, NetModule.class})

public interface NetComponent {

    void inject(MainActivity activity);
    void inject (WeatherRepository repository);

}
