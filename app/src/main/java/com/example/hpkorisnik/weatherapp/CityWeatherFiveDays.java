package com.example.hpkorisnik.weatherapp;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by HP KORISNIK on 23-Mar-17.
 */

public class CityWeatherFiveDays implements Serializable {

    private String cityName;
    private String listDt;
    private String listTempDay;
    private String listTempNight;
    private String listWeatherDescription;
    private String listWeatherIcon;

    public CityWeatherFiveDays(String cityName, String listDt, String listTempDay, String listTempNight, String listWeatherDescription, String listWeatherIcon) {
        this.cityName = cityName;
        this.listDt = listDt;
        this.listTempDay = listTempDay;
        this.listTempNight = listTempNight;
        this.listWeatherDescription = listWeatherDescription;
        this.listWeatherIcon = listWeatherIcon;
    }

    public static CityWeatherFiveDays getCityWeatherFiveDaysFromJSON(JSONObject jsonObject, int i) {
        CityWeatherFiveDays cityWeatherFiveDays;
        try {
            if (!jsonObject.getString("cod").equals("404")) {
                cityWeatherFiveDays = new CityWeatherFiveDays(
                        jsonObject.getJSONObject("city").getString("name"),
                        jsonObject.getJSONArray("list").getJSONObject(i).getString("dt"),
                        jsonObject.getJSONArray("list").getJSONObject(i).getJSONObject("temp").getString("day"),
                        jsonObject.getJSONArray("list").getJSONObject(i).getJSONObject("temp").getString("night"),
                        jsonObject.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description"),
                        jsonObject.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("icon")
                );
            }
            else {
                cityWeatherFiveDays = null;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            cityWeatherFiveDays = null;
        }
        return cityWeatherFiveDays;
    }

    @Override
    public String toString() {
        return "CityWeatherFiveDays{" +
                "cityName='" + cityName + '\'' +
                ", listDt='" + listDt + '\'' +
                ", listTempDay='" + listTempDay + '\'' +
                ", listTempNight='" + listTempNight + '\'' +
                ", listWeatherDescription='" + listWeatherDescription + '\'' +
                ", listWeatherIcon='" + listWeatherIcon + '\'' +
                '}';
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getListDt() {
        return listDt;
    }

    public void setListDt(String listDt) {
        this.listDt = listDt;
    }

    public String getListTempDay() {
        return listTempDay;
    }

    public void setListTempDay(String listTempDay) {
        this.listTempDay = listTempDay;
    }

    public String getListTempNight() {
        return listTempNight;
    }

    public void setListTempNight(String listTempNight) {
        this.listTempNight = listTempNight;
    }

    public String getListWeatherDescription() {
        return listWeatherDescription;
    }

    public void setListWeatherDescription(String listWeatherDescription) {
        this.listWeatherDescription = listWeatherDescription;
    }

    public String getListWeatherIcon() {
        return listWeatherIcon;
    }

    public void setListWeatherIcon(String listWeatherIcon) {
        this.listWeatherIcon = listWeatherIcon;
    }
}
