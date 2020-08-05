package com.example.rsp.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rsp.R;

public class Electronics extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.electronics);

        ElectronicListData[] myListData=new ElectronicListData[]{

                new ElectronicListData("Television"),
                new ElectronicListData("Computer/laptop & Accessories"),
                new ElectronicListData("Camera & Accessories"),
                new ElectronicListData("Games& Entertainment"),
                new ElectronicListData("Home Appliances"),
                new ElectronicListData("Generator/UPS"),
                new ElectronicListData("AC/ Cooler"),
                new ElectronicListData("Fridge& Freezers"),
                new ElectronicListData("Washing Machines & Dryers"),

        };

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ElectronicsListAdapter myListAdapter = new ElectronicsListAdapter(myListData , this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myListAdapter);


    }

}
