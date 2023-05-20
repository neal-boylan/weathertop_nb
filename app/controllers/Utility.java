package controllers;

import models.Reading;
import models.Station;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Utility {

    //this function was stolen from Kieron and Cormac on Slack. Cheers lads!
    public static String getDateAndTime(LocalDateTime date){
        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        if (date != null) {
            return FORMATTER.format(date); //uses your formatter to format the date/time from Yaml file
        } else {
            return FORMATTER.format(LocalDateTime.now()); //Get Current Date Time & Set formatted String
        }
    }

    public static String maxTemperature(Station s){
        double maxTemp = s.readings.get(0).getTemp();

        for(Reading reading: s.readings){
            if(reading.getTemp() > maxTemp){
                maxTemp = reading.getTemp();
            }
        }
        return String.format("%.1f",maxTemp);
    }

    public static String minTemperature(Station s){
        double minTemp = s.readings.get(0).getTemp();;

        for(Reading reading: s.readings){
            if(reading.getTemp() < minTemp){
                minTemp = reading.getTemp();
            }
        }
        return String.format("%.1f",minTemp);
    }

}
