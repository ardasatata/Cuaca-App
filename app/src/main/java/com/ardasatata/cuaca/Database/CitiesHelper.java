package com.ardasatata.cuaca.Database;

import android.content.Context;

import com.ardasatata.cuaca.Cities;
import com.ardasatata.cuaca.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ardasatata on 3/10/18.
 */

public class CitiesHelper {
    private Context context;
    private String list;

    public CitiesHelper(Context context) {
        this.context = context;
    }

    public ArrayList<Cities> getAllData() throws IOException {
        readJSON();
        Gson gson = new Gson();
        Cities cities[] = gson.fromJson(list,Cities[].class);

        ArrayList<Cities> arrayList = new ArrayList<>(Arrays.asList(cities));
        return arrayList;
    }

    public void readJSON() throws IOException {
        InputStream is = context.getResources().openRawResource(R.raw.city);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            is.close();
        }

        this.list = writer.toString();
    }
}
