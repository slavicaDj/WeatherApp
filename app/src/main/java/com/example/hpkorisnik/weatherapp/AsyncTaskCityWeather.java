package com.example.hpkorisnik.weatherapp;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by HP KORISNIK on 21-Mar-17.
 */

public class AsyncTaskCityWeather extends AsyncTask<Void,Void,CityWeather> {

    private String connectionString = "http://api.openweathermap.org/data/2.5/weather?&appid=7d3d7f48ded7a5dbb5b09587cdb25986&units=metric&q=";
    private String cityName;

    public AsyncTaskCityWeather(String cityName) {
        this.cityName = cityName;
    }

    @Override
    protected CityWeather doInBackground(Void... params) {

        CityWeather cityWeather = null;
        String responseData = "";
        try {
            // Creating http request
            URL obj = new URL(connectionString + cityName);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add request header
            con.setRequestMethod("GET");

            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            while ((line = br.readLine()) != null)
                responseData += line;
            con.disconnect();

            JSONObject response = new JSONObject(responseData);
            cityWeather = CityWeather.getCityWeatherInfoFromJSON(response);

        }
        catch (Exception e) {
            e.printStackTrace();
            cityWeather = null;
        }
        return cityWeather;
    }
}
