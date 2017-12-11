package com.example.personal.clouds.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.personal.clouds.R;
import com.example.personal.clouds.model.CloudsHomeViewModel;
import com.example.personal.clouds.model.pojo.Weather;
import com.example.personal.clouds.utilities.WeatherClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private CloudsHomeViewModel viewModel;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // viewModel = ViewModelProviders.of(this).get(CloudsHomeViewModel.class);
       // viewModel.getWeather().observe(this, weather -> {

        //});

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        WeatherClient client = retrofit.create(WeatherClient.class);
        Call<Weather> call = client.forecastForDays("201301" + ",in"
                ,getString(R.string.api_key)
                ,getString(R.string.days_forecast)
                ,getString(R.string.response_mode)
                ,getString(R.string.units));

        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {

                if(response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();

                    Weather weather = response.body();
                    Log.v(LOG_TAG,"The response is " + weather.getList().size());
                }
                else {
                    Toast.makeText(MainActivity.this, "Server error", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {

                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                Log.v(LOG_TAG, t.getMessage() + "");

            }
        });
    }
}
