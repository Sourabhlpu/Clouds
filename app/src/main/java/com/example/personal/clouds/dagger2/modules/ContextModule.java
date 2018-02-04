package com.example.personal.clouds.dagger2.modules;

import android.content.Context;

import com.example.personal.clouds.dagger2.Scopes.CloudsApplicationScope;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by personal on 2/2/2018.
 */
@Module
public class ContextModule {

    Context context;

    public ContextModule(Context context){
        this.context = context;
    }

    @Named("application_context")
    @CloudsApplicationScope
    @Provides
    public Context context(){ return context.getApplicationContext(); }
}
