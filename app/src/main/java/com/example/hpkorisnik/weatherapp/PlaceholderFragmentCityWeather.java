package com.example.hpkorisnik.weatherapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragmentCityWeather extends Fragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String CITY_WEATHER = "cityWeather";
    private static final String DEFAULT_CITY = "defaultCity";
    private static  String nameOfDefaultCity;
    private static final String STORAGE =  "storage";

    TextView textViewCity;
    TextView textViewTemperature;
    TextView textViewDescription;
    TextView textViewWindValue;
    TextView textViewCloudsValue;
    TextView textViewSunriseValue;
    TextView textViewSunsetValue;
    ImageView imageView;
    ImageView imageViewPen;
    String lastCity;
    View rootView;

    private Activity currentActivity;


    public PlaceholderFragmentCityWeather() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        currentActivity = this.getActivity();

        nameOfDefaultCity = getString(R.string.name_of_default_city);

        rootView = inflater.inflate(R.layout.fragment_city_weather, container, false);

        textViewCity = (TextView) rootView.findViewById(R.id.textViewCity);
        textViewTemperature = (TextView) rootView.findViewById(R.id.textViewTemperature);
        textViewDescription = (TextView) rootView.findViewById(R.id.textViewDescription);
        textViewCloudsValue = (TextView) rootView.findViewById(R.id.textViewCloudsValue);
        textViewWindValue = (TextView) rootView.findViewById(R.id.textViewWindValue);
        textViewSunriseValue = (TextView) rootView.findViewById(R.id.textViewSunriseValue);
        textViewSunsetValue = (TextView) rootView.findViewById(R.id.textViewSunsetValue);
        imageView = (ImageView)rootView.findViewById(R.id.imageView);
        imageViewPen = (ImageView)rootView.findViewById(R.id.imageViewPen);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //opens new AlertDialog where user types name of new city
        imageViewPen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if (MainActivity.getMainActivity().isInternetAvailable()) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    View view = inflater.inflate(R.layout.dialog_new_city, null);
                    final EditText editTextNewCity = (EditText) view.findViewById(R.id.editTextNewCity);
                    editTextNewCity.setHint("City");

                    builder.setTitle("Enter name of city").setView(view).setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    //run asynctask for first tab
                                    AsyncTaskCityWeather asyncTaskCityWeather = new AsyncTaskCityWeather(editTextNewCity.getText().toString());
                                    asyncTaskCityWeather.execute();
                                    CityWeather cityWeather = null;

                                    //run asynctask for second tab
                                    AsyncTaskCityWeatherFiveDays asyncTaskCityWeatherFiveDays = new AsyncTaskCityWeatherFiveDays(editTextNewCity.getText().toString());
                                    asyncTaskCityWeatherFiveDays.execute();
                                    ArrayList<CityWeatherFiveDays> cityWeatherFiveDaysList = null;

                                    try {
                                        //try to fetch data about entered city
                                        cityWeather = asyncTaskCityWeather.get();
                                        cityWeatherFiveDaysList = asyncTaskCityWeatherFiveDays.get();

                                        //if city is found, both tabs are updated
                                        if ((cityWeather != null) && (cityWeatherFiveDaysList != null)) {
                                            updateUI(cityWeather);
                                            MainActivity.getPlaceholderFragmentCityWeatherFiveDays().updateUI(cityWeatherFiveDaysList);
                                        }
                                        //if city is not found, toast is showed
                                        else {
                                            Toast.makeText(v.getContext(), "City not found", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    catch(Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else {
                    Toast.makeText(v.getContext(), "Internet connection not available", Toast.LENGTH_LONG).show();
                }
            }
        });
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        if (MainActivity.getMainActivity().isInternetAvailable()) {
            //internet is available
            //read just name of default city from storage, then get data from API
            AsyncTaskCityWeather asyncTaskCityWeather = new AsyncTaskCityWeather(nameOfDefaultCity);
            asyncTaskCityWeather.execute();

            CityWeather cityWeather = null;
            try {
                cityWeather = asyncTaskCityWeather.get();
                if (cityWeather != null) {
                    updateUI(cityWeather);
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        else {

            //internet is not available
            //read object CityWeather from SharedPreferences
            SharedPreferences appSharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(rootView.getContext().getApplicationContext());
            Gson gson = new Gson();
            String json = appSharedPrefs.getString(CITY_WEATHER, "");
            CityWeather cityWeather = gson.fromJson(json, CityWeather.class);
            if (cityWeather == null) {
                Toast.makeText(getContext(), "Internet connection must be available for first run", Toast.LENGTH_LONG).show();
                this.getActivity().finish();
            }
            else {
                updateUI(cityWeather);
                Toast.makeText(getContext(), "Internet connection not available", Toast.LENGTH_LONG).show();
            }
        }

        return rootView;
    }

    public void updateUI(CityWeather cityWeather) {

        storeCity(rootView, cityWeather);

        lastCity = cityWeather.getName();
        textViewCity.setText(cityWeather.getName());
        textViewTemperature.setText(cityWeather.getMainTemp() + "°C");
        textViewDescription.setText(cityWeather.getWeatherDescription());
        textViewCloudsValue.setText(cityWeather.getCloudsAll());
        textViewWindValue.setText(cityWeather.getWindSpeed());
        textViewSunriseValue.setText(getTimeFromUnixTime(Long.valueOf(cityWeather.getSysSunrise())));
        textViewSunsetValue.setText(getTimeFromUnixTime(Long.valueOf(cityWeather.getSysSunset())));
        Picasso.with(getContext()).load(getString(R.string.iconsUrl) + cityWeather.getWeatherIcon() + ".png").into(imageView);
    }

    private static String getTimeFromUnixTime(long milliseconds ) {
        Date time = new Date(milliseconds*1000);
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        return String.format("%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
    }

    private void storeCity(View rootView, CityWeather cityWeather) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(rootView.getContext().getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cityWeather);
        prefsEditor.putString(CITY_WEATHER, json);
        prefsEditor.apply();
    }


}