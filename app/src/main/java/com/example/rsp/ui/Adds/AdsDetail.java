package com.example.rsp.ui.Adds;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.rsp.Fragments.ChatActivity;
import com.example.rsp.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.HashMap;
import java.util.List;

public class AdsDetail extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    String id;
    ImageView imageView , fav;
    String img;
    TextView textdescription;
    TextView textprice  ;
    TextView texttitle;
    TextView conditon , category , subcategory ;
    String currentuser ;
    DatabaseReference post , Favref ;
    FloatingActionButton floatingActionButton , call;
    String addby ;
    TextView username , phonenumber ;
    String phone ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_detail);
        firebaseAuth = FirebaseAuth.getInstance();
        currentuser = firebaseAuth.getCurrentUser().getUid();
        id = getIntent().getStringExtra("ID");
        floatingActionButton = findViewById(R.id.chat);
        firebaseAuth = FirebaseAuth.getInstance();
        post = FirebaseDatabase.getInstance().getReference().child("Post").child(id);
        Favref = FirebaseDatabase.getInstance().getReference().child("Favourites");
        imageView = findViewById(R.id.image);
        textdescription=findViewById(R.id.description);
        texttitle=findViewById(R.id.title);
        textprice=findViewById(R.id.price);
        conditon = findViewById(R.id.txtcondition);
        category = findViewById(R.id.txtcategory);
        subcategory = findViewById(R.id.txtsubcategory);
        fav = findViewById(R.id.fav);
        username = findViewById(R.id.username);
        phonenumber = findViewById(R.id.phonenumber);
        call = findViewById(R.id.call);




        post.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    String name = (String)dataSnapshot.child("Ownername").getValue();
                    phone = (String)dataSnapshot.child("Mobilenumber").getValue();
                    username.setText(name);
                    phonenumber.setText(phone);
                    String title = dataSnapshot.child("Title").getValue().toString();
                    texttitle.setText(title);
                    String description = dataSnapshot.child("Description").getValue().toString();
                    textdescription.setText(description);
                    String price = dataSnapshot.child("Price").getValue().toString();
                    textprice.setText(price);
                    String getcondtion = dataSnapshot.child("Condition").getValue().toString();
                    conditon.setText(getcondtion);
                    addby = dataSnapshot.child("Addby").getValue().toString();
                    String  cat = dataSnapshot.child("Category").getValue().toString();
                    category.setText(cat);
                    String subcat = dataSnapshot.child("Subcategory").getValue().toString();
                    subcategory.setText(subcat);
                    if (dataSnapshot.hasChild("Image"))
                    {
                        String img = (String) dataSnapshot.child("Image").getValue();
                        Glide.with(getApplicationContext()).load(img).into(imageView);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap hashMap = new HashMap();
                hashMap.put("Like by" , id);

                Favref.child(currentuser).child(id).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        Toast.makeText(AdsDetail.this, "Done", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdsDetail.this , ChatActivity.class);
                intent.putExtra("Userid" , addby);
                startActivity(intent);
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(AdsDetail.this)
                        .withPermissions(
                                Manifest.permission.CALL_PHONE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                // check if all permissions are granted
                                if (report.areAllPermissionsGranted()) {
                                    // do you work now
                                    if (Build.VERSION.SDK_INT >= 23) {
                                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                                        callIntent.setData(Uri.parse("tel:" + phone));
                                        startActivity(callIntent);
                                    }
                                }

                                // check for permanent denial of any permission
                                if (report.isAnyPermissionPermanentlyDenied()) {
                                    // permission is denied permenantly, navigate user to app settings
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        })
                        .onSameThread()
                        .check();
            }
        });


    }



}


