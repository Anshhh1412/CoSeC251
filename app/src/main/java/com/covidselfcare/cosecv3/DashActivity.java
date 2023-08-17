package com.covidselfcare.cosecv3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class DashActivity extends AppCompatActivity {
    private TextView country,cases,tcases,deaths,tdeaths,cpm,dpm,tpm,act,rec,tt,crit;
    private RecyclerView recyclerView;
    private List<State> stateList;
    private Button RTQbtn;
    private Button locbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        country = findViewById(R.id.country);
        cases = findViewById(R.id.cases);
        tcases = findViewById(R.id.tcases);
        deaths = findViewById(R.id.deaths);
        tdeaths = findViewById(R.id.tdeaths);
        cpm = findViewById(R.id.cpm);
        dpm = findViewById(R.id.dpm);
        tpm = findViewById(R.id.tpm);
        act = findViewById(R.id.active);
        rec = findViewById(R.id.rec);
        crit = findViewById(R.id.critical);
        tt  = findViewById(R.id.tt);
        recyclerView = findViewById(R.id.Rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        stateList = new ArrayList<>();
        RTQbtn = findViewById(R.id.repToQues);
        locbtn = findViewById(R.id.locatebtn);
        yourDataTask task = new yourDataTask();
        task.execute();

        RTQbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundl = getIntent().getExtras();
                String loggedInUser = bundl.getString("name");

                Intent i = new Intent(DashActivity.this,RepliesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Name",loggedInUser);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        locbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashActivity.this, LocationActivity.class));
            }
        });

    }

    public class yourDataTask extends AsyncTask<Void, Void, JSONArray> {


        @Override
        public JSONArray doInBackground(Void... params) {

            String str1 = "https://coronavirus-19-api.herokuapp.com/countries/india";
            String str2 = "https://api.apify.com/v2/key-value-stores/toDWvRj1JpTXiM8FF/records/LATEST?disableRedirect=true";
            URLConnection urlConn = null, urlConn2 = null;
            BufferedReader bufferedReader = null,bufferedReader2 = null;
            try {
                URL url1 = new URL(str1);
                URL url2 = new URL(str2);
                urlConn = url1.openConnection();
                urlConn2 = url2.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                bufferedReader2 = new BufferedReader(new InputStreamReader(urlConn2.getInputStream()));

                StringBuffer stringBuffer = new StringBuffer();
                StringBuffer stringBuffer2 = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }
                while ((line = bufferedReader2.readLine()) != null) {
                    stringBuffer2.append(line);
                }
                JSONObject J1 = new JSONObject(stringBuffer.toString());
                JSONObject J2 = new JSONObject(stringBuffer2.toString());
                JSONArray jsonArray = new JSONArray();

                jsonArray.put(J1);
                jsonArray.put(J2);
                return  jsonArray;
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
        protected void onPostExecute(JSONArray response) {
            if (response != null) {
                try {


                    JSONObject oneObject = response.getJSONObject(0);

                    country.setText(oneObject.getString("country"));
                    cases.setText(oneObject.getString("cases"));
                    tcases.setText(oneObject.getString("todayCases"));
                    deaths.setText(oneObject.getString("deaths"));
                    tdeaths.setText(oneObject.getString("todayDeaths"));
                    act.setText(oneObject.getString("active"));
                    rec.setText(oneObject.getString("recovered"));
                    crit.setText(oneObject.getString("critical"));
                    cpm.setText(oneObject.getString("casesPerOneMillion"));
                    dpm.setText(oneObject.getString("deathsPerOneMillion"));
                    tt.setText(oneObject.getString("totalTests"));
                    tpm.setText(oneObject.getString("testsPerOneMillion"));

                    JSONObject J = response.getJSONObject(1);

                    JSONArray JA = J.getJSONArray("regionData");

                    for(int i = 0; i < JA.length(); i++)
                    {
                        JSONObject JO = JA.getJSONObject(i);
                        String region = JO.getString("region");
                        String activeCases = JO.getString("activeCases");
                        String totalInfected = JO.getString("totalInfected");
                        String recovered = JO.getString("recovered");
                        String newD = JO.getString("newDeceased");

                        State state = new State(region,activeCases,totalInfected,recovered,newD);
                        stateList.add(state);
                    }

                    Log.e("App", "Success: " + oneObject.getString("country"));


                } catch (JSONException ex) {
                    Log.e("App", "Failure", ex);
                }

                StateAdapter adapter = new StateAdapter(DashActivity.this,stateList);
                recyclerView.setAdapter(adapter);
            }
        }

    }
}