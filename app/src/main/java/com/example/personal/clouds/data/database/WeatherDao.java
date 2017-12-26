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
 */

@Dao
public interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(List<WeatherEntity> weather);

    @Query("SELECT * FROM weather WHERE date >= :date")
    LiveData<List<WeatherEntity>> loadAll(Date date);

    @Query("SELECT * FROM weather WHERE date = :date")

    LiveData<WeatherEntity> getWeatherByDate(Date date);

    @Query("SELECT COUNT(id) FROM weather WHERE date >= :date")

    int countAllFutureWeather(Date date);

    @Query("DELETE FROM weather where date < :date")
    void deleteOldWeather(Date date);
}
