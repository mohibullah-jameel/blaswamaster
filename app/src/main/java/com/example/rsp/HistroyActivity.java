package com.example.rsp;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.GridLayoutManager;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;
        import com.bumptech.glide.Glide;
        import com.example.rsp.ui.Adds.AdsDetail;
        import com.example.rsp.ui.HistroyActivityViewHolder;
        import com.firebase.ui.database.FirebaseRecyclerAdapter;
        import com.firebase.ui.database.FirebaseRecyclerOptions;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.Query;
        import com.google.firebase.database.ValueEventListener;

public class  HistroyActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    GridLayoutManager gridLayoutManager;
    RecyclerAdaptor mRecyclerAdaptor;
    private FirebaseAuth mAuth;
    private DatabaseReference Postref ;
    String CurrentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histroy);
        mRecyclerView = findViewById(R.id.recyclerView);
        mAuth = FirebaseAuth.getInstance();
        CurrentUserId = mAuth.getCurrentUser().getUid();
        Postref = FirebaseDatabase.getInstance().getReference().child("Post");
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
        FirebaseRecyclerAdapter<Post, HistroyActivityViewHolder> firebaseRecyclerOptions =
                new FirebaseRecyclerAdapter<Post, HistroyActivityViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final HistroyActivityViewHolder holder, int position, @NonNull Post model) {
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
                                            Intent intent = new Intent(HistroyActivity.this , AdsDetail.class);
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
                    public HistroyActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_history_item, parent, false);
                        HistroyActivityViewHolder nabila = new HistroyActivityViewHolder(view);
                        return nabila ;
                    }
                };
        mRecyclerView.setAdapter(firebaseRecyclerOptions);
        firebaseRecyclerOptions.startListening();

    }

}
