package com.example.rsp.ui;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rsp.ForgetPassword;
import com.example.rsp.HomePage;
import com.example.rsp.NavigationDrawer;
import com.example.rsp.R;
import com.example.rsp.Signup;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText txtEmail, txtPassword;
    Button btn_login;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progress;
    private TextView ForgetPassword;
    private TextView Register;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtEmail = (EditText) findViewById(R.id.txt_email);
        txtPassword = (EditText) findViewById(R.id.txt_password);
        btn_login = (Button) findViewById(R.id.btnlogin);
        ForgetPassword = (TextView) findViewById(R.id.forgetpassword);
        Register = (TextView) findViewById(R.id.txt_registerhere);
        firebaseAuth = FirebaseAuth.getInstance();
        progress = new ProgressDialog(this);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Signup.class));
            }
        });
        ForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, ForgetPassword.class));
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Login.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Login.this, "Please Enter password", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (!email.matches(emailPattern))
                {
                    Toast.makeText(Login.this, "Invalid Email ", Toast.LENGTH_SHORT).show();
                    return;
                }
                progress.setMessage("PLease wait for a sec....");
                progress.setTitle("Checking Details");
                progress.show();

                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(getApplicationContext(),NavigationDrawer.class));
                                    progress.dismiss();

                                } else {
                                    Toast.makeText(Login.this, "USer not available", Toast.LENGTH_SHORT).show();
                                    progress.dismiss();
                                }
                                // ...
                            }
                        });
            }
        });
   }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser!= null)
        {
            Intent intent = new Intent(Login.this , NavigationDrawer.class);
            startActivity(intent);
        }
    }
}