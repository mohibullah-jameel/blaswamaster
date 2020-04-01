package com.example.rsp.ui;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rsp.R;
public class Vehicles extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicles);
        getSupportActionBar().setTitle("Vehicles");
        VehiclesListData[] VehiclesListData = new VehiclesListData[]{
                new VehiclesListData("Car:"),
                new VehiclesListData("Buses,Vans& Trucks"),
                new VehiclesListData("Rikshaw"),
                new VehiclesListData("Motercycles"),
                new VehiclesListData("Bicycles"),
                new VehiclesListData("Scooters"),
                new VehiclesListData("Spare parts"),
        };
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        VehiclesListAdapter VehiclesListAdapter = new VehiclesListAdapter(VehiclesListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(VehiclesListAdapter);

    }
    }
