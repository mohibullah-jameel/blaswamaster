package com.example.rsp;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.util.ULocale;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.lang.reflect.Parameter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
public class AdPost extends AppCompatActivity {
//initialize the detail of item
    EditText txtTitle,txtDescription,txtOwnername,txtOwneraddress,txtMobilenumber,txtPrice;
//    location
    Button btnchooselocation;
//    submit button
    private Button Submit;
    Spinner sp_category, sp_subcategory ,sp_condition, sp_price;
    ArrayList<String> arrayList_category;
    ArrayAdapter<String>arrayAdapter_category;
    ArrayList<String>arrayList_vehicles,arrayList_dresses,arrayList_electronics,arrayList_furniture,arrayList_property,arrayList_acessories;

    ArrayAdapter<String>arrayAdapter_subcategory;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    String CurrentDate , CurrentTime;
    String randomname ;
    Button uploadimagebutton ;
    private StorageReference postimages ;
    Uri imageuri ;
    FirebaseAuth mauth ;
    private DatabaseReference postref ;
    String myurl = "" , Currentuserid ;
    private String downloadurl ;
    String currentuserid ;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ad_post);
//  ======      spinner of select Category==========///////
        mauth = FirebaseAuth.getInstance();
        currentuserid = mauth.getCurrentUser().getUid();


        sp_category=(Spinner) findViewById(R.id.sp_category);
        progressDialog = new ProgressDialog(this);
        sp_subcategory=(Spinner) findViewById(R.id.sp_subcategory);
        sp_condition=(Spinner) findViewById(R.id.sp_condition);
        postimages = FirebaseStorage.getInstance().getReference();
        sp_price=(Spinner)findViewById(R.id.sp_price);

        txtMobilenumber = findViewById(R.id.txt_mobileno);
//        upload image
        uploadimagebutton = (Button) findViewById(R.id.selectimage);
        Calendar calendarfordate = Calendar.getInstance();
        SimpleDateFormat currentdate = new SimpleDateFormat("dd-MMMM-yyyy");
        CurrentDate = currentdate.format(calendarfordate.getTime());
        postref = FirebaseDatabase.getInstance().getReference().child("Post").child(currentuserid);
        Calendar calendarfortime = Calendar.getInstance();
        SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss");
        CurrentTime = currenttime.format(calendarfortime.getTime());
        randomname = CurrentTime + CurrentDate;;
        arrayList_category=new ArrayList<>();
        arrayList_category.add("Vehicles");
        arrayList_category.add("Dresses");
        arrayList_category.add("Electronics");
        arrayList_category.add("Furniture");
        arrayList_category.add("Property");
        arrayList_category.add("Accessories");
        arrayAdapter_category=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item,arrayList_category);
        sp_category.setAdapter(arrayAdapter_category);
        uploadimagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadIamge();
            }
        });

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

                 } }

             @Override
             public void onNothingSelected(AdapterView<?> adapterView) {

             }
         });

// ========end of  sub category ===========////
        txtTitle = (EditText) findViewById(R.id.txt_title);
        txtDescription = (EditText) findViewById(R.id.txt_description);
        txtOwnername = (EditText) findViewById(R.id.txt_ownername);
        txtOwneraddress=(EditText) findViewById(R.id.txt_owneraddress);
        txtPrice=(EditText) findViewById(R.id.txt_price);
        Submit = (Button) findViewById(R.id.btnsubmit);

        databaseReference= FirebaseDatabase.getInstance().getReference("Post");
        firebaseAuth=FirebaseAuth.getInstance();
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Title = txtTitle.getText().toString();
                String Description = txtDescription.getText().toString();
                String Ownername = txtOwnername.getText().toString();
                String Owneraddress = txtOwneraddress.getText().toString();
                String Mobilenumber = txtMobilenumber.getText().toString();
                String Price=txtPrice.getText().toString();
                String Category=sp_category.getSelectedItem().toString();
                String Subcategory= sp_subcategory.getSelectedItem().toString();
                String Condition=sp_condition.getSelectedItem().toString();
                String Selectprice=sp_price.getSelectedItem().toString();

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
                if (TextUtils.isEmpty(Price)) {
                    Toast.makeText(AdPost.this, "Please Enter price", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (downloadurl.equals(null)) {
                    Toast.makeText(AdPost.this, "Upload image", Toast.LENGTH_SHORT).show();
                    return ;
                }

                HashMap hashMap = new HashMap();
                hashMap.put("Title" , Title);
                hashMap.put("Description" , Description);
                hashMap.put("Ownername" ,Ownername);
                hashMap.put("Owneraddress" ,Owneraddress);
                hashMap.put("Mobilenumber" ,Mobilenumber);
                hashMap.put("Category" ,Category);
                hashMap.put("Subcategory" ,Subcategory);
                hashMap.put("Condition" ,Condition);
                hashMap.put("Selectprice" ,Selectprice);
                hashMap.put("Price" ,Price);
                hashMap.put("Addby", currentuserid);
                hashMap.put("Image" , downloadurl);

                FirebaseDatabase.getInstance().getReference("Post")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()+randomname)
                        .setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(AdPost.this, "added sucessfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AdPost.this , NavigationDrawer.class);
                        startActivity(intent);
                        finish();

                    }
                });

            }


        });
    }

    private void uploadIamge() {
        Intent galleryintent = new Intent();

        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(Intent.createChooser(galleryintent ,"Select Image") ,438);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 438 && resultCode == RESULT_OK && data.getData() != null)
        {
            progressDialog.setTitle("Please wait");
            progressDialog.setMessage("setting your profile picture");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            imageuri = data.getData();
            final StorageReference filepath = postimages.child("Profile Images").child(imageuri.getLastPathSegment() +
                    randomname + ".jpg");
            filepath.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    final Task<Uri> firebaseUri = taskSnapshot.getStorage().getDownloadUrl();
                    firebaseUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            downloadurl = uri.toString();
                            Toast.makeText(AdPost.this, "Image uploaded", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                                }
                            });

                        }
            });

        }
    }




}

