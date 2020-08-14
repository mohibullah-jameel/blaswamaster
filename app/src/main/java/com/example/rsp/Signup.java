package com.example.rsp;

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

import com.example.rsp.ui.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
//    initialization of username full name.email,password
    EditText txtfullname,txtusername,txtEmail,txtPassword ,txtconfirmpassword;
    Button btn_register;
    private TextView Login;
//    declaration of firebase
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//    show working of dialog
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        progress=new ProgressDialog(this);
//        find the id by username id
        txtfullname=(EditText)findViewById(R.id.txt_fname);
//        find the full name  id by user
        txtusername=(EditText)findViewById(R.id.txt_uname);
//        find the email of user
        txtEmail=(EditText)findViewById(R.id.txt_email);
//        find the password by user
        txtPassword=(EditText)findViewById(R.id.txt_password);
//        find the confirm password
        txtconfirmpassword=(EditText)findViewById(R.id.txt_cpassword);
//        find the registration button
        btn_register= (Button) findViewById(R.id.buttonregister);
//        find the login button
        Login = (TextView) findViewById(R.id.txt_login);
//        user register then move to the next activity
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signup.this, Login.class));
            }
        });

        databaseReference= FirebaseDatabase.getInstance().getReference("User");
        firebaseAuth=FirebaseAuth.getInstance();
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fullname=txtfullname.getText().toString().trim();
                final String lastname=txtusername.getText().toString().trim();
                final String email=txtEmail.getText().toString().trim();
                String password=txtPassword.getText().toString().trim();
                String confirmpassword=txtconfirmpassword.getText().toString().trim();
                if(TextUtils.isEmpty(fullname)){
                    Toast.makeText(Signup.this, "Please Enter First name", Toast.LENGTH_SHORT).show();
                    return; }
                if(TextUtils.isEmpty(lastname)){
                    Toast.makeText(Signup.this, "Please Enter last name", Toast.LENGTH_SHORT).show();
                    return; }
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Signup.this, "Please Enter Email ", Toast.LENGTH_SHORT).show();
                    return; }
                if (!email.matches(emailPattern))
                {
                    Toast.makeText(Signup.this, "Invalid Email ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Signup.this, "Please Enter password", Toast.LENGTH_SHORT).show();
                    return; }
                if(TextUtils.isEmpty(confirmpassword)){
                    Toast.makeText(Signup.this, "Please Enter confrim password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length()<6){
                    Toast.makeText(Signup.this, "Too short", Toast.LENGTH_SHORT).show();

                }
                progress.setTitle("Saving Information");
                progress.setMessage("Please wait for a second...");
                progress.setTitle("Checking Details");
                progress.show();
                firebaseAuth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });
                            // Sign in success, update UI with the signed-in user's information
                            progress.dismiss();
                            User information= new User(
                                    fullname,
                                    email
                            );
                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(Signup.this, "added sucessfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),NavigationDrawer.class));
                                    progress.dismiss();

                                }
                            });

                        }
                        else
                        {

                            Toast.makeText(Signup.this , "Error", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }

    public void btn_login(View view) {
        startActivity(new Intent(getApplicationContext(), Login.class));
    }
}


