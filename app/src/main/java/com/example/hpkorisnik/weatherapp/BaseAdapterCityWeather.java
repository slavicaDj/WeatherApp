package com.example.hpkorisnik.weatherapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by HP KORISNIK on 23-Mar-17.
 */

public class BaseAdapterCityWeather extends BaseAdapter {

    private ArrayList<CityWeatherFiveDays> listData;
    private Context mContext;

    public BaseAdapterCityWeather(Context aContext, ArrayList<CityWeatherFiveDays> listData) {
        this.listData = listData;
        mContext = aContext;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        final CityWeatherFiveDays cityWeatherFiveDays = listData.get(position);

        View customView = View.inflate(mContext, R.layout.list_city_weather, null);

        TextView textViewDate = (TextView) customView.findViewById(R.id.textViewDate);
        TextView textViewDayValue = (TextView) customView.findViewById(R.id.textViewDayValue);
        TextView textViewNightValue = (TextView) customView.findViewById(R.id.textViewNightValue);
        TextView textViewDescriptionValue = (TextView) customView.findViewById(R.id.textViewDescriptionValue);
        ImageView imageViewDay = (ImageView)customView.findViewById(R.id.imageViewDay);

        textViewDate.setText("" + getDateFromUnixTime(Long.valueOf(cityWeatherFiveDays.getListDt())));
        textViewDayValue.setText(cityWeatherFiveDays.getListTempDay() + "°C");
        textViewNightValue.setText(cityWeatherFiveDays.getListTempNight()+ "°C");
        textViewDescriptionValue.setText(cityWeatherFiveDays.getListWeatherDescription());
        Picasso.with(mContext).load(mContext.getString(R.string.iconsUrl) + cityWeatherFiveDays.getListWeatherIcon() + ".png").into(imageViewDay);

        return customView;
    }

    private static String getDateFromUnixTime(long milliseconds ) {
        Date time = new Date(milliseconds*1000);
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        return String.format("%02d.%02d", cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH));
    }
}
