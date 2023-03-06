package com.example.hw05;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Forecast {

    String dt_txt, description, icon;
    Double temp, temp_min, temp_max, humidity;

    public Forecast(JSONObject value) throws JSONException {
        JSONObject mainvalue = value.getJSONObject("main");
        this.temp = mainvalue.getDouble("temp");
        this.temp_min = mainvalue.getDouble("temp_min");
        this.temp_max = mainvalue.getDouble("temp_max");
        this.humidity = mainvalue.getDouble("humidity");

        JSONArray weathervalue = value.getJSONArray("weather");
        if(weathervalue.length() >0){
            JSONObject firstweather = weathervalue.getJSONObject(0);
            this.description = firstweather.getString("description");
            this.icon = firstweather.getString("icon");
        }
        this.dt_txt = value.getString("dt_txt");

    }

    public Forecast() {
    }

    public String getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
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

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }
}
