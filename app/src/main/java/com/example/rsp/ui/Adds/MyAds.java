
package com.example.rsp.ui.Adds;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import com.bumptech.glide.Glide;
        import com.example.rsp.R;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

public class MyAds extends AppCompatActivity {

    DatabaseReference post;
    FirebaseAuth firebaseAuth;
    String id;
    ImageView imageView ;
    TextView textprice;
    TextView texttitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_detail);
        firebaseAuth = FirebaseAuth.getInstance();
        id = getIntent().getStringExtra("ID");
        firebaseAuth = FirebaseAuth.getInstance();
        post = FirebaseDatabase.getInstance().getReference().child("Post").child(id);
        imageView = findViewById(R.id.ImageView_image);
        texttitle=findViewById(R.id.textTitle);
        textprice=findViewById(R.id.textPrice);

        post.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    String title = dataSnapshot.child("Title").getValue().toString();
                    texttitle.setText(title);
                    String price = dataSnapshot.child("Price").getValue().toString();
                    textprice.setText(price);
                    if (dataSnapshot.hasChild("Image"))
                    {
                        String img = (String) dataSnapshot.child("Image").getValue();
                        Glide.with(getApplicationContext()).load(img).into(imageView);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}


