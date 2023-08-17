package com.covidselfcare.cosecv3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    private TextView name,age,gender,risk;
    DatabaseHelper mydb;
    private Button QuestionnaireBtn;
    private Button Dashbtn,BLbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.profilename);
        age = findViewById(R.id.profileage);
        gender = findViewById(R.id.profilegender);
        risk = findViewById(R.id.profilerisk);
        mydb = new DatabaseHelper(this);
        QuestionnaireBtn = findViewById(R.id.ques);
        Dashbtn = findViewById(R.id.dash);
        BLbtn = findViewById(R.id.BL);
        Bundle bundle = getIntent().getExtras();
        String loggedInUser = bundle.getString("uname");

        Cursor res = mydb.getAllData();
        if(res.getCount() == 0)
        {
            return;
        }
        while (res.moveToNext()) {
            if(loggedInUser.equals(res.getString(1))) {
                name.setText(res.getString(1));
                age.setText(res.getString(3));
                gender.setText(res.getString(2));
                risk.setText(res.getString(5));
            }
        }
        QuestionnaireBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this,QuizActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("quname",loggedInUser);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        Dashbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(ProfileActivity.this,DashActivity.class);
                Bundle bundl = new Bundle();
                bundl.putString("name",loggedInUser);
                j.putExtras(bundl);
                startActivity(j);
            }
        });
        BLbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(ProfileActivity.this,BluetoothActivity.class);
                Bundle bund = new Bundle();
                bund.putString("blname",loggedInUser);
                k.putExtras(bund);
                startActivity(k);
            }
        });

    }
}