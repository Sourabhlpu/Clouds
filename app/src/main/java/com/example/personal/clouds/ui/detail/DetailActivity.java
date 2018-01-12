package com.example.personal.clouds.ui.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.personal.clouds.R;
import com.example.personal.clouds.data.database.WeatherEntity;
import com.example.personal.clouds.di.components.Clouds;

import javax.inject.Inject;

public class DetailActivity extends AppCompatActivity {


    @Inject
    public DetailActivityViewModelFactory factory;

    private DetailActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Clouds.getNetComponent().inject(this);


        /**
         *  here we get the view model associated with this activity.
         *  Since ViewModelProviders only take the view models constructors with no arguments
         *  we use view model factory.
         *  This only returns a new view model instace when the actvity is destroyed.
         */


       mViewModel = ViewModelProviders.of(this,factory).get(DetailActivityViewModel.class);

        /**
         * Here we are observing the weather data. Whenever the data is changed onChanged method will
         * be called and the UI will be updated. This only happens when the UI is in active state.
         */

        mViewModel.getWeather().observe(this, weatherEntity -> {

           if(weatherEntity != null) bindWeatherToUI(weatherEntity);
       });
    }

    private void bindWeatherToUI(WeatherEntity weatherEntity) {

        //this is the method that update the UI.
    }
}
