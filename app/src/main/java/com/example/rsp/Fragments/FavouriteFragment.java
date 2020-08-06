package com.example.rsp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.example.rsp.Post;
import com.example.rsp.PostViewholder;
import com.example.rsp.R;
import com.example.rsp.ui.Adds.AdsDetail;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class FavouriteFragment extends Fragment {



    public FavouriteFragment() {
        // Required empty public constructor
    }

    View view ;
    RecyclerView mRecyclerView;
    private DatabaseReference Postref,Favref ;
    FirebaseAuth firebaseAuth ;
    String currentuserid ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_favourite, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        currentuserid = firebaseAuth.getCurrentUser().getUid();
        Postref = FirebaseDatabase.getInstance().getReference().child("Post");
        Favref = FirebaseDatabase.getInstance().getReference().child("Favourites").child(currentuserid);


        mRecyclerView = view.findViewById(R.id.recyclerviewmain);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setNestedScrollingEnabled(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseRecyclerOptions options =
                new FirebaseRecyclerOptions.Builder<Post>()
                        .setQuery(Favref, Post.class)
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
                                    String address = dataSnapshot.child("Address").getValue().toString();
                                    holder.address.setText(address);
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
                                        Glide.with(getContext()).load(image).into(holder.imageView) ;
                                    }
                                    holder.imageView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(getContext() , AdsDetail.class);
                                            intent.putExtra("ID" , postid);
                                            startActivity(intent);

                                        }
                                    });

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
        mRecyclerView.setAdapter(firebaseRecyclerOptions);
        firebaseRecyclerOptions.startListening();

    }

}