package com.example.rsp;
import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.security.AccessController.getContext;
public class NavigationDrawer extends AppCompatActivity {
    RecyclerView mRecyclerView;
    GridLayoutManager gridLayoutManager;
    int[] images;
    RecyclerAdaptor mRecyclerAdaptor;
    private TextView fullname;
    private FirebaseAuth mAuth;
    private DatabaseReference Postref;
    String CurrentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigationdrawer);
        mAuth = FirebaseAuth.getInstance();
        CurrentUserId = mAuth.getCurrentUser().getUid();
        Postref = FirebaseDatabase.getInstance().getReference().child("Post");

        fullname = (TextView) findViewById(R.id.fullname);
        fullname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NavigationDrawer.this, AdPost.class));
            }

        });
        mRecyclerView = findViewById(R.id.recyclerviewmain);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(true);

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions options =
                new FirebaseRecyclerOptions.Builder<Post>()
                        .setQuery(Postref, Post.class)
                        .build();
        FirebaseRecyclerAdapter<Post , PostViewholder> firebaseRecyclerOptions =
                new FirebaseRecyclerAdapter<Post, PostViewholder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final PostViewholder holder, int position, @NonNull Post model) {
                        final String postid = getRef(position).getKey();
                        Postref.child(postid).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    String title = dataSnapshot.child("Title").getValue().toString();
                                    String des = dataSnapshot.child("Description").getValue().toString();
                                    String price = dataSnapshot.child("Price").getValue().toString();

                                    holder.title.setText(title);
                                    holder.description.setText(des);
                                    holder.rupees.setText("Rs " + price);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
//                        int top = dptopx(3);
//                        int left = dptopx(3);
//                        int right = dptopx(3);
//                        int bottom = dptopx(3);
//
//                        int spancount = 2 ;
//
//                        boolean isfirst2items = position < spancount ;
//                        boolean isislast2items = position > getItemCount() - spancount ;
//
//                        if (isfirst2items)
//                        {
//                            top = dptopx(3);
//                        }
//                        if (isislast2items)
//                        {
//                            bottom = dptopx(3);
//                        }
//
//                        boolean isleftside = (position+1) % spancount != 0 ;
//                        boolean isrightside = !isleftside ;
//
//                        if (isleftside)
//                        {
//                            right = dptopx(3);
//                        }
//                        if (isrightside)
//                        {
//                            left = dptopx(3);
//                        }
//
//
//
//                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.constraintLayout.getLayoutParams();
//                        layoutParams.setMargins(left , top , right , bottom);
//                        holder.constraintLayout.setLayoutParams(layoutParams);
//
//
//
//
//                    }
//
//                    private int dptopx ( int dp )
//                    {
//                        float px = dp * getResources().getDisplayMetrics().density;
//                        return (int) px ;
//                    }
                    }


                    @NonNull
                    @Override
                    public PostViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview , parent , false);
                        PostViewholder postViewholder = new PostViewholder(view);
                        return postViewholder;
                    }
                };
        mRecyclerView.setAdapter(firebaseRecyclerOptions);
        firebaseRecyclerOptions.startListening();

     }



    }

    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//
//        if (requestCode == Gallery_pick && resultCode == RESULT_OK && data != null)
//        {
//
//            Uri resuluri = data.getData();
//
//            profileimage.setImageURI(resuluri);
//
//            progressDialog.setTitle("Setting Profile Picture");
//            progressDialog.setMessage("Please Wait.....");
//            progressDialog.setCanceledOnTouchOutside(false);
//            progressDialog.show();
//
//            final StorageReference filepath = profileimagesref.child(Currentuser + ".jpg");
//
//            filepath.putFile(resuluri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    final Task<Uri> firebaseUri = taskSnapshot.getStorage().getDownloadUrl();
//                    firebaseUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            downloadUrl = uri.toString();
//                            userref.child(Currentuser).child("Profile Image").setValue(downloadUrl).addOnCompleteListener(
//
//                                    new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if(task.isSuccessful())
//                                            {
//                                                progressDialog.dismiss();
//                                                Toast.makeText(NavigationDrawer.this, "Image uploaded Successfully", Toast.LENGTH_SHORT).show();
//                                            }
//                                        }
//                                    }
//                            );
//
//
//
//                        }
//                    });
//
//                }
//            });
//        }







