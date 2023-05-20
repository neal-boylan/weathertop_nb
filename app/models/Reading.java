package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Reading extends Model
{
    public String dateAndTime;
    public String code;
    public double temp;
    public double windSpeed;
    public double windDirection;
    public int pressure;

    public Reading(String dateAndTime, String code, double temp, double windSpeed, double windDirection, int pressure)
    {
        this.dateAndTime = dateAndTime;
        this.code = code;
        this.temp = temp;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.pressure = pressure;
    }

    public String getCode(){
        return code;
    }

    public double getTemp() {
        return temp;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getWindDirection() {
        return windDirection;
    }

    public int getPressure() {
        return pressure;
    }
}

