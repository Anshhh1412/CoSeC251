package com.covidselfcare.cosecv3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    private EditText name,age,gender,password;
    private Button Signup;
    private DatabaseHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_activity);

        name = findViewById(R.id.signupusername);
        age = findViewById(R.id.signupage);
        gender = findViewById(R.id.signupgender);
        password = findViewById(R.id.signuppassword);
        Signup = findViewById(R.id.signupbutton);
        myDB = new DatabaseHelper(this);

        insertUser();
    }

    private void insertUser(){
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                boolean var = myDB.registerUser(name.getText().toString(),gender.getText().toString(),age.getText().toString(),password.getText().toString());

                if(var){
                    Toast.makeText(SignUpActivity.this, "User Registered Successfully !!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this , LoginActivity.class));
                    finish();

                }
                else
                    Toast.makeText(SignUpActivity.this, "SignUp Error !!", Toast.LENGTH_SHORT).show();

            }


        });
    }

}