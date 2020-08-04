package com.example.rsp;
import android.accounts.Account;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;

import com.example.rsp.Fragments.FavouriteFragment;
import com.example.rsp.ui.Accessories;
import com.example.rsp.ui.Adds.AdsDetail;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.rsp.ui.Adds.MyAds;
import com.example.rsp.ui.Dresses;
import com.example.rsp.ui.Electronics;
import com.example.rsp.ui.Furniture;
import com.example.rsp.ui.Login;
import com.example.rsp.ui.Property;
import com.example.rsp.ui.Vehicles;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import de.hdodenhof.circleimageview.CircleImageView;
   public class HomePage extends AppCompatActivity {
    private StorageReference postimages;
    private DatabaseReference postref;
    Uri imageuri;
    String myurl = "", Currentuserid;
    private String downloadurl;
    String currentuserid;
    ProgressDialog progressDialog;
    int[] images;
    private FirebaseAuth mAuth;
    private DatabaseReference Postref, ProfileImgref,Favref;
    String CurrentDate, CurrentTime;
    String randomname;
    private ProgressDialog progress;
    Button btn_electronics,btn_dresses,btn_furniture,btn_accessories,btn_propety,btn_vehicles;

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigationView;
    CircleImageView circleImageView;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        progressDialog = new ProgressDialog(this);
        postimages = FirebaseStorage.getInstance().getReference();
        toolbar = findViewById(R.id.toolbar);

        navigationView = findViewById(R.id.navigationview);
        drawerLayout = findViewById(R.id.drawerlayout);
        View headView = navigationView.getHeaderView(0);
        circleImageView = headView.findViewById(R.id.Circleimg);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadIamge();
            }
        });
        btn_electronics= (Button) findViewById(R.id.Buttonelectronics);
        btn_electronics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, Electronics.class));
            }
        });
        btn_dresses= (Button) findViewById(R.id.Buttondresses);
        btn_dresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, Dresses.class));
            }
        });
        btn_propety= (Button) findViewById(R.id.Buttonproperty);
        btn_propety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, Property.class));
            }
        });
        btn_accessories= (Button) findViewById(R.id.Buttonaccessories);
        btn_accessories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, Accessories.class));
            }
        });
        btn_furniture= (Button) findViewById(R.id.Buttonfurniture);
        btn_furniture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, Furniture.class));
            }
        });
        btn_vehicles= (Button) findViewById(R.id.Buttonvechiles);
        btn_vehicles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, Vehicles.class));
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = setupDrawerToggle();
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);
        mAuth = FirebaseAuth.getInstance();
        currentuserid = mAuth.getCurrentUser().getUid();
        Postref = FirebaseDatabase.getInstance().getReference().child("Post");

        ProfileImgref = FirebaseDatabase.getInstance().getReference().child("User");
        Calendar calendarfordate = Calendar.getInstance();
        SimpleDateFormat currentdate = new SimpleDateFormat("dd-MMMM-yyyy");
        CurrentDate = currentdate.format(calendarfordate.getTime());
        Calendar calendarfortime = Calendar.getInstance();
        SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss");
        CurrentTime = currenttime.format(calendarfortime.getTime());
        randomname = CurrentTime + CurrentDate;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

    }


    @Override
    public void onStart() {
        super.onStart();
        ProfileImgref.child(currentuserid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    if (dataSnapshot.hasChild("ProfileImg")) {
                        String img = (String) dataSnapshot.child("ProfileImg").getValue();
                        Glide.with(getApplicationContext()).load(img).into(circleImageView);
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }
        private void uploadIamge () {
            Intent galleryintent = new Intent();
            galleryintent.setAction(Intent.ACTION_GET_CONTENT);
            galleryintent.setType("image/*");
            startActivityForResult(Intent.createChooser(galleryintent, "Select Image"), 438);
        }

        @Override
        public void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == 438 && resultCode == RESULT_OK && data.getData() != null) {
                progressDialog.setTitle("Please wait");
                progressDialog.setMessage("setting your profile picture");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                imageuri = data.getData();
                final StorageReference filepath = postimages.child("Profile Images").child(imageuri.getLastPathSegment() +
                        randomname + ".jpg");
                filepath.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        final Task<Uri> firebaseUri = taskSnapshot.getStorage().getDownloadUrl();
                        firebaseUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                downloadurl = uri.toString();
                                Toast.makeText(HomePage.this, "Image uploaded", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                HashMap hashMap = new HashMap();
                                hashMap.put("ProfileImg", downloadurl);
                                FirebaseDatabase.getInstance().getReference("User")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {

                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(HomePage.this, "added sucessfully", Toast.LENGTH_SHORT).show();

                                    }
                                });


                            }
                        });
                    }

                });
            }
        }


    }


