package com.example.personal.clouds.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.personal.clouds.R;
import com.example.personal.clouds.model.DetailActivityViewModel;

public class DetailActivity extends AppCompatActivity {


    private DetailActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        viewModel = ViewModelProviders.of(this).get(DetailActivityViewModel.class);
        viewModel.getWeather().observe(this, WeatherEntity -> {
            //update the UI.
        });
    }
}
