package com.example.personal.clouds.model.pojo;

import java.util.List;

/**
 * Created by personal on 12/10/2017.
 */

public class Weather {

    public Weather() {

    }

    private City city;
    private List<Forecast> list;


    public class City {
        private String id;
        private String name;
        private Coordinates coord;
        private String country;

        public String getId()
        {
            return id;
        }

        public String getName()
        {
            return name;
        }
        public Coordinates getCoord()
        {
            return coord;
        }
        public String getCountry()
        {
            return country;
        }

    }

    public class Coordinates {
        private double lon;
        private double lat;

        public double getLon()
        {
            return lon;
        }
        public double getLat()
        {
            return lat;
        }
    }

    public class Forecast {
        private long dt;
        private Main main;
        private Wind wind;
        private List<WeatherList> weather;

        public long getDt()
        {
            return dt;
        }
        public Main getMain()
        {
            return main;
        }
        public Wind getWind()
        {
            return wind;
        }
        public List<WeatherList> getWeatherList()
        {
            return weather;
        }

    }

    public class Main {
        private double temp_min;
        private double temp_max;
        private double pressure;
        private int humidity;

        public double getTemp_min()
        {
            return temp_min;
        }

        public double getTemp_max()
        {
            return temp_max;
        }
        public double getPressure()
        {
            return pressure;
        }
        public int getHumidity()
        {
            return humidity;
        }
    }

    public class Wind {
       private double speed;
       private double deg;

       public double getSpeed()
       {
           return getSpeed();
       }
       public double getDeg()
       {
           return deg;
       }

    }

    public class WeatherList {
        private int id;

        public int getId()
        {
            return id;
        }
    }

    public List<Forecast> getList()
    {
        return list;
    }

    public City getCity()
    {
        return city;
    }

}
