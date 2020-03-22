package com.example.rsp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");


    }

    public void btn_Registerhere(View view) {
        startActivity(new Intent(getApplicationContext(), Signup.class));
    }
    public void btn_Forgot_Password(View view) {
        startActivity(new Intent(getApplicationContext(), ForgetPassword.class));
    }


    public void btn_Login(View view) {
        startActivity(new Intent(getApplicationContext(), NavigationDrawer.class));
    }

}


