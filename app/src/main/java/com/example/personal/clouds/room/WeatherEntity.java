package com.example.personal.clouds.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by personal on 12/18/2017.
 */

@Entity
public class WeatherEntity {

    @PrimaryKey
    private int id;

   private long  mDate;

   private int mWeatherId;

   private double mMin;

   private double mMax;

   private float mHumidity;

   private float mWindSpeed;

   private float mWindDirection;

   private float mPressure;

   public long getDate()
   {
       return mDate;
   }

   public void setDate(long date)
   {
       mDate = date;
   }

   public int getWeatherId()
   {
       return mWeatherId;
   }

   public void setWeatherId(int weatherId)
   {
       mWeatherId = weatherId;
   }

   public double getMin()
   {
       return mMin;
   }

   public void setmMin(double min)
   {
       mMin = min;
   }

    public double getMax()
    {
        return mMax;
    }

    public void setmMax(double max)
    {
        mMax = max;
    }

    public float getHumidity()
    {
        return mHumidity;
    }

    public void setHumidity(float humidity)
    {
        mHumidity = humidity;
    }

    public float getWindSpeed()
    {
        return  mWindSpeed;
    }

    public void setWindSpeed(float windSpeed)
    {
        mWindSpeed = windSpeed;
    }

    public float getWindDirection()
    {
        return mWindDirection;
    }

    public void setmWindDirection(float windDirection)
    {
        mWindDirection = windDirection;
    }

    public float getmPressure()
    {
        return mPressure;
    }

    public void setmPressure(float pressure)
    {
        mPressure = pressure;
    }



}
