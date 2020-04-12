package com.example.rsp;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import com.example.rsp.ui.Accessories;
import com.example.rsp.ui.Dresses;
import com.example.rsp.ui.Electronics;
import com.example.rsp.ui.Furniture;
import com.example.rsp.ui.Property;
import com.example.rsp.ui.Vehicles;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
public class NavigationDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final int GALLARY_PICK = 1;
    private AppBarConfiguration mAppBarConfiguration;
    private ImageView imageView;
   private Uri imageUri;
   private StorageReference reference;
   private DatabaseReference mDatabase;
    String haha ;
    DrawerLayout drawer;
    View headerview ;
    CircleImageView circleImageView ;
    TextView name , email ;
    ActionBarDrawerToggle actionBarDrawerToggle ;
    ImageView imageView , vechiles , dresses , furniture , property,accessories;
    FirebaseAuth firebaseAuth ;
    DatabaseReference firebaseDatabase ;
    String Currentuser ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigationdrawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        imageUri=null;
        mDatabase=FirebaseDatabase.getInstance().getReference().child("Images");
        reference=FirebaseStorage.getInstance().getReference().child("Images");
        setSupportActionBar(toolbar);
        imageView = findViewById(R.id.catelectronics);
        vechiles = findViewById(R.id.catvehicle);
        dresses = findViewById(R.id.catdresses);
        furniture = findViewById(R.id.catfurniture);
        property = findViewById(R.id.catproperty);
        accessories = findViewById(R.id.cataccessories);
        drawer = findViewById(R.id.drawer_layout);
        firebaseAuth = FirebaseAuth.getInstance();
        Currentuser = firebaseAuth.getCurrentUser().getUid() ;
        firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("User");
        actionBarDrawerToggle = new ActionBarDrawerToggle(this , drawer , R.string.open , R.string.close);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavigationDrawer.this, Property.class);
                startActivity(intent);
            }
        });

        furniture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavigationDrawer.this, Furniture.class);
                startActivity(intent);
            }
        });

        dresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavigationDrawer.this, Dresses.class);
                startActivity(intent);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavigationDrawer.this, Electronics.class);
                startActivity(intent);
            }
        });

        vechiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavigationDrawer.this, Vehicles.class);
                startActivity(intent);
            }
        });
        accessories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavigationDrawer.this, Accessories.class);
                startActivity(intent);
            }
        });

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        NavigationView navigationView = findViewById(R.id.nav_view);
        headerview = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_Account, R.id.nav_About, R.id.nav_Rental_History,
                R.id.nav_My_Ads, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();

        headerview = navigationView.getHeaderView(0);

        name = headerview.findViewById(R.id.txt_fname);
        email = headerview.findViewById(R.id.txt_email);
        ImageView imageView=headerview.findViewById(R.id.profile_image);
        Button button=headerview.findViewById(R.id.btn_AddImage);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Select Image"),GALLARY_PICK);
            }
        });


firebaseDatabase.child(Currentuser).addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});


        firebaseDatabase.child(Currentuser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    String Name = dataSnapshot.child("FullName").getValue().toString();
                    String Email = dataSnapshot.child("Email").getValue().toString();

                    name.setText(Name);
                    email.setText(Email);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLARY_PICK && resultCode==RESULT_OK){
            imageUri=data.getData();
            StorageReference reference1=reference.child("Image"+".jpg");
            reference1.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    final String download=taskSnapshot.getDownloadUrl().toString();
                    Map<String, String> map=new HashMap<>();
                    map.put("image",downloadUrl);
                    mDatabase.setValue(downloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Picasso.with(NavigationDrawer.this).load(downloadUrl).intro(imageView);
                            }
                        }
                    });
                }
            });
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigationdrawer, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawer.openDrawer(Gravity.LEFT);

        return true;
    }
}
