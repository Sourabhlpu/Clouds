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

       mViewModel = ViewModelProviders.of(this,factory).get(DetailActivityViewModel.class);

       mViewModel.getWeather().observe(this, weatherEntity -> {

           if(weatherEntity != null) bindWeatherToUI(weatherEntity);
       });
    }

    private void bindWeatherToUI(WeatherEntity weatherEntity) {

        //this is the method that update the UI.
    }
}
