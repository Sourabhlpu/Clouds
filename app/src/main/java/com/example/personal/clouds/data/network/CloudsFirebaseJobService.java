package com.example.personal.clouds.data.network;

import com.example.personal.clouds.dagger2.components.Clouds;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import javax.inject.Inject;

/**
 * Created by personal on 12/24/2017.
 */

public class CloudsFirebaseJobService extends JobService {

   @Inject WeatherNetworkDataSource networkDataSource;
    @Override
    public boolean onStartJob(JobParameters job) {
        Clouds.getWeatherRepositoryComponent().injectCloudsFirebaseJobService(this);

        networkDataSource.fetchWeather();

        jobFinished(job,false);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return true;
    }
}
