
package com.example.rsp.ui.Adds;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.FrameLayout;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.ActionBarDrawerToggle;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.GridLayoutManager;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import com.bumptech.glide.Glide;

        import com.example.rsp.MyAdsViewHolder;
        import com.example.rsp.NavigationDrawer;
        import com.example.rsp.Post;
        import com.example.rsp.PostViewholder;
        import com.example.rsp.R;
        import com.example.rsp.RecyclerAdaptor;
        import com.firebase.ui.database.FirebaseRecyclerAdapter;
        import com.firebase.ui.database.FirebaseRecyclerOptions;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.Query;
        import com.google.firebase.database.ValueEventListener;

        import java.util.HashMap;

public class MyAds extends AppCompatActivity {

    RecyclerView mRecyclerView;
    GridLayoutManager gridLayoutManager;
    RecyclerAdaptor mRecyclerAdaptor;
    private FirebaseAuth mAuth;
    private DatabaseReference Postref , hisref ;
    String CurrentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myads);
        mRecyclerView = findViewById(R.id.recyclerView);
        mAuth = FirebaseAuth.getInstance();
        CurrentUserId = mAuth.getCurrentUser().getUid();
        Postref = FirebaseDatabase.getInstance().getReference().child("Post");
        hisref = FirebaseDatabase.getInstance().getReference().child("History");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(true);
    }
    @Override
    public void onStart() {
        super.onStart();
        Query q = Postref.orderByChild("Addby").equalTo(CurrentUserId);
        FirebaseRecyclerOptions options =
                new FirebaseRecyclerOptions.Builder<Post>()
                        .setQuery(q, Post.class)
                        .build();
        FirebaseRecyclerAdapter<Post, MyAdsViewHolder> firebaseRecyclerOptions =
                new FirebaseRecyclerAdapter<Post, MyAdsViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final MyAdsViewHolder holder, final int position, @NonNull Post model) {
                        final String postid = getRef(position).getKey();
                        Postref.child(postid).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    String title = dataSnapshot.child("Title").getValue().toString();
                                    String description = dataSnapshot.child("Description").getValue().toString();
                                    String price = dataSnapshot.child("Price").getValue().toString();

                                    holder.title.setText(title);
                                    holder.rupees.setText("Rs " + price);
                                    if (dataSnapshot.hasChild("Image"))
                                    {
                                        String image = dataSnapshot.child("Image").getValue().toString();
                                        Glide.with(getApplicationContext()).load(image).into(holder.imageView) ;
                                    }
                                    holder.imageView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(MyAds.this , AdsDetail.class);
                                            intent.putExtra("ID" , postid);
                                            startActivity(intent);

                                        }
                                    });



                                    String a = dataSnapshot.child("Available").getValue().toString();

                                    if (a.equals("No"))
                                    {
                                        holder.btn.setText("Rent In");
                                    }
                                    else
                                    {
                                        holder.btn.setText("Rent Out");
                                    }

                                    if (holder.btn.getText().equals("Rent In"))
                                    {
                                        holder.btn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                final HashMap hashMap = new HashMap();
                                                hashMap.put("Available" , "Yes");
                                                Postref.child(postid).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                                    @Override
                                                    public void onComplete(@NonNull Task task) {
                                                        if (task.isSuccessful())
                                                        {
                                                            Toast.makeText(MyAds.this, "Done", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });


                                            }
                                        });
                                    }
                                    else
                                    {
                                        holder.btn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                HashMap hashMap = new HashMap();
                                                hashMap.put("Available" , "No");
                                                Postref.child(postid).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                                    @Override
                                                    public void onComplete(@NonNull Task task) {
                                                        if (task.isSuccessful())
                                                        {
                                                            HashMap hashMap = new HashMap();
                                                            hashMap.put("Rent Out" , "Yes" );

                                                            hisref.child(CurrentUserId).child(postid).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                                                @Override
                                                                public void onComplete(@NonNull Task task) {
                                                                    Toast.makeText(MyAds.this, "Done", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                        }

                                                    }
                                                });
                                            }
                                        });
                                    }

                                    holder.del.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Postref.child(postid).removeValue();
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
                    public MyAdsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myadslist_item, parent, false);
                        MyAdsViewHolder nabila = new MyAdsViewHolder(view);
                        return nabila ;
                    }
                };
        mRecyclerView.setAdapter(firebaseRecyclerOptions);
        firebaseRecyclerOptions.startListening();

    }

}
