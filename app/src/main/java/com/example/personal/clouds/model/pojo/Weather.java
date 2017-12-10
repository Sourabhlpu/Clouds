package com.example.personal.clouds.model.pojo;

/**
 * Created by personal on 12/10/2017.
 */

public class Weather {

    private long mDate;
    private int mWeatherId;
    private double mMinTemp;
    private double mMaxTemp;
    private float mHumidity;
    private int mPressure;
    private float mWindSpeed;
    private int mDegrees;

    public long getmDate()
    {
        return mDate;
    }
    public void setmDate(long date)
    {
        mDate = date;
    }

    public int getmWeatherId()
    {
        return mWeatherId;
    }

    public void setmWeatherId(int weatherId)
    {
        mWeatherId = weatherId;
    }

    public double getmMinTemp()
    {
        return mMinTemp;
    }

    public void setmMinTemp(double minTemp)
    {
        mMinTemp = minTemp;
    }

    public double getmMaxTemp()
    {
        return mMaxTemp;
    }

    public void setmMaxTemp(double maxTemp)
    {
        mMaxTemp = maxTemp;
    }

    public float getmHumidity()
    {
        return mHumidity;
    }

    public void setmHumidity(float humidity)
    {
        mHumidity = humidity;
    }

    public int getmPressure()
    {
        return mPressure;
    }

    public void setmPressure(int pressure)
    {
        mPressure = pressure;
    }

    public Float getmWindSpeed()
    {
        return mWindSpeed;
    }

    public void setmWindSpeed(int windSpeed)
    {
        mWindSpeed = windSpeed;
    }

    public int getmDegrees()
    {
        return mDegrees;
    }

    public void setmDegrees(int degrees)
    {
        mDegrees = degrees;
    }







}
