package com.ardasatata.cuaca.Database;

/**
 * Created by ardasatata on 3/10/18.
 */

public class City {

    private int id;
    private int id_city;
    private String name;
    private int temp;
    private String weather;

    public City(){
    }

    public City(int id_city, String name, int temp, String weather) {
        this.id_city = id_city;
        this.name = name;
        this.temp = temp;
        this.weather = weather;
    }

    public City(int id, int id_city, String name, int temp, String weather) {
        this.id = id;
        this.id_city = id_city;
        this.name = name;
        this.temp = temp;
        this.weather = weather;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_city() {
        return id_city;
    }

    public void setId_city(int id_city) {
        this.id_city = id_city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}
