package com.example.rsp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
 EditText txtEmail,txtPassword;
 Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");
        txtEmail=(EditText)findViewById(R.id.txt_email);
        txtPassword=(EditText)findViewById(R.id.txt_password);
        btn_login= (Button) findViewById(R.id.buttonLogin);
   btn_login.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           String email=txtEmail.getText().toString().trim();
           String password=txtPassword.getText().toString().trim();
           if(TextUtils.isEmpty(email)){
               Toast.makeText(Login.this,"Please enter Email",Toast.LENGTH_SHORT);
               return;
           }
           if(TextUtils.isEmpty(password)){
               Toast.makeText(Login.this,"Please enter Password",Toast.LENGTH_SHORT);
               return;

           }
       }
   });



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


