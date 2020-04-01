package com.example.rsp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePage extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    View view ;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

               bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem ) {

                switch (menuItem.getItemId()){
                    case R.id.action_Home:
                        Toast.makeText(HomePage.this, "Home Button Clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_Chat:
                        Toast.makeText(HomePage.this, "Chat Button Clicked", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.action_Post_Ads:

                        Toast.makeText(HomePage.this, "Post Ads Button Clicked", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(HomePage.this,AdPost.class);

                        startActivity(intent);

                        break;
                    case R.id.action_favorites:
                        Toast.makeText(HomePage.this, "Favorite Button Clicked", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        });


    }

}
