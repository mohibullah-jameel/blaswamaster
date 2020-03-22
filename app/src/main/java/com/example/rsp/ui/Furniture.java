package com.example.rsp.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rsp.R;

public class Furniture extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.furniture);
        getSupportActionBar().setTitle("Furniture");
        FurnitureListData[] MyListData2=new FurnitureListData[]{

                new FurnitureListData("Sofa& chairs"),
                new FurnitureListData("Beds& Wardrobes"),
                new FurnitureListData("Tables& Dining"),
                new FurnitureListData("Home Decoration"),
                new FurnitureListData("Garden& outdoor"),
                new FurnitureListData("Rugs& Carpets"),
                new FurnitureListData("office Furniture"),
                new FurnitureListData("Other house hold items"),




        };

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        FurnitureListAdapter myListAdapter2 = new FurnitureListAdapter(MyListData2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myListAdapter2);

    }
}
