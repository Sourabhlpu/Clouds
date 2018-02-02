package com.example.personal.clouds.dagger2.modules;

import android.content.Context;

import com.example.personal.clouds.dagger2.Scopes.CloudsApplicationScope;

import dagger.Provides;

/**
 * Created by personal on 2/2/2018.
 */

public class ContextModule {

    Context context;

    public ContextModule(Context context){
        this.context = context;
    }

    @CloudsApplicationScope
    @Provides
    public Context context(){ return context.getApplicationContext(); }
}
