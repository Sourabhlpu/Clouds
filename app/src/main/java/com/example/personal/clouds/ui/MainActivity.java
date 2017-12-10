package com.example.personal.clouds.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.personal.clouds.R;
import com.example.personal.clouds.model.CloudsHomeViewModel;

public class MainActivity extends AppCompatActivity {

    private CloudsHomeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(CloudsHomeViewModel.class);
        viewModel.getWeather().observe(this, weather -> {

        });
    }
}