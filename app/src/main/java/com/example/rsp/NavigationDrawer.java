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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
import android.view.View;
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
    LinearLayoutManager mLinearLayoutManager;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDataReference;
    private FirebaseAuth mAuth;
    private DatabaseReference Postref;
    String CurrentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigationdrawer);
        mAuth = FirebaseAuth.getInstance();
        CurrentUserId = mAuth.getCurrentUser().getUid();
        Postref = FirebaseDatabase.getInstance().getReference().child("Posts").child(CurrentUserId);
        String[] rupees = {"RS 10000", "RS 2500", "RS 3000", "RS 6000", "RS 55500", "RS 10000", "RS 2500", "RS 3000", "RS 6000", "RS 55500"
                , "RS 10000", "RS 2500", "RS 3000", "RS 6000", "RS 55500", "RS 10000", "RS 2500", "RS 3000", "RS 6000", "RS 55500"};
        String[] des = {"Iphone", "Iphone", "Iphone", "Iphone", "Iphone", "Iphone", "Iphone", "Iphone", "Iphone", "Iphone",
                "Iphone", "Iphone", "Iphone", "Iphone", "Iphone", "Iphone", "Iphone", "Iphone", "Iphone", "Iphone"};
        int[] images = {R.drawable.log, R.drawable.log, R.drawable.log, R.drawable.log, R.drawable.log, R.drawable.log, R.drawable.log, R.drawable.log, R.drawable.log, R.drawable.log,
                R.drawable.log, R.drawable.log, R.drawable.log, R.drawable.log, R.drawable.log, R.drawable.log, R.drawable.log, R.drawable.log, R.drawable.log, R.drawable.log};

        fullname = (TextView) findViewById(R.id.fullname);
        fullname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NavigationDrawer.this, AdPost.class));
            }

        });

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.hasFixedSize();
        gridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(true);

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Post>().setQuery(Postref, Post.class).build();
         FirebaseRecyclerAdapter<Post , PostViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Post, PostViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final PostViewHolder holder, final int position, @NonNull Post model) {

                        final String Postid = getRef(position).getKey();

                            @Override

                            public void onCancelled(@NonNull DatabaseError databaseError) {

 

                            }

                        });

                        postref.child(postid).addValueEventListener(new ValueEventListener() {

                            @Override

                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if(dataSnapshot.exists())

                                {

                                    if (dataSnapshot.hasChild("Name") && dataSnapshot.hasChild("Date") && dataSnapshot.hasChild("PostBy")

                                            && dataSnapshot.hasChild("Time") && dataSnapshot.hasChild("Title")

                                            && dataSnapshot.hasChild("Description") && dataSnapshot.hasChild("PostImage")

                                            && dataSnapshot.hasChild("User_profile_image") && dataSnapshot.hasChild("Postfile")

                                            && dataSnapshot.hasChild("Category"))

 

                                    {

                                        String Username = dataSnapshot.child("Name").getValue().toString();

                                        String Date = dataSnapshot.child("Date").getValue().toString();

                                        final String postby = dataSnapshot.child("PostBy").getValue().toString();

                                        String Time = dataSnapshot.child("Time").getValue().toString();

                                        String Title = dataSnapshot.child("Title").getValue().toString();

                                        String description = dataSnapshot.child("Description").getValue().toString();

                                        String Image = dataSnapshot.child("PostImage").getValue().toString();

                                        String profileimage = dataSnapshot.child("User_profile_image").getValue().toString();

                                        final String document = dataSnapshot.child("Postfile").getValue().toString();

                                        String cat = dataSnapshot.child("Category").getValue().toString();

 

                                        holder.addcat.setText(cat);

                                        if (Title.isEmpty())

                                        {

                                            holder.title.setVisibility(View.GONE);

                                            holder.Username.setText(Username);

                                            holder.description.setText(description);

                                            holder.Date.setText(Date);

                                            holder.Time.setText(Time);

                                            holder.Count.setText(Integer.toString(countcomments));

                                            Picasso.get().load(Image).into(holder.imageView);

                                            Picasso.get().load(profileimage).into(holder.UserImage);

 

                                        }

                                        if (document.isEmpty())

                                        {

                                            holder.doc.setVisibility(View.GONE);

                                            holder.Username.setText(Username);

                                            holder.Date.setText(Date);

                                            holder.Time.setText(Time);

                                            holder.Count.setText(Integer.toString(countcomments));

                                            holder.title.setText(Title);

                                            Picasso.get().load(profileimage).into(holder.UserImage);

                                        }

                                        if (description.isEmpty())

                                        {

 

                                            holder.description.setVisibility(View.GONE);

                                            holder.Username.setText(Username);

                                            holder.Date.setText(Date);

                                            holder.Time.setText(Time);

                                            holder.Count.setText(Integer.toString(countcomments));

                                            holder.title.setText(Title);

                                            Picasso.get().load(Image).into(holder.imageView);

                                            Picasso.get().load(profileimage).into(holder.UserImage);

 

 

                                        }

                                        if (description.isEmpty() && Title.isEmpty() )

                                        {

                                            holder.description.setVisibility(View.GONE);

                                            holder.title.setVisibility(View.GONE);

                                            holder.Username.setText(Username);

                                            holder.Date.setText(Date);

                                            holder.Count.setText(Integer.toString(countcomments));

                                            holder.Time.setText(Time);

                                            Picasso.get().load(Image).into(holder.imageView);

                                            Picasso.get().load(profileimage).into(holder.UserImage);

                                        }

                                        if (Image.isEmpty())

                                        {

                                            holder.imageView.setVisibility(View.GONE);

                                            holder.Username.setText(Username);

                                            holder.description.setText(description);

                                            holder.Date.setText(Date);

                                            holder.Time.setText(Time);

                                            holder.title.setText(Title);

                                            holder.Count.setText(Integer.toString(countcomments));

                                            Picasso.get().load(profileimage).into(holder.UserImage);

                                        }

                                        else

                                        {

                                            holder.Username.setText(Username);

                                            holder.description.setText(description);

                                            holder.Date.setText(Date);

                                            holder.Time.setText(Time);

                                            holder.Count.setText(Integer.toString(countcomments));

                                            holder.title.setText(Title);

                                            Picasso.get().load(Image).into(holder.imageView);

                                            Picasso.get().load(profileimage).into(holder.UserImage);

                                        }

                                        holder.doc.setOnClickListener(new View.OnClickListener() {

                                            @Override

                                            public void onClick(View v) {

                                                Intent intent = new Intent(Intent.ACTION_VIEW , Uri.parse(document));

                                                holder.doc.getContext().startActivity(intent);

                                            }

                                        });

                                        holder.dots.setOnClickListener(new View.OnClickListener() {

                                            @Override

                                            public void onClick(View v) {

                                                ShowDailog(postid , postby);

 

                                            }

                                        });

 

                                        holder.commenttext.setOnClickListener(new View.OnClickListener() {

                                            @Override

                                            public void onClick(View v) {

                                                sendusertocommentactivity(postid);

                                            }

                                        });

                                        holder.UserImage.setOnClickListener(new View.OnClickListener() {

                                            @Override

                                            public void onClick(View v) {

                                                Intent intent = new Intent(getContext() , UserProfileActivity.class);

                                                intent.putExtra("UserKey" , postby);

                                                startActivity(intent);

                                            }

                                        });

 

                                        if (Image.isEmpty())

                                        {

                                            holder.itemView.setOnClickListener(new View.OnClickListener() {

                                                @Override

                                                public void onClick(View v) {

                                                    Intent onpostviewclick = new Intent(getContext() , CommentsActivity.class);

                                                    onpostviewclick.putExtra("Postkey" , postid);

                                                    startActivity(onpostviewclick);

                                                }

                                            });

                                        }

                                        else

                                        {

                                            holder.itemView.setOnClickListener(new View.OnClickListener() {

                                                @Override

                                                public void onClick(View v) {

                                                    Intent onpostviewclick = new Intent(getContext() , Onpostviewclick.class);

                                                    onpostviewclick.putExtra("postid" , postid);

                                                    startActivity(onpostviewclick);

                                                }

                                            });

                                        }

 

 

 

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

                    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_user_post ,viewGroup , false);

                        PostViewHolder viewHolder = new PostViewHolder(view);

                        return viewHolder;

                    }

                };

 

        recyclerView.setAdapter(firebaseRecyclerAdapter);

 

        firebaseRecyclerAdapter.startListening();

 

    }
    public static class PostViewHolder extends RecyclerView.ViewHolder {
        CircleImageView UserImage;
        TextView Username, Date, Time, title, description, commenttext, Count, addcat;
        ImageView imageView, dots, Commentimage;
        LinearLayout doc;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private void requestStoragePermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            requestCameraPermission();
                            Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
//                            Intent Gallery = new Intent();
//                            Gallery.setAction(Intent.ACTION_GET_CONTENT);
//                            Gallery.setType("image/*");
//                            startActivityForResult(Gallery , Gallery_pick);
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void requestCameraPermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // permission is granted

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(NavigationDrawer.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }


    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 100);
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



    }



