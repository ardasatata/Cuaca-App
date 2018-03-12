package com.ardasatata.cuaca;

import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.TextView;

import com.ardasatata.cuaca.Database.City;
import com.ardasatata.cuaca.Database.CityHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailWeather extends Activity {

    String id_city;

    TextView cityName;
    TextView cityTemp;
    TextView cityWeather;

    String datax;

    CityHelper cityHelper;

    City cityUpdate;

    float tempConvert;

    ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_weather);

        layout = findViewById(R.id.layout_detail);

        Intent intent = getIntent();
        String message = intent.getStringExtra(CityAdapter.EXTRA_MESSAGE);

        id_city = message;

        cityName = findViewById(R.id.city_name);
        cityTemp = findViewById(R.id.city_temperature);
        cityWeather = findViewById(R.id.city_weather);

        cityHelper = new CityHelper(this);

        new JSONWeatherTask().execute();



    }

    private class JSONWeatherTask extends AsyncTask<String, String,String> {

        @Override
        protected String doInBackground(String... params) {

            String data = ((new OpenWeatherHttp()).getWeatherData(id_city));

//            try {
//                weather = JSONWeatherParser.getWeather(data);
//
//                // Let's retrieve the icon
//                weather.iconData = ( (new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            datax = data;

            return data;

        }




        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

           //  We create out JSONObject from the data
            JSONObject jObj = null;
            try {
                jObj = new JSONObject(result);
                JSONObject json_data = jObj;

                JSONObject sysObj = getObject("sys", jObj);
                JSONObject mainObj = getObject("main", jObj);

                JSONArray jArr = jObj.getJSONArray("weather");
                JSONObject JSONWeather = jArr.getJSONObject(0);

                System.out.println(getString("name",jObj));

                tempConvert = Float.parseFloat(mainObj.getString("temp"));
                tempConvert -= 273.15F;


                cityName.setText(getString("name",jObj));
                cityTemp.setText(Integer.toString((int) tempConvert)+ "\u2103");
                cityWeather.setText(getString("main",JSONWeather));

                cityUpdate = new City(Integer.parseInt(id_city),getString("name",jObj),(int) tempConvert,getString("main",JSONWeather));

                updateWeatherValue(cityUpdate);
                setWeatherColor(getString("main",JSONWeather));

            } catch (JSONException e) {
                e.printStackTrace();
            }

//
//            try {
//
//                JSONArray jArray = new JSONArray(result);
//
//                // Extract data from json and store into ArrayList as class objects
////                for(int i=0;i<jArray.length();i++) {
//
//                    JSONObject json_data = jObj;
////                    cities.setId(json_data.getString("id"));
////                    cities.setName(json_data.getString("name"));
////                    cities.setCountry(json_data.getString("country"));
//
//
//
//                    cityName.setText(json_data.getString("name"));
//                    cityTemp.setText(json_data.getString("temp"));
//                    cityWeather.setText(json_data.getString("description"));
////                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

            // We start extracting the info
//            Location loc = new Location();
//
//            JSONObject coordObj = getObject("coord", jObj);
//            loc.setLatitude(getFloat("lat", coordObj));
//            loc.setLongitude(getFloat("lon", coordObj));
//
//            JSONObject sysObj = getObject("sys", jObj);
//            loc.setCountry(getString("country", sysObj));
//            loc.setSunrise(getInt("sunrise", sysObj));
//            loc.setSunset(getInt("sunset", sysObj));
//            loc.setCity(getString("name", jObj));
//            weather.location = loc;
//
//            // We get weather info (This is an array)
//            JSONArray jArr = jObj.getJSONArray("weather");
//
//            // We use only the first value
//            JSONObject JSONWeather = jArr.getJSONObject(0);
//            weather.currentCondition.setWeatherId(getInt("id", JSONWeather));
//            weather.currentCondition.setDescr(getString("description", JSONWeather));
//            weather.currentCondition.setCondition(getString("main", JSONWeather));
//            weather.currentCondition.setIcon(getString("icon", JSONWeather));
//
//            JSONObject mainObj = getObject("main", jObj);
//            weather.currentCondition.setHumidity(getInt("humidity", mainObj));
//            weather.currentCondition.setPressure(getInt("pressure", mainObj));
//            weather.temperature.setMaxTemp(getFloat("temp_max", mainObj));
//            weather.temperature.setMinTemp(getFloat("temp_min", mainObj));
//            weather.temperature.setTemp(getFloat("temp", mainObj));
//
//            // Wind
//            JSONObject wObj = getObject("wind", jObj);
//            weather.wind.setSpeed(getFloat("speed", wObj));
//            weather.wind.setDeg(getFloat("deg", wObj));
//
//            // Clouds
//            JSONObject cObj = getObject("clouds", jObj);
//            weather.clouds.setPerc(getInt("all", cObj));
//
//            cityText.setText(weather.location.getCity() + "," + weather.location.getCountry());
//            condDescr.setText(weather.currentCondition.getCondition() + "(" + weather.currentCondition.getDescr() + ")");
//            temp.setText("" + Math.round((weather.temperature.getTemp() - 273.15)) + "�C");
//            hum.setText("" + weather.currentCondition.getHumidity() + "%");
//            press.setText("" + weather.currentCondition.getPressure() + " hPa");
//            windSpeed.setText("" + weather.wind.getSpeed() + " mps");
//            windDeg.setText("" + weather.wind.getDeg() + "�");

        }
    }

    private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static float  getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static int  getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }

    private void updateWeatherValue(City city){
        cityHelper.open();
        cityHelper.update(city);
        cityHelper.close();
    }

    public void setWeatherColor(String weather){
        int color;
        switch (weather){
            case "Clear":
                color = Color.parseColor("#FFEB3B");
                break;
            case "Rain":
                color = Color.parseColor("#1565C0");
                break;
            case "Clouds":
                color = Color.parseColor("#4DB6AC");
                break;
            default:
                color = Color.parseColor("#000000");
        }

        setActivityBackgroundColor(color);
    }

    public void setActivityBackgroundColor(int color) {
        View view = getWindow().getDecorView();
        layout.setBackgroundColor(color);
        view.setBackgroundColor(color);

    }
}


