package com.covidselfcare.cosecv3;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothDevice;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.Cartesian;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.Pie;
import com.anychart.anychart.ValueDataEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactActivity extends AppCompatActivity {
    public ArrayList<String> mTokens = new ArrayList<>();
    public TokenListAdapter mTokenListAdapter;
    ListView lvNewTokens;
    private DatabaseHelper mydb;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        mydb = new DatabaseHelper(this);
        lvNewTokens = findViewById(R.id.tokenList);
        AnyChartView anyChartView = findViewById(R.id.any_chart_view1);
        AnyChartView anyChartView2 = findViewById(R.id.any_chart_view2);
        Cartesian cartesian = AnyChart.column();
        Cartesian cartesian2 = AnyChart.column();
        List<DataEntry> data = new ArrayList<>();
        List<DataEntry> data2 = new ArrayList<>();

        Bundle bund = getIntent().getExtras();
        String loggedInUser = bund.getString("uname");
        Cursor res = mydb.getAllData();
        if(res.getCount() == 0)
        {
            return;
        }
        String tok = new String();
//        String t = new String();
          String d = new String();
        while (res.moveToNext()) {
            if(loggedInUser.equals(res.getString(1))) {
                tok = res.getString(9);
//                t = res.getString(10);
                d = res.getString(11);
                break;
            }
        }
        String[] newStr = tok.split("\\s+");    // newStr2
        String[] newStr1 = d.split("\\s+");
        // for graph 1
        Arrays.sort(newStr1);
        int j =0,r = newStr1.length-1;

        for(int i=0;i<r;i++)
        {
            if(!newStr1[i].equals(newStr1[i+1]) )
            {
                data.add(new ValueDataEntry(newStr1[i], i-j+1));
                j=i;
            }
        }
        if(!newStr1[r].equals(newStr1[r-1])){

            data.add(new ValueDataEntry(newStr1[r], 1));

        }
        else{
            data.add(new ValueDataEntry(newStr1[r], r+1-j));
        }

        // for adapter
        mTokens.add(newStr[0]);
        for(int i = 1; i < newStr.length; i++)
        {

            if(!mTokens.contains(newStr[i]))
                {mTokens.add(newStr[i]);}
        }
        // for graph
        int counter=0;
        HashMap<String, Integer> freqMap = new HashMap<String, Integer>();
        for (int i = 0; i < newStr.length; i++) {
            String key = newStr[i];
            int freq = freqMap.getOrDefault(key, 0);
            freqMap.put(key, ++freq);
        }
        int x = 1;
        for (String result : mTokens){
            data2.add(new ValueDataEntry(x, freqMap.get(result)));
            x++;
        }


        cartesian.setData(data);
        cartesian.setTitle("Token vs Date");
        cartesian.setYScale("0d");
        cartesian.setXAxis("Date");
        cartesian.setYAxis("No. of tokens");
        anyChartView.setChart(cartesian);

        cartesian2.setData(data2);
        cartesian2.setTitle("Token vs DeviceID");
        cartesian2.setYScale("0d");
        cartesian2.setXAxis("Tokens");
        cartesian2.setYAxis("Repeats");
        anyChartView2.setChart(cartesian2);

        
        mTokenListAdapter = new TokenListAdapter(ContactActivity.this, R.layout.tokens_view, mTokens);
        lvNewTokens.setAdapter(mTokenListAdapter);


    }
}