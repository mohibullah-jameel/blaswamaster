package com.example.rsp.ui.MyAdds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.CaseMap;
import android.media.Image;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rsp.AdPost;
import com.example.rsp.NavigationDrawer;
import com.example.rsp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import javax.xml.validation.Validator;

public class AdsDetail extends AppCompatActivity {

    DatabaseReference post;
    FirebaseAuth firebaseAuth;
    String id;
    ImageView imageView ;
    TextView textprice;
    TextView textitle;
    TextView textdescription;
    TextView textparameter;
    TextView textownername;
    TextView textowneraddress;
    TextView textmobilenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_detail);
        firebaseAuth=FirebaseAuth.getInstance();
        id = getIntent().getStringExtra("ID");
        post= FirebaseDatabase.getInstance().getReference().child("Post").child(id);
        imageView = findViewById(R.id.profile_image);
        textprice=findViewById(R.id.txt_price);
        textitle=findViewById(R.id.txt_title);

    }

    @Override
    protected void onStart() {
        super.onStart();

        post.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    if (dataSnapshot.hasChild("Image"))
                    {
                        String image =(String) dataSnapshot.child("Image").getValue();
                        Picasso.get().load(image).into(imageView);

                    }
                    if(dataSnapshot.hasChild("Price"))
                    {
                        String price=(String)dataSnapshot.child("Price").getValue();

                        textprice.setText(price);
                    }
                    if(dataSnapshot.hasChild("Title"))
                    {
                        String title=(String)dataSnapshot.child("Title").getValue();

                        textitle.setText(title);
                    }
                    if(dataSnapshot.hasChild("Description"))
                    {
                        String description=(String)dataSnapshot.child("Description").getValue();

                        textdescription.setText(description);
                    }
                    if(dataSnapshot.hasChild("Parameter"))
                    {
                        String parameter=(String)dataSnapshot.child("Parameter").getValue();

                        textparameter.setText(parameter);
                    }
                    if(dataSnapshot.hasChild("Ownername"))
                    {
                        String ownername=(String)dataSnapshot.child("Ownername").getValue();

                        textownername.setText(ownername);
                    }
                    if(dataSnapshot.hasChild("Owneraddress"))
                    {
                        String owneraddress=(String)dataSnapshot.child("Owneraddress").getValue();

                        textowneraddress.setText(owneraddress);
                    }
                    if(dataSnapshot.hasChild("Mobilenumber"))
                    {
                        String mobilenumber=(String)dataSnapshot.child("Mobilenumber").getValue();

                        textmobilenumber.setText(mobilenumber);
                    }
                    }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
