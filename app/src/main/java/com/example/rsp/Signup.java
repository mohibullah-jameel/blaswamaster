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

import com.example.rsp.ui.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    EditText txtfullname,txtlastname,txtEmail,txtPassword ,txtconfirmpassword;
    Button btn_register;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().setTitle("Signup");
        txtfullname=(EditText)findViewById(R.id.txt_fname);
        txtlastname=(EditText)findViewById(R.id.txt_lname);
        txtEmail=(EditText)findViewById(R.id.txt_email);
        txtPassword=(EditText)findViewById(R.id.txt_password);
        txtconfirmpassword=(EditText)findViewById(R.id.txt_cpassword);
        btn_register= (Button) findViewById(R.id.buttonregister);
        databaseReference= FirebaseDatabase.getInstance().getReference("User");
        firebaseAuth=FirebaseAuth.getInstance();
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fullname=txtfullname.getText().toString().trim();
                final String lastname=txtlastname.getText().toString().trim();
                final String email=txtEmail.getText().toString().trim();
                String password=txtPassword.getText().toString().trim();
                String confirmpassword=txtconfirmpassword.getText().toString().trim();
                if(TextUtils.isEmpty(fullname)){
                    Toast.makeText(Signup.this,"Please Enter Full Name",Toast.LENGTH_SHORT);
                    return; }
                if(TextUtils.isEmpty(lastname)){
                    Toast.makeText(Signup.this,"Please Enter Last Name",Toast.LENGTH_SHORT);
                    return; }
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Signup.this,"Please Enter Email",Toast.LENGTH_SHORT);
                    return; }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Signup.this,"Please Enter Password",Toast.LENGTH_SHORT);
                    return; }
                if(TextUtils.isEmpty(confirmpassword)){
                    Toast.makeText(Signup.this,"Please Enter  Confirm Password",Toast.LENGTH_SHORT);
                    return;
                }
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    User information= new User(
                                            fullname,
                                            lastname,
                                            email
                                    );
                                    FirebaseDatabase.getInstance().getReference("User")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(Signup.this,"Please Enter Full Name",Toast.LENGTH_SHORT);
                                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                                        }
                                    });

                                } else {
                                    //
                                }

                            }
                        });












                if (password.length()<6){
                    Toast.makeText(Signup.this,"Password too short",Toast.LENGTH_SHORT);

                }
            }
        });
    }

    public void btn_login(View view) {
        startActivity(new Intent(getApplicationContext(), Login.class));
    }
}


