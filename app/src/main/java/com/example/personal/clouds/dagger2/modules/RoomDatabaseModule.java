package com.example.personal.clouds.dagger2.modules;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.personal.clouds.data.database.CloudsDatabase;
import com.example.personal.clouds.data.database.WeatherDao;

import dagger.Module;
import dagger.Provides;

/**
 * Created by personal on 2/2/2018.
 */

@Module
public class RoomDatabaseModule {

    @Provides
    public WeatherDao weatherDao(CloudsDatabase database)
    {
        return database.weatherDao();
    }

    @Provides
    public CloudsDatabase cloudsDatabase(Application context)
    {
        return  Room
                .databaseBuilder(context,CloudsDatabase.class,CloudsDatabase.DATABASE_NAME)
                .build();
    }
}
