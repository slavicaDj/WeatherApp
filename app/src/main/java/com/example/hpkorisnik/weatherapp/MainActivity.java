package com.example.hpkorisnik.weatherapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private static MainActivity mainActivity;
    private static PlaceholderFragmentCityWeather placeholderFragmentCityWeather;
    private static PlaceholderFragmentCityWeatherFiveDays placeholderFragmentCityWeatherFiveDays;
    private static String newDefaultCity;
    private static final String DEFAULT_CITY = "defaultCity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivity = this;

        SharedPreferences storage = getSharedPreferences("storage",MODE_PRIVATE);
        newDefaultCity = storage.getString(DEFAULT_CITY,"");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public static MainActivity getMainActivity () {
        return mainActivity;
    }

    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static PlaceholderFragmentCityWeatherFiveDays getPlaceholderFragmentCityWeatherFiveDays() {
        return placeholderFragmentCityWeatherFiveDays;
    }

    public static PlaceholderFragmentCityWeather getPlaceholderFragmentCityWeather() {
        return placeholderFragmentCityWeather;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
            /*//////////////
            if (MainActivity.getMainActivity().isInternetAvailable()) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this.getApplicationContext());
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_new_city, null);
                final EditText editTextNewCity = (EditText) view.findViewById(R.id.editTextNewCity);
                editTextNewCity.setHint(newDefaultCity);

                builder.setTitle("Enter name of new default city").setView(view).setPositiveButton("Save", new DialogInterface.OnClickListener() {
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
                                MainActivity.getPlaceholderFragmentCityWeather().updateUI(cityWeather);
                                MainActivity.getPlaceholderFragmentCityWeatherFiveDays().updateUI(cityWeatherFiveDaysList);
                                newDefaultCity = cityWeather.getName();
                                storeDefaultCity();
                            }
                            //if city is not found, toast is showed
                            else {
                                Toast.makeText(getApplicationContext(), "City not found", Toast.LENGTH_LONG).show();
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
                Toast.makeText(getApplicationContext(), "Internet connection not available", Toast.LENGTH_LONG).show();
            }*/
        }

        return super.onOptionsItemSelected(item);
    }


    private void storeDefaultCity() {
        SharedPreferences storage = getSharedPreferences("storage",MODE_PRIVATE);
        SharedPreferences.Editor edit = storage.edit();
        edit.putString(DEFAULT_CITY,newDefaultCity);
        edit.apply();
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment = null;
            switch(position) {
                case 0:
                    placeholderFragmentCityWeather = new PlaceholderFragmentCityWeather();
                    fragment = placeholderFragmentCityWeather;
                    break;
                case 1:
                    placeholderFragmentCityWeatherFiveDays = new PlaceholderFragmentCityWeatherFiveDays();
                    fragment = placeholderFragmentCityWeatherFiveDays;
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Current";
                case 1:
                    return "Next 5 days";
            }
            return null;
        }
    }


}
