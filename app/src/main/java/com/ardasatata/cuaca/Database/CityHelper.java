package com.ardasatata.cuaca.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.ardasatata.cuaca.Database.DBcontract.CityColumns.ID_CITY;
import static com.ardasatata.cuaca.Database.DBcontract.CityColumns.NAME;
import static com.ardasatata.cuaca.Database.DBcontract.CityColumns.TEMP;
import static com.ardasatata.cuaca.Database.DBcontract.CityColumns.WEATHER;
import static com.ardasatata.cuaca.Database.DBcontract.TABLE_CITY;

/**
 * Created by ardasatata on 3/10/18.
 */

public class CityHelper {

    private Context context;
    private DBhelper dataBaseHelper;
    private SQLiteDatabase database;

    public CityHelper(Context context) {
        this.context = context;
    }

    public CityHelper open() throws SQLException {
        dataBaseHelper = new DBhelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dataBaseHelper.close();
    }

    public ArrayList<City> getAllData() {
        Cursor cursor = database.query(TABLE_CITY, null, null, null, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<City> arrayList = new ArrayList<>();
        City city;
        if (cursor.getCount() > 0) {
            do {
                city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                city.setId_city(cursor.getInt(cursor.getColumnIndexOrThrow(ID_CITY)));
                city.setName(cursor.getString(cursor.getColumnIndexOrThrow(NAME)));
                city.setTemp(cursor.getInt(cursor.getColumnIndexOrThrow(TEMP)));
                city.setWeather(cursor.getString(cursor.getColumnIndexOrThrow(WEATHER)));
                arrayList.add(city);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(City city) {

        database.beginTransaction();
        try {
            ContentValues initialValues = new ContentValues();
            initialValues.put(ID_CITY, city.getId_city());
            initialValues.put(NAME, city.getName());
            initialValues.put(TEMP, city.getTemp());
            initialValues.put(WEATHER, city.getWeather());
            return database.insert(TABLE_CITY, null, initialValues);
        }
        finally {
            database.setTransactionSuccessful();
            database.endTransaction();
            System.out.println("city inserted");
        }
    }

    public long update(City city) {

        database.beginTransaction();
        try {
            ContentValues args = new ContentValues();
            args.put(ID_CITY, city.getId_city());
            args.put(NAME, city.getName());
            args.put(TEMP, city.getTemp());
            args.put(WEATHER, city.getWeather());
            return database.update(TABLE_CITY, args, _ID + "= '" + city.getId() + "'", null);
        }
        finally {
            database.setTransactionSuccessful();
            database.endTransaction();
        }
    }

    public int delete(int id) {

        database.beginTransaction();
        try {
            return database.delete(TABLE_CITY, _ID + " = '" + id + "'", null);
        }
        finally {
            database.setTransactionSuccessful();
            database.endTransaction();
        }
    }
}
