package com.example.rsp.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rsp.R;

public class Dresses extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dresses);
        getSupportActionBar().setTitle("Dresses");

        DressesListData[] DressesListData = new DressesListData[]{
                new DressesListData("Men:"),
                new DressesListData("Western"),
                new DressesListData("Groom dress"),
                new DressesListData("Women:"),
                new DressesListData("Traditional"),
                new DressesListData("Western"),
                new DressesListData("Bridal dresses"),
                new DressesListData("Party wears"),
        };

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        DressesListAdapter DressesListAdapter = new DressesListAdapter(DressesListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(DressesListAdapter);

    }
    }
