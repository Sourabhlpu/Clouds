package com.example.personal.clouds.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by personal on 12/18/2017.
 * Dao is an abstract class that defines methods to access data from the database.
 * Its an easy way to abstract the details of querying the data.
 *
 */

@Dao
public interface WeatherDao {

    //@Insert room inserts all the parameters into the database in a single transaction.
    //We want to always insert the latest data so we always replace in case of conflict.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(List<WeatherEntity> weather);

    //@Query is the main annotation in room. It allows us to have both read/write operations.
    // this query returns all the data that's greater than the given date.
    // we are returning a LiveData so that we can observe this data.
    @Query("SELECT * FROM weather WHERE date >= :date")
    LiveData<List<WeatherEntity>> loadAll(Date date);

    //this method returns just one single weather data for a given date.
    @Query("SELECT * FROM weather WHERE date = :date")
    LiveData<WeatherEntity> getWeatherByDate(Date date);


    //query to get the count for the forecast days.
    @Query("SELECT COUNT(id) FROM weather WHERE date >= :date")
    int countAllFutureWeather(Date date);

    //lets just delete some old useless data.
    @Query("DELETE FROM weather where date < :date")
    void deleteOldWeather(Date date);
}
