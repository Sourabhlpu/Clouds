package com.example.personal.clouds.data.network;

import com.example.personal.clouds.model.pojo.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by personal on 12/10/2017.
 */

public interface WeatherClient {

    @GET("data/2.5/forecast/daily")
    Call<Weather> forecastForDays(
            @Query("zip") String zipCode,
            @Query("appid") String apiKey,
            @Query("cnt") String count,
            @Query("mode") String mode,
            @Query("units") String unit
    );
}
