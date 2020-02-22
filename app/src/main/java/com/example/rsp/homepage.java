package com.example.rsp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class homepage extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem ) {

                switch (menuItem.getItemId()){
                    case R.id.action_Home:
                        Toast.makeText(homepage.this, "Home Button Clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_Chat:
                        Toast.makeText(homepage.this, "Chat Button Clicked", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.action_Post_Ads:
                        Toast.makeText(homepage.this, "Post Ads Button Clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_favorites:
                        Toast.makeText(homepage.this, "Favorite Button Clicked", Toast.LENGTH_SHORT).show();
                        break;

                }
                return;
            }
        });

    }
}
