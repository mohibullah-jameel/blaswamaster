package com.example.rsp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("login");


    }

    public void btn_Registerhere(View view) {
        startActivity(new Intent(getApplicationContext(), Signup.class));
    }
    public void btn_Forgot_Password(View view) {
        startActivity(new Intent(getApplicationContext(),Forgetpassword.class));
    }


    public void btn_Login(View view) {
        startActivity(new Intent(getApplicationContext(),navigationdrawer.class));
    }

}


