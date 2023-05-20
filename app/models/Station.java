package models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.Logger;
import play.db.jpa.Model;

import static java.lang.Math.pow;

@Entity
public class Station extends Model
{
    public String name;
    public double latitude;
    public double longitude;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Reading> readings = new ArrayList<Reading>();

    public Station(String name, double latitude, double longitude)
    {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Usage of comparator. Taken from
    // https://www.geeksforgeeks.org/how-to-sort-an-arraylist-of-objects-by-property-in-java/
    public static Comparator<Station> StationNameComparator = new Comparator<Station>() {

        public int compare(Station s1, Station s2) {
            String StationName1 = s1.name.toUpperCase();
            String StationName2 = s2.name.toUpperCase();

            return StationName1.compareTo(StationName2);
        }
    };

    public double tempTrend(){
        if (readings.size() > 2) {
            double temp1 = readings.get(readings.size()-1).getTemp();
            double temp2 = readings.get(readings.size()-3).getTemp();
            Logger.info("Temp1 " + readings.get(readings.size()-1).getTemp());
            Logger.info("Temp2 " + readings.get(readings.size()-3).getTemp());
            return temp2 - temp1;

        } else{
            return 0;
        }
    }

    public double windTrend(){
        if (readings.size() > 2) {
            double wind1 = readings.get(readings.size()-1).getWindSpeed();
            double wind2 = readings.get(readings.size()-3).getWindSpeed();

            return wind2 - wind1;

        } else{
            return 0;
        }
    }

    public double pressureTrend(){
        if (readings.size() > 2) {
            double pressure1 = readings.get(readings.size()-1).getPressure();
            double pressure2 = readings.get(readings.size()-3).getPressure();

            return pressure2 - pressure1;
        } else{
            return 0;
        }
    }

    public String getLatestWeather(){
        if (!readings.isEmpty()) {
            String latestCode = readings.get(readings.size() - 1).getCode();
            String latestWeather;
            switch (latestCode) {
                case "100":
                    latestWeather = "Clear";
                    break;
                case "200":
                    latestWeather = "Partial Clouds";
                    break;
                case "300":
                    latestWeather = "Cloudy";
                    break;
                case "400":
                    latestWeather = "Sunny";
                    break;
                case "500":
                    latestWeather = "Light Showers";
                    break;
                case "600":
                    latestWeather = "Heavy Showers";
                    break;
                case "700":
                    latestWeather = "Rain";
                    break;
                case "800":
                    latestWeather = "Thunder";
                    break;
                default:
                    latestWeather = "N/A";
                    break;
            }

            return latestWeather;
        } else{
            return "N/A";
        }
    }

    public String getLatestTempC(){
        if (!readings.isEmpty()) {
            return String.format("%.1f", readings.get(readings.size() - 1).getTemp());
        } else{
            return "N/A";
        }
    }

    public String getLatestTempF(){
        if (!readings.isEmpty()) {
            double tempCelcius;
            tempCelcius = readings.get(readings.size() - 1).getTemp();
            return String.format("%.1f", (tempCelcius * (9.0 / 5.0)) + 32);
        } else {
            return "N/A";
        }
    }

    public String maxTemperature(){
        if (!readings.isEmpty()) {
            double maxTemp = readings.get(0).getTemp();

            for(Reading reading: readings){
                if(reading.getTemp() > maxTemp){
                    maxTemp = reading.getTemp();
                }
            }
            return String.format("%.1f",maxTemp);
        } else {
            return "N/A";
        }
    }

    public String minTemperature(){
        if (!readings.isEmpty()) {
            double minTemp = readings.get(0).getTemp();;

            for(Reading reading: readings){
                if(reading.getTemp() < minTemp){
                    minTemp = reading.getTemp();
                }
            }
            return String.format("%.1f",minTemp);
        } else {
            return "N/A";
        }
    }

    public String getLatestWindSpeed(){
        if (!readings.isEmpty()) {
            return String.valueOf(readings.get(readings.size() - 1).getWindSpeed());
        } else{
            return "N/A";
        }
    }

    public String getLatestBeaufort(){
        if (!readings.isEmpty()) {
            double windSpeed = readings.get(readings.size() - 1).getWindSpeed();

            if (windSpeed >= 0 && windSpeed < 1) {
                return "0";
            } else if (windSpeed >= 1 && windSpeed < 6) {
                return "1";
            } else if (windSpeed >= 6 && windSpeed < 12) {
                return "2";
            } else if (windSpeed >= 12 && windSpeed < 20) {
                return "3";
            } else if (windSpeed >= 20 && windSpeed < 29) {
                return "4";
            } else if (windSpeed >= 29 && windSpeed < 39) {
                return "5";
            } else if (windSpeed >= 39 && windSpeed < 50) {
                return "6";
            } else if (windSpeed >= 50 && windSpeed < 62) {
                return "7";
            } else if (windSpeed >= 62 && windSpeed < 75) {
                return "8";
            } else if (windSpeed >= 75 && windSpeed < 89) {
                return "9";
            } else if (windSpeed >= 89 && windSpeed < 103) {
                return "10";
            } else {
                return "11";
            }
        } else{
            return "N/A";
        }
    }

    public String getLatestWindDirection(){
        if (!readings.isEmpty()) {
            double windDirection;
            windDirection = readings.get(readings.size() - 1).getWindDirection();

            if (windDirection >= 348.75 || windDirection < 11.25) {
                return "North";
            } else if (windDirection >= 11.25 && windDirection < 33.75) {
                return "NNE";
            } else if (windDirection >= 33.75 && windDirection < 56.25) {
                return "NE";
            } else if (windDirection >= 56.25 && windDirection < 78.75) {
                return "ENE";
            } else if (windDirection >= 78.75 && windDirection < 101.25) {
                return "East";
            } else if (windDirection >= 101.25 && windDirection < 123.75) {
                return "ESE";
            } else if (windDirection >= 123.75 && windDirection < 146.25) {
                return "SE";
            } else if (windDirection >= 146.25 && windDirection < 168.75) {
                return "SSE";
            } else if (windDirection >= 168.75 && windDirection < 191.25) {
                return "South";
            } else if (windDirection >= 191.25 && windDirection < 213.75) {
                return "SSW";
            } else if (windDirection >= 213.75 && windDirection < 236.25) {
                return "SW";
            } else if (windDirection >= 236.26 && windDirection < 258.75) {
                return "WSW";
            } else if (windDirection >= 258.75 && windDirection < 281.25) {
                return "West";
            } else if (windDirection >= 281.25 && windDirection < 303.75) {
                return "WNW";
            } else if (windDirection >= 303.75 && windDirection < 326.25) {
                return "NW";
            } else if (windDirection >= 326.25 && windDirection < 348.75) {
                return "NNW";
            } else {
                return "N/A";
            }
        } else{
            return "N/A";
        }
    }

    public String getWindChill(){
        if (!readings.isEmpty()) {
            double windChill;
            double tempCelcius = readings.get(readings.size() - 1).getTemp();
            double windSpeed = readings.get(readings.size() - 1).getWindSpeed();
            windChill = 13.12 + (0.6215*tempCelcius) - (11.37*pow(windSpeed, 0.16)) + (0.3965*tempCelcius*(pow(windSpeed, 0.16)));
            return String.format("%.1f", windChill);
        } else {
            return "N/A";
        }
    }

    public String maxWindSpeed(){
        if (!readings.isEmpty()) {
            double maxWind = readings.get(0).getWindSpeed();

            for(Reading reading: readings){
                if(reading.getWindSpeed() > maxWind){
                    maxWind = reading.getWindSpeed();
                }
            }
            return String.format("%.1f",maxWind);
        } else {
            return "N/A";
        }
    }

    public String minWindSpeed(){
        if (!readings.isEmpty()) {
            double minSpeed = readings.get(0).getWindSpeed();;

            for(Reading reading: readings){
                if(reading.getWindSpeed() < minSpeed){
                    minSpeed = reading.getWindSpeed();
                }
            }
            return String.format("%.1f",minSpeed);
        } else {
            return "N/A";
        }
    }

    public String getLatestPressure(){
        if (!readings.isEmpty()) {
            return String.valueOf(readings.get(readings.size() - 1).getPressure());
        } else{
            return "N/A";
        }
    }

    public String maxPressure(){
        if (!readings.isEmpty()) {
            double maxPressure = readings.get(0).getPressure();

            for(Reading reading: readings){
                if(reading.getPressure() > maxPressure){
                    maxPressure = reading.getPressure();
                }
            }
            return String.format("%.0f",maxPressure);
        } else{
            return "N/A";
        }
    }

    public String minPressure(){
        if (!readings.isEmpty()) {
            double minPressure = readings.get(0).getPressure();

            for(Reading reading: readings){
                if(reading.getPressure() < minPressure){
                    minPressure = reading.getPressure();
                }
            }
            return String.format("%.0f",minPressure);}
        else{
            return "N/A";
        }
    }
}
