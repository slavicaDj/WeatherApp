package com.example.hpkorisnik.weatherapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by HP KORISNIK on 23-Mar-17.
 */

public class PlaceholderFragmentCityWeatherFiveDays extends Fragment {

    private static final String CITY_WEATHER_LIST = "cityWeatherList";
    private static final String DEFAULT_CITY = "defaultCity";
    private static final String STORAGE =  "storage";

    private static String nameOfDefaultCity;

    private ListView listView;
    private View rootView;

    public PlaceholderFragmentCityWeatherFiveDays() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        nameOfDefaultCity = getString(R.string.name_of_default_city);

        rootView = inflater.inflate(R.layout.fragment_city_weather_five_days, container, false);
        listView = (ListView)rootView.findViewById(R.id.listView);

        if (MainActivity.getMainActivity().isInternetAvailable()) {

            AsyncTaskCityWeatherFiveDays asyncTaskCityWeatherFiveDays = new AsyncTaskCityWeatherFiveDays(nameOfDefaultCity);
            asyncTaskCityWeatherFiveDays.execute();

            try {
                ArrayList<CityWeatherFiveDays> cityWeatherFiveDaysList = asyncTaskCityWeatherFiveDays.get();
                if (cityWeatherFiveDaysList != null) {
                    updateUI(cityWeatherFiveDaysList);
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        else {

            SharedPreferences appSharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(rootView.getContext().getApplicationContext());
            Gson gson = new Gson();
            String json = appSharedPrefs.getString(CITY_WEATHER_LIST, "");
            Type type = new TypeToken<List<CityWeatherFiveDays>>(){}.getType();
            ArrayList<CityWeatherFiveDays> cityWeatherFiveDaysList = gson.fromJson(json, type);

            if (cityWeatherFiveDaysList != null) {
                updateUI(cityWeatherFiveDaysList);
            }

        }

        return rootView;
    }

    public void updateUI(ArrayList<CityWeatherFiveDays> cityWeatherFiveDaysList) {
        storeCityList(cityWeatherFiveDaysList);
        BaseAdapterCityWeather adapter = new BaseAdapterCityWeather(rootView.getContext(), cityWeatherFiveDaysList);
        listView.setAdapter(adapter);
    }

    private void storeCityList(ArrayList<CityWeatherFiveDays> cityWeatherFiveDaysList) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(rootView.getContext().getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cityWeatherFiveDaysList);
        prefsEditor.putString(CITY_WEATHER_LIST, json);
        prefsEditor.apply();
    }


}
