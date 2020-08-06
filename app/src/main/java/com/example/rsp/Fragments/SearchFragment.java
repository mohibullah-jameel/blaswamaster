package com.example.rsp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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


public class SearchFragment extends Fragment {
    View view ;
    public SearchFragment() {
        // Required empty public constructor
    }


    RecyclerView recyclerView ;
    private DatabaseReference userref ;
    private FirebaseAuth mAuth ;
    private DatabaseReference Postref,Favref ;
    String currentuserid;
    EditText search ;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_search, container, false);
        Postref = FirebaseDatabase.getInstance().getReference().child("Post");
        Favref = FirebaseDatabase.getInstance().getReference().child("Favourites");
        search = view.findViewById(R.id.search);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView = view.findViewById(R.id.recyclerviewmain);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Search(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view ;
    }

   public void Search(String s) {

       Query q = Postref.orderByChild("Subcategory").startAt(s).endAt(s + "\uf8ff");
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
                                    String address = dataSnapshot.child("Address").getValue().toString();
                                    holder.address.setText(address);
                                    if (dataSnapshot.hasChild("Available"))
                                    {
                                        String  a = (String) dataSnapshot.child("Available").getValue();

                                        if (a.equals("No"))
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