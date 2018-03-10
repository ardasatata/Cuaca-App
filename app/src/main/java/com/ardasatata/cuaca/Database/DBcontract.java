package com.ardasatata.cuaca.Database;

/**
 * Created by ardasatata on 3/10/18.
 */

import android.provider.BaseColumns;

public class DBcontract {

    static String TABLE_CITY = "table_city";

    static final class CityColumns implements BaseColumns {


        static String ID_CITY = "id_city";

        static String NAME = "name";

        static String TEMP = "temp";

        static String WEATHER = "weather";

    }

}
