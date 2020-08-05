package com.example.rsp.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rsp.R;
import com.example.rsp.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatFragment extends Fragment {

    View view ;
    RecyclerView recyclerView ;
    private DatabaseReference userref ;
    private FirebaseAuth mAuth ;
    private DatabaseReference messageref ;
    String currentuserid;
    ProgressDialog progressDialog ;
    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        mAuth = FirebaseAuth.getInstance();
        currentuserid = mAuth.getCurrentUser().getUid();
        userref = FirebaseDatabase.getInstance().getReference().child("User");
        messageref = FirebaseDatabase.getInstance().getReference().child("Messages").child(currentuserid);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        progressDialog = new ProgressDialog(getContext());
        recyclerView.setNestedScrollingEnabled(false);
        return view ;
    }

    @Override
    public void onStart() {
        super.onStart();


        FirebaseRecyclerOptions options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(messageref, User.class)
                        .build();
        FirebaseRecyclerAdapter<User, UserViewHOlder> firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<User, UserViewHOlder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final UserViewHOlder userViewHOlder, int i, @NonNull User user) {
                final String postid = getRef(i).getKey();

                userref.child(postid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            if (isAdded()) {
                                String Image = (String) dataSnapshot.child("ProfileImg").getValue();
                                String username = (String) dataSnapshot.child("FullName").getValue();
                                Glide.with(getContext()).load(Image).into(userViewHOlder.profileimage);

                                userViewHOlder.username.setText(username);

                                userViewHOlder.itemView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(getContext() , ChatActivity.class);
                                        intent.putExtra("Userid" , postid);
                                        startActivity(intent);
                                    }
                                });
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @NonNull
            @Override
            public UserViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allmessageslayout, parent, false);
                UserViewHOlder userViewHOlder = new UserViewHOlder(view);
                return userViewHOlder;
            }

        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    public static class UserViewHOlder extends RecyclerView.ViewHolder {

        private CircleImageView profileimage ;
        private TextView username , profession ;

        public UserViewHOlder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            profileimage = itemView.findViewById(R.id.profileimage);

        }
    }

}