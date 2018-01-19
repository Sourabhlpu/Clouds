package com.example.personal.clouds.data.database;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by personal on 12/21/2017.
 * Type convertor is needed to convert the data from java types to sql types.
 * In this project we store Date in the WeatherEntity class, now sql doesn't have Date type, so we
 * use DateConverter class for that.
 */

public class DateConverter {


   //this method converts long into a Date object.
@TypeConverter
    public static Date toDate(Long timestamp)
    {
        return timestamp == null ? null : new Date(timestamp);
    }


    //this method converts a Date object into a Long.
    @TypeConverter
    public static Long toTimeStamp(Date date)
    {
       return  date == null ? null : date.getTime();
    }
}
