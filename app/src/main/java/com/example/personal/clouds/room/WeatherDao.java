package com.example.personal.clouds.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

/**
 * Created by personal on 12/18/2017.
 */

@Dao
public interface WeatherDao {
    @Insert
    void save(WeatherEntity weatherEntity);

    @Query("SELECT * FROM WeatherEntity")
    LiveData<WeatherEntity> loadAll();
}
