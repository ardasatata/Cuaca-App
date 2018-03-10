package com.ardasatata.cuaca.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.ardasatata.cuaca.Database.DBcontract.CityColumns.ID_CITY;
import static com.ardasatata.cuaca.Database.DBcontract.CityColumns.NAME;
import static com.ardasatata.cuaca.Database.DBcontract.CityColumns.TEMP;
import static com.ardasatata.cuaca.Database.DBcontract.CityColumns.WEATHER;
import static com.ardasatata.cuaca.Database.DBcontract.TABLE_CITY;

/**
 * Created by ardasatata on 3/10/18.
 */

public class DBhelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    public static String CREATE_TABLE_CITY = "create table " + TABLE_CITY +
            " (" + _ID + " integer primary key autoincrement, " +
            ID_CITY + " integer not null, " +
            NAME + " text not null, " +
            TEMP + " integer not null, " +
            WEATHER + " text not null);";

    private static String DATABASE_NAME = "dbCityList";

    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CITY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITY);

        onCreate(db);
    }
}
