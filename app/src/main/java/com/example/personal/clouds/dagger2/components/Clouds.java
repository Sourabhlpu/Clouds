package com.example.personal.clouds.dagger2.components;

import android.app.Activity;
import android.app.Application;

import com.example.personal.clouds.R;
import com.example.personal.clouds.dagger2.modules.ContextModule;
import com.example.personal.clouds.dagger2.modules.WeatherModule;

/**
 * Created by personal on 2/4/2018.
 */

public class Clouds extends Application {

    private static WeatherRepositoryComponent weatherRepositoryComponent;

    public static Clouds get(Activity activity)
    {
        return (Clouds) activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        weatherRepositoryComponent = DaggerWeatherRepositoryComponent.builder()
                .contextModule(new ContextModule(this))
                .weatherModule(new WeatherModule(this.getString(R.string.base_url)))
                .build();
    }

    public static WeatherRepositoryComponent getWeatherRepositoryComponent()
    {
        return weatherRepositoryComponent;
    }
}
