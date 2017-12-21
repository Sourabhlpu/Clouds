package com.example.personal.clouds.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.Date;

/**
 * Created by personal on 12/18/2017.
 */

@Dao
public interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(WeatherEntity... weather);

    @Query("SELECT * FROM WeatherEntity")
    LiveData<WeatherEntity> loadAll();

    @Query("SELECT * FROM weather WHERE date = :date")

    WeatherEntity getWeatherByDate(Date date);
}
