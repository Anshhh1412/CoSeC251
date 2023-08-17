package com.covidselfcare.cosecv3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    private QuestionLibrary mQuestionLib = new QuestionLibrary();
    private DatabaseHelper myDb;
    private TextView mQuestionView;
    private Button mButtonChoice1;
    private Button mButtonChoice2;

    private String mAnswer;
    private int mScore =0;
    private int mQuestionNumber = 0;
    int final_count=0;
    String[] ans = new String[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        myDb = new DatabaseHelper(this);
        mQuestionView = (TextView) findViewById(R.id.question);
        mButtonChoice1 = (Button) findViewById(R.id.choice1);
        mButtonChoice2 = (Button) findViewById(R.id.choice2);

        mQuestionView.setText(mQuestionLib.getQuestion(mQuestionNumber));
        mAnswer = QuestionLibrary.answers[mQuestionNumber];

        mButtonChoice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = getIntent().getExtras();
                String loggedInUserq = bundle.getString("quname");
                if (mAnswer.equals("Yes")){
                    mScore++;
                    ans[final_count]="Yes";
                    final_count++;
                    if(final_count==3){final_count=0;}
                    if(mQuestionNumber == QuestionLibrary.questions.length-1)
                    {
                        String s = new String(" ");
                        if(mScore>0){s = "AT_RISK";}
                        else {s = "HEALTHY";}
                        myDb.updateRisk(loggedInUserq,s,ans[0],ans[1],ans[2]);

                        QuizActivity.this.finish();

                    }
                    else{
                        updateQuestion();
                    }
                }
                else{
                    if(mQuestionNumber == QuestionLibrary.questions.length-1)
                    {
                        String s = new String(" ");
                        if(mScore>0){s = "AT_RISK";}
                        else {s = "HEALTHY";}
                        myDb.updateRisk(loggedInUserq,s,ans[0],ans[1],ans[2]);

                        QuizActivity.this.finish();
                    }
                    else{
                        updateQuestion();
                    }

                }

            }
        });
        mButtonChoice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getIntent().getExtras();
                String loggedInUserq = bundle.getString("quname");

                ans[final_count]="No";
                final_count++;
                if(final_count==3){final_count=0;}
                if(mQuestionNumber == QuestionLibrary.questions.length-1)
                {
                    String s = new String(" ");
                    if(mScore>0){s = "AT_RISK";}
                    else {s = "HEALTHY";}
                    myDb.updateRisk(loggedInUserq,s,ans[0],ans[1],ans[2]);
                    QuizActivity.this.finish();

                }
                else{
                    updateQuestion();
                }
            }
        });
    }
    private void updateQuestion()
    {
        mQuestionNumber = mQuestionNumber + 1;
        mQuestionView.setText(mQuestionLib.getQuestion(mQuestionNumber));
        mAnswer = QuestionLibrary.answers[mQuestionNumber];

    }



}