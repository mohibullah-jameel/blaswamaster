package com.example.rsp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
 EditText txtEmail,txtPassword;
 Button btn_login;
 private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");
        txtEmail=(EditText)findViewById(R.id.txt_email);
        txtPassword=(EditText)findViewById(R.id.txt_password);
        btn_login= (Button) findViewById(R.id.buttonLogin);
        firebaseAuth=FirebaseAuth.getInstance();
   btn_login.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           String email = txtEmail.getText().toString().trim();
           String password = txtPassword.getText().toString().trim();
           if (TextUtils.isEmpty(email)) {
               Toast.makeText(Login.this, "Please enter Email", Toast.LENGTH_SHORT);
               return;
           }
           if (TextUtils.isEmpty(password)) {
               Toast.makeText(Login.this, "Please enter Password", Toast.LENGTH_SHORT);
               return;

           }

           firebaseAuth.createUserWithEmailAndPassword(email, password)
                   .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()) {
                               // Sign in success, update UI with the signed-in user's information
                               startActivity(new Intent(getApplicationContext(), HomePage.class));

                           } else {
                               Toast.makeText(Login.this, "Login Failed or User not Available", Toast.LENGTH_SHORT);
                           }

                           // ...
                       }
                   });




       public void btn_Registerhere(View view) {
           startActivity(new Intent(getApplicationContext(), Signup.class));
       }

       public void btn_Forgot_Password(View view) {
           startActivity(new Intent(getApplicationContext(), ForgetPassword.class));
       }


       public void btn_Login(View view) {
           startActivity(new Intent(getApplicationContext(), NavigationDrawer.class));
       }


