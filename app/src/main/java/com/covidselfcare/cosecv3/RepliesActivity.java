package com.covidselfcare.cosecv3;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class RepliesActivity extends AppCompatActivity {
    private TextView q1,q2,q3,a1,a2,a3;
    private QuestionLibrary mQuestionLib = new QuestionLibrary();
    private DatabaseHelper mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replies);

        q1 = findViewById(R.id.q1);
        q2 = findViewById(R.id.q2);
        q3 = findViewById(R.id.q3);
        a1 = findViewById(R.id.a1);
        a2 = findViewById(R.id.a2);
        a3 = findViewById(R.id.a3);
        mydb = new DatabaseHelper(this);

        q1.setText(mQuestionLib.getQuestion(0));
        q2.setText(mQuestionLib.getQuestion(1));
        q3.setText(mQuestionLib.getQuestion(2));

        Bundle bundle1 = getIntent().getExtras();
        String loggedInUser = bundle1.getString("Name");

        Cursor res = mydb.getAllData();
        if(res.getCount() == 0)
        {
            return;
        }
        while (res.moveToNext()) {
            if(loggedInUser.equals(res.getString(1))) {
                a1.setText(res.getString(6));
                a2.setText(res.getString(7));
                a3.setText(res.getString(8));

            }
        }


    }
}