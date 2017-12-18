package com.example.personal.clouds.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by personal on 12/18/2017.
 */

@Database(entities = {WeatherEntity.class}, version = 1)
public abstract class CloudsDatabase extends RoomDatabase {

    public abstract WeatherDao weatherDao();
}
