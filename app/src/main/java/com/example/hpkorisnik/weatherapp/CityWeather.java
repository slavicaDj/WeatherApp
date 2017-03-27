package com.example.hpkorisnik.weatherapp;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by HP KORISNIK on 21-Mar-17.
 */

public class CityWeather implements Serializable {

    private String name;
    private String weatherDescription;
    private String weatherIcon;
    private String mainTemp;
    private String windSpeed;
    private String cloudsAll;
    private String sysSunrise;
    private String sysSunset;

    public CityWeather(String name, String weatherDescription, String weatherIcon, String mainTemp, String windSpeed, String cloudsAll, String sysSunrise, String sysSunset) {
        this.name = name;
        this.weatherDescription = weatherDescription;
        this.weatherIcon = weatherIcon;
        this.mainTemp = mainTemp;
        this.windSpeed = windSpeed;
        this.cloudsAll = cloudsAll;
        this.sysSunrise = sysSunrise;
        this.sysSunset = sysSunset;
    }

    public static CityWeather getCityWeatherInfoFromJSON(JSONObject jsonObject) {
        CityWeather cityWeather;
        String code = "";
        try {
            code = jsonObject.getString("cod");
            if (!code.equals("404")) {
                cityWeather = new CityWeather(
                        jsonObject.getString("name"),
                        jsonObject.getJSONArray("weather").getJSONObject(0).getString("description"),
                        jsonObject.getJSONArray("weather").getJSONObject(0).getString("icon"),
                        jsonObject.getJSONObject("main").getString("temp"),
                        jsonObject.getJSONObject("wind").getString("speed"),
                        jsonObject.getJSONObject("clouds").getString("all"),
                        jsonObject.getJSONObject("sys").getString("sunrise"),
                        jsonObject.getJSONObject("sys").getString("sunset")
                );
                System.out.println("everything ok, name of city/code : " + jsonObject.getString("name") + "/" + code);
            }
            else {
                System.out.println("cod is 404, code is: " + code);
                cityWeather = null;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("exception caught, code is: " + code);
            cityWeather = null;
        }
        return cityWeather;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public String getMainTemp() {
        return mainTemp;
    }

    public void setMainTemp(String mainTemp) {
        this.mainTemp = mainTemp;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getCloudsAll() {
        return cloudsAll;
    }

    public void setCloudsAll(String cloudsAll) {
        this.cloudsAll = cloudsAll;
    }

    public String getSysSunrise() {
        return sysSunrise;
    }

    public void setSysSunrise(String sysSunrise) {
        this.sysSunrise = sysSunrise;
    }

    public String getSysSunset() {
        return sysSunset;
    }

    public void setSysSunset(String sysSunset) {
        this.sysSunset = sysSunset;
    }
}
