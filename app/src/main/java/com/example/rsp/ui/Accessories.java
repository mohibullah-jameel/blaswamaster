package com.example.rsp.ui;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rsp.R;

public class Accessories extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accessories);
        getSupportActionBar().setTitle("Accessories");
        AccessoriesListData[] AccessoriesListData = new AccessoriesListData[]{
                new AccessoriesListData("Men:"),
                new AccessoriesListData("Boots & Shoes"),
                new AccessoriesListData("Watches"),
                new AccessoriesListData("Women:"),
                new AccessoriesListData("Boots & Shoes"),
                new AccessoriesListData("Watches"),
                new AccessoriesListData("Jewellery"),
                new AccessoriesListData("Bags & Clutches"),
        };

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        AccessoriesListAdapter AccessoriesListAdapter = new AccessoriesListAdapter(AccessoriesListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(AccessoriesListAdapter);

    }
    }
