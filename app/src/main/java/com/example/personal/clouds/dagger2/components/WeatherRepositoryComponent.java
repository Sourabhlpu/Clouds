package com.example.personal.clouds.dagger2.components;

import com.example.personal.clouds.dagger2.Scopes.CloudsApplicationScope;
import com.example.personal.clouds.dagger2.modules.WeatherRepositoryModule;
import com.example.personal.clouds.data.WeatherRepository;
import com.example.personal.clouds.data.network.CloudsSyncIntentService;
import com.example.personal.clouds.data.network.WeatherNetworkDataSource;
import com.example.personal.clouds.ui.detail.DetailActivityViewModel;
import com.example.personal.clouds.ui.list.ListActivityViewModel;

import dagger.Component;

/**
 * Created by personal on 2/2/2018.
 */

@CloudsApplicationScope
@Component(modules = WeatherRepositoryModule.class)
public interface WeatherRepositoryComponent {

    void injectWeatherRepository(WeatherRepository weatherRepository);
    void injectWeatherNetworkDataSource(WeatherNetworkDataSource weatherNetworkDataSource);
    void injectDetailActivityViewModel(DetailActivityViewModel detailActivityViewModel);
    void injectListActivityViewModel(ListActivityViewModel listActivityViewModel);
    void injectCloudsSyncIntentService (CloudsSyncIntentService cloudsSyncIntentService);
}
