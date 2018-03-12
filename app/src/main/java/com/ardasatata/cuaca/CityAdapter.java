package com.ardasatata.cuaca;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.ardasatata.cuaca.Database.CitiesHelper;
import com.ardasatata.cuaca.Database.City;
import com.ardasatata.cuaca.Database.CityHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



/**
 * Created by ardasatata on 3/10/18.
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CustomViewHolder>{

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private LayoutInflater mInflater;
    private ArrayList<City> city;
    private Context context;
    private CityHelper cityHelper;

    private int lastPosition = -1;

    List<City> data= Collections.emptyList();

    public CityAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        cityHelper = new CityHelper(context);
    }


    @Override
    public CityAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(
                viewGroup.getContext());
        View v =
                inflater.inflate(R.layout.list_weather, viewGroup, false);

        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CityAdapter.CustomViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        CustomViewHolder myHolder = (CustomViewHolder) holder;
        City current=city.get(position);


        final int idCity = current.getId_city();
        final String cityName = current.getName();
        final int cityTemp = current.getTemp();
        final String cityWeather = current.getWeather();

        myHolder.cityName.setText(cityName);
        myHolder.cityTemp.setText(Integer.toString(cityTemp)+ "\u2103");
        myHolder.cityWeather.setText(cityWeather);

        setAnimation(holder.itemView, position);

    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return city.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView cityName;
        TextView cityTemp;
        TextView cityWeather;

        // create constructor to get widget reference
        public CustomViewHolder(View itemView) {
            super(itemView);
            cityName = (TextView) itemView.findViewById(R.id.list_city_name);
            cityTemp = (TextView) itemView.findViewById(R.id.list_city_temperature);
            cityWeather = (TextView) itemView.findViewById(R.id.list_city_weather);

            itemView.setOnClickListener(this);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    CharSequence text = "Long CLikc";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    return false;
                }
            });

        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, DetailWeather.class);

            int pos = getAdapterPosition();

            String id_city = Integer.toString(city.get(pos).getId_city());
            intent.putExtra(EXTRA_MESSAGE, id_city);

            context.startActivity(intent);
//
//            int pos = getAdapterPosition();
//
//            Cities current=data.get(pos);
//
//            final int idCity = Integer.parseInt(current.getId());
//            final String cityName = current.getName();
//            final String country = current.getCountry();
//
//            CityHelper cityHelper = new CityHelper(context);
//            City city = new City(idCity,cityName,0,"-");
//
//            cityHelper.open();
//            cityHelper.insert(city);
//            cityHelper.close();
        }


    }


    public void getData(){
        cityHelper.open();
        city = cityHelper.getAllData();
        cityHelper.close();
    }
}

