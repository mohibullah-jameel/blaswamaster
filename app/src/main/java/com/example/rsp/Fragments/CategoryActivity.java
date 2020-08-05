package com.example.rsp.Fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.example.rsp.MyAdsViewHolder;
import com.example.rsp.Post;
import com.example.rsp.PostViewholder;
import com.example.rsp.R;
import com.example.rsp.ui.Adds.AdsDetail;
import com.example.rsp.ui.Adds.MyAds;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class CategoryActivity extends AppCompatActivity {

    RecyclerView recyclerView ;
    private DatabaseReference userref ;
    private FirebaseAuth mAuth ;
    private DatabaseReference Postref,Favref ;
    String currentuserid;
    String cat ;
    EditText search ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Postref = FirebaseDatabase.getInstance().getReference().child("Post");
        Favref = FirebaseDatabase.getInstance().getReference().child("Favourites");
        search = findViewById(R.id.search);
        cat = getIntent().getExtras().get("cat").toString();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recyclerviewmain);
        recyclerView.setLayoutManager(new GridLayoutManager(CategoryActivity.this, 2));
        recyclerView.setNestedScrollingEnabled(true);
    }
    @Override
    public void onStart() {
        super.onStart();
        Query q = Postref.orderByChild("Subcategory").equalTo(cat);
        FirebaseRecyclerOptions options =
                new FirebaseRecyclerOptions.Builder<Post>()
                        .setQuery(q, Post.class)
                        .build();
        FirebaseRecyclerAdapter<Post, PostViewholder> firebaseRecyclerOptions =
                new FirebaseRecyclerAdapter<Post, PostViewholder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final PostViewholder holder, int position, @NonNull Post model) {
                        final String postid = getRef(position).getKey();
                        Postref.child(postid).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    String title = dataSnapshot.child("Title").getValue().toString();
                                    String description = dataSnapshot.child("Description").getValue().toString();
                                    String price = dataSnapshot.child("Price").getValue().toString();
                                    holder.title.setText(title);
                                    if (dataSnapshot.hasChild("isAvailable"))
                                    {
                                        String  a = (String) dataSnapshot.child("isAvailable").getValue();

                                        if (a.equals("no"))
                                        {
                                            holder.availble.setVisibility(View.VISIBLE);
                                        }
                                    }
                                    holder.description.setText(description);
                                    holder.rupees.setText("Rs " + price);
                                    if (dataSnapshot.hasChild("Image"))
                                    {
                                        String image = dataSnapshot.child("Image").getValue().toString();
                                        Glide.with(CategoryActivity.this).load(image).into(holder.imageView) ;
                                    }
                                    holder.imageView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(CategoryActivity.this , AdsDetail.class);
                                            intent.putExtra("ID" , postid);
                                            startActivity(intent);

                                        }
                                    });

                                    if (dataSnapshot.hasChild("Available"))
                                    {
                                        String  a = (String) dataSnapshot.child("Available").getValue();

                                        if (a.equals("No"))
                                        {
                                            holder.availble.setVisibility(View.VISIBLE);
                                        }
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        int top = dptopx(3);
                        int left = dptopx(3);
                        int right = dptopx(3);
                        int bottom = dptopx(3);

                        int spancount = 2;

                        boolean isfirst2items = position < spancount;
                        boolean isislast2items = position > getItemCount() - spancount;

                        if (isfirst2items) {
                            top = dptopx(3);
                        }
                        if (isislast2items) {
                            bottom = dptopx(3);
                        }

                        boolean isleftside = (position + 1) % spancount != 0;
                        boolean isrightside = !isleftside;

                        if (isleftside) {
                            right = dptopx(3);
                        }
                        if (isrightside) {
                            left = dptopx(3);
                        }


                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.constraintLayout.getLayoutParams();
                        layoutParams.setMargins(left, top, right, bottom);
                        holder.constraintLayout.setLayoutParams(layoutParams);


                    }

                    private int dptopx(int dp) {
                        float px = dp * getResources().getDisplayMetrics().density;
                        return (int) px;
                    }

                    @NonNull
                    @Override
                    public PostViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview, parent, false);
                        PostViewholder postViewholder = new PostViewholder(view);
                        return postViewholder;
                    }
                };
        recyclerView.setAdapter(firebaseRecyclerOptions);
        firebaseRecyclerOptions.startListening();

    }
}