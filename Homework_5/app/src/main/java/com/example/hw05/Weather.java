package com.example.hw05;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Weather {

    String description, icon;
    Double temp, temp_min, temp_max, pressure, humidity, speed, deg, cloudiness;

    public Weather(JSONObject value) throws JSONException {
        JSONObject mainvalue = value.getJSONObject("main");
        this.temp = mainvalue.getDouble("temp");
        this.temp_min = mainvalue.getDouble("temp_min");
        this.temp_max = mainvalue.getDouble("temp_max");
        this.pressure = mainvalue.getDouble("pressure");
        this.humidity = mainvalue.getDouble("humidity");

        JSONObject windvalue = value.getJSONObject("wind");
        this.speed = windvalue.getDouble("speed");
        this.deg = windvalue.getDouble("deg");

        JSONObject cloudvalue = value.getJSONObject("clouds");
        this.cloudiness = cloudvalue.getDouble("all");

        JSONArray weathervalue = value.getJSONArray("weather");
        if(weathervalue.length() > 0){
            JSONObject actualvalue = weathervalue.getJSONObject(0);
            this.description = actualvalue.getString("description");
            this.icon = actualvalue.getString("icon");

        }

    }

    public Weather() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(Double temp_min) {
        this.temp_min = temp_min;
    }

    public Double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(Double temp_max) {
        this.temp_max = temp_max;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getDeg() {
        return deg;
    }

    public void setDeg(Double deg) {
        this.deg = deg;
    }

    public Double getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(Double cloudiness) {
        this.cloudiness = cloudiness;
    }
}
