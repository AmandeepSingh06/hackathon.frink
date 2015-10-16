package com.frink.hackathon.task;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by shashwatsinha on 16/10/15.
 */
public class JsonTask extends AsyncTask<String, Void, InputStream> {

    @Override
    protected InputStream doInBackground(String... parmas) {

        InputStream is = null;
        try {
            URL url = new URL(parmas[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            is = conn.getInputStream();


        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return is;
    }

}
