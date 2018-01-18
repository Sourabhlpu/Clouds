package com.example.personal.clouds.ui.list;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.personal.clouds.R;
import com.example.personal.clouds.di.components.Clouds;
import com.example.personal.clouds.ui.detail.DetailActivity;

import java.util.Date;

import javax.inject.Inject;

public class ListActivity extends AppCompatActivity implements ForecastAdapter.ForecastAdapterOnItemClickHandler {

    @Inject
    ListActivityViewModelFactory factory;

    private ForecastAdapter mForecastAdapter;
    private RecyclerView mRecyclerView;
    private int mPosition = RecyclerView.NO_POSITION;
    private ProgressBar mLoadingIndicator;


    private ListActivityViewModel viewModel;
    private static final String LOG_TAG = ListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_forecast);

        mLoadingIndicator = (ProgressBar)findViewById(R.id.pb_loading_indicator);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mForecastAdapter = new ForecastAdapter(this,this);

        mRecyclerView.setAdapter(mForecastAdapter);

        Clouds.getNetComponent().inject(this);

       viewModel = ViewModelProviders.of(this,factory).get(ListActivityViewModel.class);

       viewModel.getWeatherList().observe(this, weatherEntities -> {


           mForecastAdapter.swapForecast(weatherEntities);
           if(mPosition == RecyclerView.NO_POSITION) mPosition = 0;
           mRecyclerView.smoothScrollToPosition(mPosition);

           if (weatherEntities != null && weatherEntities.size() != 0) showWeatherDataView();
           else showLoading();

        });

    }

    @Override
    public void onItemClick(Date date) {

        Intent weatherDetailIntent = new Intent(ListActivity.this, DetailActivity.class);
        long timestamp = date.getTime();
        weatherDetailIntent.putExtra(DetailActivity.WEATHER_ID_EXTRA, timestamp);
        startActivity(weatherDetailIntent);

    }

    private void showWeatherDataView() {
        // First, hide the loading indicator
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        // Finally, make sure the weather data is visible
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showLoading() {
        // Then, hide the weather data
        mRecyclerView.setVisibility(View.INVISIBLE);
        // Finally, show the loading indicator
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }
}
