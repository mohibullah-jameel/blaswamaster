package com.example.rsp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class bottomnavigationbar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottomnavigationbar);
        getSupportActionBar().setTitle("bottomnavigationbar");
    }


}


