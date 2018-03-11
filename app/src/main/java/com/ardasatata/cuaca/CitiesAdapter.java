package com.ardasatata.cuaca;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
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

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.CustomViewHolder>{

    private LayoutInflater mInflater;
    private ArrayList<Cities> cities;
    private Context context;
    private CitiesHelper citiesHelper;



    List<Cities> data= Collections.emptyList();

    public CitiesAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        citiesHelper = new CitiesHelper(context);
    }

    public CitiesAdapter(Context context, List<Cities> data) {
        mInflater= LayoutInflater.from(context);
        this.context = context;
        citiesHelper = new CitiesHelper(context);
        this.data=data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView cityName;
        private TextView countryName;

        public ViewHolder(View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.search_cityName);
            countryName = itemView.findViewById(R.id.search_country);
        }
    }

    @Override
    public CitiesAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(
                viewGroup.getContext());
        View v =
                inflater.inflate(R.layout.list_cities, viewGroup, false);

        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CitiesAdapter.CustomViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        CustomViewHolder myHolder = (CustomViewHolder) holder;
        Cities current=data.get(position);


        final int idCity = Integer.parseInt(current.getId());
        final String cityName = current.getName();
        final String country = current.getCountry();

        myHolder.cityName.setText(cityName);
        myHolder.cityCountry.setText(country);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void getData() throws IOException {

        cities = citiesHelper.getAllData();

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView cityName;
        TextView cityCountry;

        // create constructor to get widget reference
        public CustomViewHolder(View itemView) {
            super(itemView);
            cityName = (TextView) itemView.findViewById(R.id.search_cityName);
            cityCountry = (TextView) itemView.findViewById(R.id.search_country);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
//            Intent intent = new Intent(context, DetailWeather.class);
//            context.startActivity(intent);

            int pos = getAdapterPosition();

            Cities current=data.get(pos);

            final int idCity = Integer.parseInt(current.getId());
            final String cityName = current.getName();
            final String country = current.getCountry();

            CityHelper cityHelper = new CityHelper(context);
            City city = new City(idCity,cityName,0,"-");

            cityHelper.open();
            ArrayList<City> allCity = cityHelper.getAllData();
            cityHelper.close();

            for (City city1 : allCity){
                if (city1.getId_city()==city.getId_city()){
                    CharSequence text = "The city already added!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    break;
                }
                else{
                    cityHelper.open();
                    cityHelper.insert(city);
                    cityHelper.close();
                    break;
                }
            }


        }
    }


}

