package com.example.personal.clouds.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

/**
 * Created by personal on 12/18/2017.
 * This class defines the database and extends the RoomDatabase class.
 * Declaring the database name.
 * Also passing all the entities that are in this database. Here its only one.
 * version defines the database version. Need to increment it if we modify the database in future.
 *
 */

@Database(entities = {WeatherEntity.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class CloudsDatabase extends RoomDatabase {

    private static final String LOG_TAG = CloudsDatabase.class.getSimpleName();
    public static final String DATABASE_NAME = "weather";

    //for getting a singleton
    private static final Object LOCK = new Object();
    private static CloudsDatabase sInstance;

    public static CloudsDatabase getInstance(Context context)
    {
        Log.d(LOG_TAG,"getting the database");

        if(sInstance == null)
        {
            synchronized (LOCK)
            {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        CloudsDatabase.class,CloudsDatabase.DATABASE_NAME).build();
                Log.d(LOG_TAG,"Made new database");

            }
        }
        return sInstance;
    }

    //we have to define the an abstract method to return the Dao type.
    public abstract WeatherDao weatherDao();
}
