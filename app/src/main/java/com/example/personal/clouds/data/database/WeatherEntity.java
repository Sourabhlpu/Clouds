package com.example.personal.clouds.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by personal on 12/18/2017.
 */

@Entity(tableName = "weather", indices = {@Index(value = {"mDate"}, unique = true)})
public class WeatherEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;


   private Date mDate;

   private int mWeatherId;

   private double mMin;

   private double mMax;

   private double mHumidity;

   private double mWindSpeed;

   private double mWindDirection;

   private double mPressure;


   public WeatherEntity(int id, int weatherId, Date date, double min, double max, double humidity, double pressure, double wind, double degrees)
   {
       this.id = id;
       mWeatherId = weatherId;
       mDate = date;
       mMin = min;
       mMax = max;
       mHumidity = humidity;
       mWindSpeed = wind;
       mWindDirection = degrees;
       mPressure = pressure;

   }

   public int getId()
   {
       return id;
   }

   public Date getDate()
   {
       return mDate;
   }



   public int getWeatherId()
   {
       return mWeatherId;
   }



   public double getMin()
   {
       return mMin;
   }



    public double getMax()
    {
        return mMax;
    }



    public double getHumidity()
    {
        return mHumidity;
    }



    public double getWindSpeed()
    {
        return  mWindSpeed;
    }



    public double getWindDirection()
    {
        return mWindDirection;
    }


    public double getmPressure()
    {
        return mPressure;
    }





}
