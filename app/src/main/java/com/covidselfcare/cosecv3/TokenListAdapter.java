package com.covidselfcare.cosecv3;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TokenListAdapter extends ArrayAdapter<String> {
    private LayoutInflater mLayoutInflater;
    private ArrayList<String> mTokens;
    private int  mViewResourceId;

    public TokenListAdapter(Context context, int tvResourceId, ArrayList<String> tokens){
        super(context, tvResourceId,tokens);
        this.mTokens = tokens;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = tvResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mLayoutInflater.inflate(mViewResourceId, null);

        String s = mTokens.get(position);

        if (!s.isEmpty() ) {
            TextView tokenName = (TextView) convertView.findViewById(R.id.tokenName);


            if (tokenName != null) {
                tokenName.setText(s);
            }

        }

        return convertView;
    }
}
