package com.example.personal.clouds.room;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by personal on 12/21/2017.
 */

public class DateConverter {

@TypeConverter
    public static Date toDate(Long timestamp)
    {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimeStamp(Date date)
    {
       return  date == null ? null : date.getTime();
    }
}
