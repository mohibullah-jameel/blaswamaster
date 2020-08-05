package com.example.rsp.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rsp.R;

public class Property extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.property);

        PropertyListData[] PropertyListData = new PropertyListData[]{
                new PropertyListData("Houses"),
                new PropertyListData("Land & Plots"),
                new PropertyListData("Apartment & Flats"),
                new PropertyListData("Guest Houses"),
                new PropertyListData("Shops/Offices/Commercial Space"),

        };

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        PropertyListAdapter PropertyListAdapter = new PropertyListAdapter(PropertyListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(PropertyListAdapter);

    }
    }
