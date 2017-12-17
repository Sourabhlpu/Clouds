package com.example.personal.clouds.di.components;

import android.app.Application;

import com.example.personal.clouds.di.modules.AppModule;
import com.example.personal.clouds.di.modules.NetModule;

/**
 * Created by personal on 12/17/2017.
 */

public class Clouds extends Application {

    private static NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule("https://api.openweathermap.org"))
                .build();
    }

    public static NetComponent getNetComponent()
    {
        return mNetComponent;
    }
}
