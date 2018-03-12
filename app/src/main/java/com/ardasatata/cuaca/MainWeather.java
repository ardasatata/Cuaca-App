package com.ardasatata.cuaca;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ardasatata.cuaca.Database.CityHelper;

public class MainWeather extends AppCompatActivity {

    CityAdapter cityAdapter;
    private RecyclerView recyclerCity;
    private CityHelper cityHelper;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cityAdapter = new CityAdapter(getBaseContext());
        cityHelper = new CityHelper(getBaseContext());
        cityAdapter.getData();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        recyclerCity = findViewById(R.id.list_main_weather);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false);
        recyclerCity.setLayoutManager(layoutManager);

        recyclerCity.setAdapter(cityAdapter);
        recyclerCity.setLayoutManager(new LinearLayoutManager(MainWeather.this));


        swipeRefreshLayout = findViewById(R.id.swipe_main);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                System.out.println("refresh");
                refresh();
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.destroyDrawingCache();
                swipeRefreshLayout.clearAnimation();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCity();
            }
        });
    }

    protected void showDetail(){
        Intent intent = new Intent(this, DetailWeather.class);
        startActivity(intent);
    }

    protected void addCity(){
        Intent intent = new Intent(this, SearchCity.class);
        startActivity(intent);
    }

    protected void refresh(){
        cityHelper.open();
        cityHelper.getAllData();
        cityHelper.close();
        recyclerCity.setAdapter(cityAdapter);
        cityAdapter.getData();
    }

}
