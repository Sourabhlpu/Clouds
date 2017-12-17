package com.example.personal.clouds.utilities.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.personal.clouds.R;
import com.example.personal.clouds.di.components.Clouds;
import com.example.personal.clouds.model.pojo.Weather;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by personal on 12/11/2017.
 */

public class WeatherRepository {

    @Inject  private WeatherClient client;
    



    public LiveData<Weather.Forecast> getWeatherForecast()
    {
        Clouds.getNetComponent().inject(this);

        final MutableLiveData<Weather.Forecast> data = new MutableLiveData<>();

        Call<Weather> call = client.forecastForDays("201301" + ",in"
                ,String.valueOf(R.string.api_key)
                ,String.valueOf(R.string.days_forecast)
                ,String.valueOf(R.string.response_mode)
                ,String.valueOf(R.string.units));

        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {

                List<Weather.Forecast> forecasts = response.body().getList();
                data.setValue((Weather.Forecast) forecasts);
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {

            }
        });

        return data;

    }
}
