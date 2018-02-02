package com.example.personal.clouds.dagger2.components;

import com.example.personal.clouds.dagger2.Scopes.CloudsApplicationScope;
import com.example.personal.clouds.data.WeatherRepository;

import dagger.Component;

/**
 * Created by personal on 2/2/2018.
 */

@CloudsApplicationScope
@Component(modules = WeatherRepository.class)
public interface WeatherRepositoryComponent {

    WeatherRepository getWeatherRepository();
}
