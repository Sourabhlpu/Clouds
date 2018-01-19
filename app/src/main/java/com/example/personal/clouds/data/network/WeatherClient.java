package com.example.personal.clouds.data.network;

import com.example.personal.clouds.model.pojo.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by personal on 12/10/2017.
 * This is the Client where we define the API call. @GET defines delcares that this request uses the
 * HTTP GET method.
 */

public interface WeatherClient {



    // we define a return type of Call<Weather> which helps in mapping the server response directly
    // into the Weather object.
    // @Query defines the query parameters for the Uri.
    @GET("data/2.5/forecast/daily")
    Call<Weather> forecastForDays(
            @Query("zip") String zipCode,
            @Query("appid") String apiKey,
            @Query("cnt") String count,
            @Query("mode") String mode,
            @Query("units") String unit
    );
}
