package com.example.personal.clouds.ui.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.personal.clouds.R;
import com.example.personal.clouds.dagger2.components.Clouds;
import com.example.personal.clouds.dagger2.components.DaggerDetailActivityComponent;
import com.example.personal.clouds.dagger2.components.DetailActivityComponent;
import com.example.personal.clouds.dagger2.modules.DetailActivityModule;
import com.example.personal.clouds.data.database.WeatherEntity;
import com.example.personal.clouds.databinding.ActivityDetailBinding;
import com.example.personal.clouds.utilities.CloudsDateUtils;
import com.example.personal.clouds.utilities.CloudsWeatherUtils;

import java.util.Date;

import javax.inject.Inject;

public class DetailActivity extends AppCompatActivity {

    public static final String WEATHER_ID_EXTRA = "WEATHER_ID_EXTRA";



    @Inject
    DetailActivityViewModelFactory factory;

     DetailActivityViewModel mViewModel;
     ActivityDetailBinding mDetailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        long timestamp = getIntent().getLongExtra(WEATHER_ID_EXTRA, -1);
        Date date = new Date(timestamp);

        injectDependencies(date);


        /**
         *  here we get the view model associated with this activity.
         *  Since ViewModelProviders only take the view models constructors with no arguments
         *  we use view model factory.
         *  This only returns a new view model instace when the actvity is destroyed.
         */


       mViewModel = ViewModelProviders.of(this,factory).get(DetailActivityViewModel.class);

        /**
         * Here we are observing the weather data. Whenever the data is changed onChanged method will
         * be called and the UI will be updated. This only happens when the UI is in active state.
         */

        mViewModel.getWeather().observe(this, weatherEntity -> {

           if(weatherEntity != null) bindWeatherToUI(weatherEntity);
       });
    }

    private void bindWeatherToUI(WeatherEntity weatherEntity) {


        /****************
         * Weather Icon *
         ****************/

        int weatherId = weatherEntity.getWeatherIconId();
        int weatherImageId = CloudsWeatherUtils.getLargeArtResourceIdForWeatherCondition(weatherId);

        /* Set the resource ID on the icon to display the art */
        mDetailBinding.primaryInfo.weatherIcon.setImageResource(weatherImageId);

        /****************
         * Weather Date *
         ****************/

        long localDateMidnightGmt = weatherEntity.getDate();
        String dateText = CloudsDateUtils.getFriendlyDateString(DetailActivity.this,localDateMidnightGmt, true);
        mDetailBinding.primaryInfo.date.setText(dateText);

        /***********************
         * Weather Description *
         ***********************/

        /* Use the weatherId to obtain the proper description */
        String description = CloudsWeatherUtils.getStringForWeatherCondition(DetailActivity.this,weatherId);

        /* Create the accessibility (a11y) String from the weather description */
        String descriptionA11y = getString(R.string.a11y_forecast,description);

        /* Set the text and content description (for accessibility purposes) */
        mDetailBinding.primaryInfo.weatherDescription.setText(description);
        mDetailBinding.primaryInfo.weatherDescription.setContentDescription(descriptionA11y);

         /* Set the content description on the weather image (for accessibility purposes) */
        mDetailBinding.primaryInfo.weatherIcon.setContentDescription(descriptionA11y);

        /**************************
         * High (max) temperature *
         **************************/

        double maxInCelcius = weatherEntity.getMax();

        String highString = CloudsWeatherUtils.formatTemperature(DetailActivity.this, maxInCelcius);

        String highA11y = getString(R.string.a11y_high_temp,highString);

        mDetailBinding.primaryInfo.highTemperature.setText(highString);
        mDetailBinding.primaryInfo.highTemperature.setContentDescription(highA11y);

        /*************************
         * Low (min) temperature *
         *************************/

        double minInCelsius = weatherEntity.getMin();
        /*
         * If the user's preference for weather is fahrenheit, formatTemperature will convert
         * the temperature. This method will also append either °C or °F to the temperature
         * String.
         */
        String lowString = CloudsWeatherUtils.formatTemperature(DetailActivity.this, minInCelsius);

        String lowA11y = getString(R.string.a11y_low_temp, lowString);

        /* Set the text and content description (for accessibility purposes) */
        mDetailBinding.primaryInfo.lowTemperature.setText(lowString);
        mDetailBinding.primaryInfo.lowTemperature.setContentDescription(lowA11y);

        /************
         * Humidity *
         ************/

        double humidity = weatherEntity.getHumidity();
        String humidityString = getString(R.string.format_humidity, humidity);
        String humidityA11y = getString(R.string.a11y_humidity, humidityString);

        /* Set the text and content description (for accessibility purposes) */
        mDetailBinding.extraDetails.humidity.setText(humidityString);
        mDetailBinding.extraDetails.humidity.setContentDescription(humidityA11y);

        mDetailBinding.extraDetails.humidityLabel.setContentDescription(humidityA11y);

        /****************************
         * Wind speed and direction *
         ****************************/
/* Read wind speed (in MPH) and direction (in compass degrees)*/
         double windSpeed = weatherEntity.getWind();
         double windDirection = weatherEntity.getDegrees();
         String windString = CloudsWeatherUtils
                 .getFormattedWind(DetailActivity.this, windSpeed, windDirection);

        String windA11y = getString(R.string.a11y_wind, windString);

        /* Set the text and content description (for accessibility purposes) */
        mDetailBinding.extraDetails.windMeasurement.setText(windString);
        mDetailBinding.extraDetails.windMeasurement.setContentDescription(windA11y);
        mDetailBinding.extraDetails.windLabel.setContentDescription(windA11y);

        /************
         * Pressure *
         ************/
        double pressure = weatherEntity.getPressure();

        /*
         * Format the pressure text using string resources. The reason we directly access
         * resources using getString rather than using a method from SunshineWeatherUtils as
         * we have for other data displayed in this Activity is because there is no
         * additional logic that needs to be considered in order to properly display the
         * pressure.
         */
        String pressureString = getString(R.string.format_pressure, pressure);

        String pressureA11y = getString(R.string.a11y_pressure, pressureString);

        /* Set the text and content description (for accessibility purposes) */
        mDetailBinding.extraDetails.pressure.setText(pressureString);
        mDetailBinding.extraDetails.pressure.setContentDescription(pressureA11y);
        mDetailBinding.extraDetails.pressureLabel.setContentDescription(pressureA11y);
    }

    private void injectDependencies(Date date)
    {

        DetailActivityComponent detailActivityComponent = DaggerDetailActivityComponent.builder()
                .detailActivityModule(new DetailActivityModule(this,date))
                .weatherRepositoryComponent(Clouds.get(this).getWeatherRepositoryComponent())
                .build();

        detailActivityComponent.injectDetailActivity(this);
    }
}
