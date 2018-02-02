package com.example.personal.clouds.dagger2.modules;

import android.app.Activity;
import android.content.Context;

import com.example.personal.clouds.dagger2.Scopes.CloudsApplicationScope;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by personal on 2/2/2018.
 */

@Module
public class ActivityModule {
    private final Context context;

    public ActivityModule(Activity context)
    {
        this.context = context;
    }

    @Named("activity_context")
    @CloudsApplicationScope
    @Provides
    public Context context()
    {
        return context;
    }
}
