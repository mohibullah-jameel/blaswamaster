package com.example.rsp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.rsp.ui.Accessories;
import com.example.rsp.ui.AccessoriesListData;
import com.example.rsp.ui.Dresses;
import com.example.rsp.ui.DressesListData;
import com.example.rsp.ui.ElectronicListData;
import com.example.rsp.ui.Electronics;
import com.example.rsp.ui.Furniture;
import com.example.rsp.ui.FurnitureListData;
import com.example.rsp.ui.Login;
import com.example.rsp.ui.Property;
import com.example.rsp.ui.PropertyListData;
import com.example.rsp.ui.Vehicles;
import com.example.rsp.ui.VehiclesListData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;
public class AdPost extends AppCompatActivity {
    EditText txtTitle,txtDescription,txtOwnername,txtOwneraddress,txtMobilenumber;
    Button btn_submit;
    private Button Submit;
    Spinner sp_category, sp_subcategory;
    ArrayList<String> arrayList_category;
    ArrayAdapter<String>arrayAdapter_category;
    ArrayList<String>arrayList_vehicles,arrayList_dresses,arrayList_electronics,arrayList_furniture,arrayList_property,arrayList_acessories;
    ArrayList<String> arrayList_subcategory;
    ArrayAdapter<String>arrayAdapter_subcategory;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_post);
//  ======      spinner of select Category==========///////
        sp_category=(Spinner) findViewById(R.id.sp_category);
        sp_subcategory=(Spinner) findViewById(R.id.sp_subcategory);
        arrayList_category=new ArrayList<>();
        arrayList_category.add("Vehicles");
        arrayList_category.add("Dresses");
        arrayList_category.add("Electroniocs");
        arrayList_category.add("Furniture");
        arrayList_category.add("Property");
        arrayList_category.add("Accessories");
        arrayAdapter_category=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item,arrayList_category);
        sp_category.setAdapter(arrayAdapter_category);

//=========spinner of sub category========//

//=================Vehicles===========///
        arrayList_vehicles= new ArrayList<>();
        arrayList_vehicles.add("Vehicles");
        arrayList_vehicles.add ("Car");
        arrayList_vehicles.add("Buses,Vans& Trucks");
        arrayList_vehicles.add("Rikshaw");
        arrayList_vehicles.add("Motercycles");
        arrayList_vehicles.add("Bicycles");
        arrayList_vehicles.add("Scooters");
        arrayList_vehicles.add("Spare parts");

//==================Dresses===========/////
        arrayList_dresses=new ArrayList<>();
        arrayList_dresses.add("Men:");
        arrayList_dresses.add("Western");
        arrayList_dresses.add("Groom dress");
        arrayList_dresses.add("Women:");
        arrayList_dresses.add("Traditional");
        arrayList_dresses.add("Western");
        arrayList_dresses.add("Bridal dresses");
        arrayList_dresses.add("Party wears");

//==================Electronics=========//////
        arrayList_electronics=new ArrayList<>();
        arrayList_electronics.add("Television");
        arrayList_electronics.add("Computer/laptop & Accessories");
        arrayList_electronics.add("Camera & Accessories");
        arrayList_electronics.add("Games& Entertainment");
        arrayList_electronics.add("Home Appliances");
        arrayList_electronics.add("Generator/UPS");
        arrayList_electronics.add("AC/ Cooler");
        arrayList_electronics.add("Fridge& Freezers");
        arrayList_electronics.add("Washing Machines & Dryers");
//=====================Furniture==========//////////

        arrayList_furniture=new ArrayList<>();
        arrayList_furniture.add("Sofa& chairs");
        arrayList_furniture.add ("Beds& Wardrobes");
        arrayList_furniture.add("Tables& Dining");
        arrayList_furniture.add("Home Decoration");
        arrayList_furniture.add("Garden& outdoor");
        arrayList_furniture.add("Rugs& Carpets");
        arrayList_furniture.add("office Furniture");
        arrayList_furniture.add("Other house hold items");

//==============Property=============///
        arrayList_property=new ArrayList<>();
        arrayList_property.add("Houses");
        arrayList_property.add("Land & Plots");
        arrayList_property.add("Apartment & Flats");
        arrayList_property.add("Guest Houses");
        arrayList_property.add("Shops/Offices/Commercial Space");
//    ===============Accessories=============///////

        arrayList_acessories=new ArrayList<>();
        arrayList_acessories.add("Men:");
        arrayList_acessories.add("Boots & Shoes");
        arrayList_acessories.add("Watches");
        arrayList_acessories.add("Women:");
        arrayList_acessories.add("Boots & Shoes");
        arrayList_acessories.add("Watches");
        arrayList_acessories.add("Jewellery");
        arrayList_acessories.add("Bags & Clutches");

         sp_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                 if (i ==0){
                     arrayAdapter_subcategory=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item,arrayList_vehicles);
                     sp_subcategory.setAdapter(arrayAdapter_subcategory);
                 }

                 if (i==1){
                     arrayAdapter_subcategory=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item,arrayList_dresses);
                     sp_subcategory.setAdapter(arrayAdapter_subcategory);
                 }
                 if (i==2){
                     arrayAdapter_subcategory=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item,arrayList_electronics);
                     sp_subcategory.setAdapter(arrayAdapter_subcategory);

                 }
                 if (i==3){
                     arrayAdapter_subcategory=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item,arrayList_furniture);
                     sp_subcategory.setAdapter(arrayAdapter_subcategory);
                 }
                 if (i==4){
                     arrayAdapter_subcategory=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item,arrayList_property);
                     sp_subcategory.setAdapter(arrayAdapter_subcategory);
                 }
                 if (i==5){
                     arrayAdapter_subcategory=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item,arrayList_acessories);
                     sp_subcategory.setAdapter(arrayAdapter_subcategory);
                 }

             }


             @Override
             public void onNothingSelected(AdapterView<?> adapterView) {

             }

         });


// ========end of  sub category ===========////

        txtTitle = (EditText) findViewById(R.id.txt_title);
        txtDescription = (EditText) findViewById(R.id.txt_description);
        txtOwnername = (EditText) findViewById(R.id.txt_ownername);
        txtOwneraddress=(EditText) findViewById(R.id.txt_owneraddress);
        txtMobilenumber=(EditText) findViewById(R.id.txt_mobileno);
        Submit = (Button) findViewById(R.id.btnsubmit);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdPost.this, NavigationDrawer.class));
            }
        });
        databaseReference= FirebaseDatabase.getInstance().getReference("Post");
        firebaseAuth=FirebaseAuth.getInstance();
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Title = txtTitle.getText().toString().trim();
                String Description = txtDescription.getText().toString().trim();
                String Ownername = txtOwnername.getText().toString().trim();
                String Owneraddress = txtOwneraddress.getText().toString().trim();
                String Mobilenumber = txtMobilenumber.getText().toString().trim();

                if (TextUtils.isEmpty(Title)) {
                    Toast.makeText(AdPost.this, "Please Enter the title", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(Description)) {
                    Toast.makeText(AdPost.this, "Please Enter description ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(Ownername)) {
                    Toast.makeText(AdPost.this, "Please Enter ownername", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(Owneraddress)) {
                    Toast.makeText(AdPost.this, "Please Enter owneraddress", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(Mobilenumber)) {
                    Toast.makeText(AdPost.this, "Please Enter mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }

                Post information = new Post(
                        Title,
                        Description,
                        Ownername,
                        Owneraddress,
                        Mobilenumber

                );

                FirebaseDatabase.getInstance().getReference("Post")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(AdPost.this, "added sucessfully", Toast.LENGTH_SHORT).show();


                    }
                });


            }
        });
    }
    }
