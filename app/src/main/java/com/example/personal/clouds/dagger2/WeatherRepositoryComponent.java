package com.example.personal.clouds.dagger2;

import com.example.personal.clouds.data.WeatherRepository;

import dagger.Component;

/**
 * Created by personal on 2/2/2018.
 */

@Component
public interface WeatherRepositoryComponent {

    WeatherRepository getWeatherRepository();
}
