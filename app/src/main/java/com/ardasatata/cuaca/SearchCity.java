package com.ardasatata.cuaca;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.ardasatata.cuaca.Database.CitiesHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SearchCity extends AppCompatActivity {

    private RecyclerView recyclerSearch;
    private CitiesAdapter citiesAdapter;
    private CitiesHelper citiesHelper;
    private RecyclerView.LayoutManager mLayoutManager;
    private EditText searchBar;

    String key = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        citiesHelper = new CitiesHelper(getBaseContext());
        recyclerSearch = findViewById(R.id.recyclerSearch);
        searchBar = findViewById(R.id.search_city);


        mLayoutManager = new LinearLayoutManager(this);
        recyclerSearch.setLayoutManager(mLayoutManager);
        recyclerSearch.setAdapter(citiesAdapter);



        //new AsyncFetch().execute();

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                key = s.toString();
                //System.out.println(s);
                new AsyncFetch().execute();
            }
        });
    }

    void getSearchData() throws IOException {

        recyclerSearch.setAdapter(citiesAdapter);

    }

    private class AsyncFetch extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            try {

                // Check if successful connection made

                    // Read data sent from server
                    InputStream input = getResources().openRawResource(R.raw.list_min);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());


            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            }

        }

        @Override
//        protected void onPostExecute(String result) {
//
//            //this method will be running on UI thread
//
//            List<Cities> data=new ArrayList<>();
//
//            try {
//
//                JSONArray jArray = new JSONArray(result);
//
//                // Extract data from json and store into ArrayList as class objects
//                for(int i=0;i<jArray.length();i++){
//                    JSONObject json_data = jArray.getJSONObject(i);
//                    Cities cities = new Cities();
//                    cities.setId(json_data.getString("id"));
//                    cities.setName(json_data.getString("name"));
//                    cities.setCountry(json_data.getString("country"));
//                    data.add(cities);
//                }
//
//                // Setup and Handover data to recyclerview
//                recyclerSearch = (RecyclerView)findViewById(R.id.recyclerSearch);
//                citiesAdapter = new CitiesAdapter(getBaseContext(),data);
//                recyclerSearch.setAdapter(citiesAdapter);
//                recyclerSearch.setLayoutManager(new LinearLayoutManager(SearchCity.this));
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//        }

        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            List<Cities> data=new ArrayList<>();

            try {

                JSONArray jArray = new JSONArray(result);

                // Extract data from json and store into ArrayList as class objects
                for(int i=0;i<jArray.length();i++){

                    JSONObject json_data = jArray.getJSONObject(i);
                    Cities cities = new Cities();
                    cities.setId(json_data.getString("id"));
                    cities.setName(json_data.getString("name"));
                    cities.setCountry(json_data.getString("country"));
                    if (cities.getName()!=null && cities.getName().toLowerCase().contains(key.toLowerCase())){
                        data.add(cities);
                    }

                }

                // Setup and Handover data to recyclerview
                recyclerSearch = (RecyclerView)findViewById(R.id.recyclerSearch);
                citiesAdapter = new CitiesAdapter(getBaseContext(),data);
                recyclerSearch.setAdapter(citiesAdapter);
                recyclerSearch.setLayoutManager(new LinearLayoutManager(SearchCity.this));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

}
