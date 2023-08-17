package com.covidselfcare.cosecv3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {
    private EditText loginUsername , loginPassword;
    private Button loginButton;
    private DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginUsername = findViewById(R.id.loginusername);
        loginPassword = findViewById(R.id.loginpassword);
        loginButton = findViewById(R.id.loginbutton);
        myDb = new DatabaseHelper(this);

        loginUser();
    }
    private void loginUser(){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = loginUsername.getText().toString();
                String b = loginPassword.getText().toString();

                boolean var = myDb.checkUser(loginUsername.getText().toString() , loginPassword.getText().toString());
                if(a.isEmpty() || b.isEmpty() || !var)
                {
                    Toast.makeText(LoginActivity.this, "Login Failed !!", Toast.LENGTH_SHORT).show();

                }
                else {

                    Toast.makeText(LoginActivity.this, "Login Successful!!", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(LoginActivity.this,ProfileActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("uname",a);
                    i.putExtras(bundle);
                    LoginActivity.this.finish();
                    startActivity(i);
                }
            }
        });
    }


}