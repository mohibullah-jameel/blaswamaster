package com.example.rsp.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.rsp.AdPost;
import com.example.rsp.Post;
import com.example.rsp.PostViewholder;
import com.example.rsp.R;
import com.example.rsp.ui.Accessories;
import com.example.rsp.ui.Adds.AdsDetail;
import com.example.rsp.ui.Dresses;
import com.example.rsp.ui.Electronics;
import com.example.rsp.ui.Furniture;
import com.example.rsp.ui.Property;
import com.example.rsp.ui.Vehicles;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import de.hdodenhof.circleimageview.CircleImageView;


public class PostFragment extends Fragment {

    View view ;
    RecyclerView mRecyclerView;
    StorageReference postimages ;
    String currentuserid ;
    ProgressDialog progressDialog;
    private DatabaseReference Postref,Favref ;
    FloatingActionButton add ;
    String CurrentDate, CurrentTime;
    String randomname;
    private TextView fullname;
    CircleImageView electronics  , vehicles , dresses , furniture , property , accessroies;


    public PostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_post, container, false);
        progressDialog=new ProgressDialog(getContext());
        postimages = FirebaseStorage.getInstance().getReference();
        Postref = FirebaseDatabase.getInstance().getReference().child("Post");
        Favref = FirebaseDatabase.getInstance().getReference().child("Favourites");
        electronics = view.findViewById(R.id.Buttonelectronics);
        vehicles = view.findViewById(R.id.Buttonvechiles);
        dresses = view.findViewById(R.id.Buttondresses);
        furniture = view.findViewById(R.id.Buttonfurniture);
        property = view.findViewById(R.id.Buttonproperty);
        accessroies = view.findViewById(R.id.Buttonaccessories);
        add = view.findViewById(R.id.addpost);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext() ,AdPost.class));
            }
        });

        property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext() , Property.class));
            }
        });

        accessroies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext() , Accessories.class));
            }
        });

        furniture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext() , Furniture.class));
            }
        });

        dresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext() , Dresses.class));
            }
        });

        vehicles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext() , Vehicles.class));
            }
        });

        electronics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext() , Electronics.class));
            }
        });




        Calendar calendarfordate = Calendar.getInstance();
        SimpleDateFormat currentdate = new SimpleDateFormat("dd-MMMM-yyyy");
        CurrentDate = currentdate.format(calendarfordate.getTime());
        Calendar calendarfortime = Calendar.getInstance();
        SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss");
        CurrentTime = currenttime.format(calendarfortime.getTime());
        randomname = CurrentTime + CurrentDate;
        mRecyclerView = view.findViewById(R.id.recyclerviewmain);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setNestedScrollingEnabled(true);

        // Inflate the layout for this fragment
        return view ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Query q = Postref.orderByChild("AddBy").equalTo(currentuserid);
        FirebaseRecyclerOptions options =
                new FirebaseRecyclerOptions.Builder<Post>()
                        .setQuery(Postref, Post.class)
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
                                    String address = dataSnapshot.child("Address").getValue().toString();
                                    holder.title.setText(title);
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
                                    holder.address.setText(address);
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