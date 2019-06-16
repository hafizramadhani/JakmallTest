package com.example.jakmalltestproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FetchData extends AsyncTask<Void, Void, String> {

    String urlString = "https://api.icndb.com/jokes/random/10-";
    String rawData;
    Context context;
    ProgressDialog dialog;

    public FetchData(Context pContext) {
        context = pContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);

        dialog.setMessage("Processing ...");
        dialog.show();
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line!=null){
                line = bufferedReader.readLine();
                rawData = rawData + line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rawData;
    }

    @Override
    protected void onPostExecute(String pString) {
        super.onPostExecute(pString);
        dialog.dismiss();
    }
}
