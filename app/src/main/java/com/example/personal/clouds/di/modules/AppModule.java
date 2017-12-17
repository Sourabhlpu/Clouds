package com.example.personal.clouds.di.modules;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by personal on 12/17/2017.
 */

@Module
public class AppModule {

    Application mApplication;

    public AppModule(Application application)
    {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application providesApplication()
    {
        return mApplication;
    }

}
