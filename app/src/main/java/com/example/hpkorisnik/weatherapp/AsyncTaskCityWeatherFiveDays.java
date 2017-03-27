package com.example.hpkorisnik.weatherapp;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by HP KORISNIK on 23-Mar-17.
 */

public class AsyncTaskCityWeatherFiveDays extends AsyncTask<Void,Void,ArrayList<CityWeatherFiveDays>> {

    private String connectionString = "http://api.openweathermap.org/data/2.5/forecast/daily?cnt=6&appid=7d3d7f48ded7a5dbb5b09587cdb25986&units=metric&q=";
    private String cityName;

    public AsyncTaskCityWeatherFiveDays(String cityName) {
        this.cityName = cityName;
    }

    @Override
    protected ArrayList<CityWeatherFiveDays> doInBackground(Void... params) {

        ArrayList<CityWeatherFiveDays> cityWeatherFiveDaysList = new ArrayList<>();
        String responseData = "";
        try {
            // Creating http request
            URL obj = new URL(connectionString + cityName);
            System.out.println(obj);
            System.out.println("connection made");

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            System.out.println("connection made");
            //add request header
            con.setRequestMethod("GET");

            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            while ((line = br.readLine()) != null)
                responseData += line;
            con.disconnect();

            //int responseCode = con.getResponseCode();

            JSONObject response = new JSONObject(responseData);

            //looping through JSONObject and collecting data for every of 5 days
            for (int i = 1; i < 6; i++) {
                cityWeatherFiveDaysList.add(CityWeatherFiveDays.getCityWeatherFiveDaysFromJSON(response,i));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            cityWeatherFiveDaysList = null;
        }
        return cityWeatherFiveDaysList;
    }

}
