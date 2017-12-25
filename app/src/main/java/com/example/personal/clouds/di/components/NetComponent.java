package com.example.personal.clouds.di.components;

import com.example.personal.clouds.data.network.CloudsSyncIntentService;
import com.example.personal.clouds.data.network.WeatherNetworkDataSource;
import com.example.personal.clouds.di.modules.AppModule;
import com.example.personal.clouds.di.modules.NetModule;
import com.example.personal.clouds.ui.detail.DetailActivity;
import com.example.personal.clouds.ui.detail.DetailActivityViewModel;
import com.example.personal.clouds.ui.list.CloudsHomeViewModel;
import com.example.personal.clouds.data.WeatherRepository;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by personal on 12/17/2017.
 */

@Singleton
@Component(modules = {AppModule.class, NetModule.class})

public interface NetComponent {

    //void inject(MainActivity activity);
    void inject (WeatherRepository repository);
    void inject (CloudsHomeViewModel viewModel);
    void inject (WeatherNetworkDataSource networkDataSource);
    void inject (CloudsSyncIntentService syncIntentService);
    void inject (DetailActivityViewModel detailActivityViewModel);
    void inject (DetailActivity detailActivity);

}
