package com.covidselfcare.cosecv3;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class yourDataTask extends AsyncTask<Void, Void, JSONObject> {
    @Override
    public JSONObject doInBackground(Void... params) {

        String str1 = "https://coronavirus-19-api.herokuapp.com/countries/india";
        String str2 = "https://api.apify.com/v2/key-value-stores/toDWvRj1JpTXiM8FF/records/LATEST?disableRedirect=true";
        URLConnection urlConn = null;
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(str1);
            urlConn = url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }

            return new JSONObject(stringBuffer.toString());
        } catch (Exception ex) {
            Log.e("App", "yourDataTask", ex);
            return null;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onPostExecute(JSONObject response) {
        if (response != null) {
            try {
                Log.e("App", "Success: " + response.getString("country"));
            } catch (JSONException ex) {
                Log.e("App", "Failure", ex);
            }
        }
    }
}
 /*String result = null;
        try {
            URL url = new URL("https://coronavirus-19-api.herokuapp.com/countries/india");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream());
                BufferedReader reader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();
                String temp;

                while ((temp = reader.readLine()) != null) {
                    stringBuilder.append(temp);
                }
                result = stringBuilder.toString();
            }else  {
                result = "error";
            }

        } catch (Exception  e) {
            e.printStackTrace();
        }

        try {
            JSONObject JSON = new JSONObject(r


                    esult);

            country.setText(JSON.getString("country"));
            cases.setText(JSON.getString("cases"));
            tcases.setText(JSON.getString("todayCases"));
            deaths.setText(JSON.getString("deaths"));
            tdeaths.setText(JSON.getString("todayDeaths"));
            act.setText(JSON.getString("active"));
            rec.setText(JSON.getString("recovered"));
            crit.setText(JSON.getString("critical"));
            cpm.setText(JSON.getString("casesPerOneMillion"));
            dpm.setText(JSON.getString("deathPerOneMillion"));
            tt.setText(JSON.getString("totalTests"));
            tpm.setText(JSON.getString("testsPerOneMillion"));
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
