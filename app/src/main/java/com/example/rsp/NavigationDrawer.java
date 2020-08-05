package com.example.rsp;
import android.accounts.Account;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;

import com.example.rsp.Fragments.ChatFragment;
import com.example.rsp.Fragments.FavouriteFragment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.rsp.Fragments.PostFragment;
import com.example.rsp.ui.Accessories;
import com.example.rsp.ui.Adds.MyAds;
import com.example.rsp.ui.Dresses;
import com.example.rsp.ui.Electronics;
import com.example.rsp.ui.Furniture;
import com.example.rsp.ui.Login;
import com.example.rsp.ui.Property;
import com.example.rsp.ui.Vehicles;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import de.hdodenhof.circleimageview.CircleImageView;
public class NavigationDrawer extends AppCompatActivity {
    private StorageReference postimages ;
    Uri imageuri ;
    private String downloadurl ;
    String currentuserid ;
    ProgressDialog progressDialog;
    private TextView fullname;
    private FirebaseAuth mAuth;
    private DatabaseReference ProfileImgref ;
    String randomname;
    DrawerLayout drawerLayout ;
    Toolbar toolbar ;
    ActionBarDrawerToggle drawerToggle;
    BottomNavigationView bottomNavigationView;
    NavigationView navigationView ;
    CircleImageView circleImageView;
    TextView email , name ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigationdrawer);
        mAuth = FirebaseAuth.getInstance();
        currentuserid = mAuth.getCurrentUser().getUid();
        loadFragment(new PostFragment());
        progressDialog=new ProgressDialog(this);
        postimages = FirebaseStorage.getInstance().getReference();
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationview);
        drawerLayout = findViewById(R.id.drawerlayout);
        View headView=navigationView.getHeaderView(0);

        circleImageView=headView.findViewById(R.id.Circleimg);
        name = headView.findViewById(R.id.txt_fname);
        email = headView.findViewById(R.id.txt_email);




        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadIamge();
            }
        });



        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = setupDrawerToggle();
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);

        ProfileImgref = FirebaseDatabase.getInstance().getReference().child("User");


        ProfileImgref.child(currentuserid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    String _name = (String) dataSnapshot.child("FullName").getValue();
                    String _email = (String) dataSnapshot.child("EmailAddress").getValue();
                    name.setText(_name);
                    email.setText(_email);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId() ) {
                    case R.id.nav_My_Ads:
                        startActivity(new Intent(NavigationDrawer.this, MyAds.class));
                        return true;

                    case R.id.nav_History:
                        startActivity(new Intent(NavigationDrawer.this, HistroyActivity.class));
                        return true;

                    case R.id.nav_Account:
                        startActivity(new Intent(NavigationDrawer.this, AcountActivity.class));
                        return true;

                    case R.id.Log_Out:
                         mAuth.signOut();
                         finish();
                         startActivity(new Intent(NavigationDrawer.this , Login.class));
                        return true;
                }
                return false;
                    }

        });

        bottomNavigationView = findViewById(R.id.bottomnavigationview);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    protected void onStart() {
        super.onStart();

        ProfileImgref.child(currentuserid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("ProfileImg"))
                {
                    String img = (String) dataSnapshot.child("ProfileImg").getValue();
                    Glide.with(getApplicationContext()).load(img).into(circleImageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId())
            {
                case R.id.action_home:
                    toolbar.setTitle("Post");
                    loadFragment(new PostFragment());
                    return true ;
                case R.id.action_Chat:
                    toolbar.setTitle("Chat");
                    loadFragment(new ChatFragment());
                    return true ;


                case R.id.action_favorites:
                    toolbar.setTitle("Favourite");
                    loadFragment(new FavouriteFragment());
                    return true ;
            }
            return false;
        }
    };


    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }
    private void uploadIamge() {
        Intent galleryintent = new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(Intent.createChooser(galleryintent ,"Select Image") ,438);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 438 && resultCode == RESULT_OK && data.getData() != null)
        {
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
                            Toast.makeText(NavigationDrawer.this, "Image uploaded", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            HashMap hashMap = new HashMap();
                            hashMap.put("ProfileImg", downloadurl);
                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(NavigationDrawer.this, "added sucessfully", Toast.LENGTH_SHORT).show(); }
                            }); }
                    });
                }

            });
        }
    }

    private void loadFragment(Fragment fragment) {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, fragment).addToBackStack(null)
                .commit();
    }

}


