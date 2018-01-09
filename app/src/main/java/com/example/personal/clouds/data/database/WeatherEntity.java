package com.example.personal.clouds.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by personal on 12/18/2017.
 * Entity class acts as a mapping of database table into the objects.
 * So for each entity a table is created within the associated database.
 * By default each room creates a column for each field in the entitiy class.
 */


/**
 * @Entity anotation is used to define an entity in room.
 * By default the name of the class is the table name.
 * Here we are setting the table name to weather.
 * Defining index for some fields and speed up the the queries. We are doing it for Date and setting
 * it to be unique.
 */



@Entity(tableName = "weather", indices = {@Index(value = {"date"}, unique = true)})
public class WeatherEntity {

    //For Room its mandatory to define a primary key. We want the id's to be autogenerated so setting
    //autoGenerate to true.
    @PrimaryKey(autoGenerate = true)
    private int id;


    /**
     * So for room to be able to persist a field, it needs to have an access to that field.
     * To do that we can either make the fields public or we can do it as below.
     * Here we are setting the fields to be private and then providing a public getter methods for each.
     *
     */
    private int weatherIconId;
    private Date date;
    private double min;
    private double max;
    private double humidity;
    private double pressure;
    private double wind;
    private double degrees;

    /**
     * Room either requires either an empty constructor(if fields are accessible) or a constructor
     * like below.
     * @param id
     * @param weatherIconId
     * @param date
     * @param min
     * @param max
     * @param humidity
     * @param pressure
     * @param wind
     * @param degrees
     */


    public WeatherEntity(int id, int weatherIconId, Date date, double min, double max, double humidity, double pressure, double wind, double degrees) {
        this.id = id;
        this.weatherIconId = weatherIconId;
        this.date = date;
        this.min = min;
        this.max = max;
        this.humidity = humidity;
        this.pressure = pressure;
        this.wind = wind;
        this.degrees = degrees;
    }

    /**
     * We do not want to store these entities.
     * @param weatherIconId
     * @param date
     * @param min
     * @param max
     * @param humidity
     * @param pressure
     * @param wind
     * @param degrees
     */

    @Ignore
    public WeatherEntity(int weatherIconId, Date date, double min, double max, double humidity, double pressure, double wind, double degrees) {
        this.weatherIconId = weatherIconId;
        this.date = date;
        this.min = min;
        this.max = max;
        this.humidity = humidity;
        this.pressure = pressure;
        this.wind = wind;
        this.degrees = degrees;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public int getWeatherIconId() {
        return weatherIconId;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public double getWind() {
        return wind;
    }

    public double getDegrees() {
        return degrees;
    }





}
