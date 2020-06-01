package com.example.rsp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rsp.ui.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AdPost extends AppCompatActivity {
    EditText txtTitle,txtDescription,txtOwnername,txtOwneraddress,txtMobilenumber;
    Button btn_submit;
    private Button Submit;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_post);
        txtTitle = (EditText) findViewById(R.id.txt_title);
        txtDescription = (EditText) findViewById(R.id.txt_description);
        txtOwnername = (EditText) findViewById(R.id.txt_ownername);
        txtOwneraddress=(EditText) findViewById(R.id.txt_owneraddress);
        txtMobilenumber=(EditText) findViewById(R.id.txt_mobileno);
        Submit = (Button) findViewById(R.id.btnsubmit);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdPost.this, NavigationDrawer.class));
            }
        });
        databaseReference= FirebaseDatabase.getInstance().getReference("Post");
        firebaseAuth=FirebaseAuth.getInstance();
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Title = txtTitle.getText().toString().trim();
                String Description = txtDescription.getText().toString().trim();
                String Ownername = txtOwnername.getText().toString().trim();
                String Owneraddress = txtOwneraddress.getText().toString().trim();
                String Mobilenumber = txtMobilenumber.getText().toString().trim();

                if (TextUtils.isEmpty(Title)) {
                    Toast.makeText(AdPost.this, "Please Enter the title", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(Description)) {
                    Toast.makeText(AdPost.this, "Please Enter description ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(Ownername)) {
                    Toast.makeText(AdPost.this, "Please Enter ownername", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(Owneraddress)) {
                    Toast.makeText(AdPost.this, "Please Enter owneraddress", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(Mobilenumber)) {
                    Toast.makeText(AdPost.this, "Please Enter mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }

                Post information = new Post(
                        Title,
                        Description,
                        Ownername,
                        Owneraddress,
                        Mobilenumber

                );

                FirebaseDatabase.getInstance().getReference("Post")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(AdPost.this, "added sucessfully", Toast.LENGTH_SHORT).show();


                    }
                });


            }
        });
    }
    }
