package com.example.personal.clouds.ui.list;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.personal.clouds.R;
import com.example.personal.clouds.di.components.Clouds;

import javax.inject.Inject;

public class ListActivity extends AppCompatActivity {

    @Inject
    ListActivityViewModelFactory factory;

    private ListActivityViewModel viewModel;
    private static final String LOG_TAG = ListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Clouds.getNetComponent().inject(this);

       viewModel = ViewModelProviders.of(this,factory).get(ListActivityViewModel.class);

       viewModel.getWeatherList().observe(this, weatherEntities -> {

           Log.e(LOG_TAG, "weather.getMain().getHumidity()" );

        });




    }
}
