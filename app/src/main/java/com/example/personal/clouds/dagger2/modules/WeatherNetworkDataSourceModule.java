package com.example.personal.clouds.dagger2.modules;

import android.content.Context;

import com.example.personal.clouds.dagger2.Scopes.CloudsApplicationScope;
import com.example.personal.clouds.data.network.WeatherClient;
import com.example.personal.clouds.data.network.WeatherNetworkDataSource;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by personal on 2/2/2018.
 */

@Module(includes = {WeatherModule.class, ContextModule.class})
public class WeatherNetworkDataSourceModule {

    @CloudsApplicationScope
    @Provides
    public WeatherNetworkDataSource weatherNetworkDataSource(WeatherClient client,
                                                             @Named("application_context") Context context) {
        return new WeatherNetworkDataSource(client, context);
    }
}
