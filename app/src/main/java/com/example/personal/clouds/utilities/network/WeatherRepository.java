package com.example.personal.clouds.utilities.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.personal.clouds.R;
import com.example.personal.clouds.model.pojo.Weather;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by personal on 12/11/2017.
 */

public class WeatherRepository {

    private WeatherClient weatherClient;

    public LiveData<Weather.Forecast> getWeatherForecast()
    {
        final MutableLiveData<Weather.Forecast> data = new MutableLiveData<>();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(String.valueOf(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        WeatherClient client = retrofit.create(WeatherClient.class);
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
