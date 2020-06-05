package com.example.rsp;
import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import static java.security.AccessController.getContext;
public class NavigationDrawer extends AppCompatActivity {
    RecyclerView recyclerView ;
    GridLayoutManager gridLayoutManager ;
    int [] images ;
    RecyclerAdaptor recyclerAdaptor ;
    private TextView fullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigationdrawer);
        String [] rupees = {"RS 10000" ,"RS 2500" , "RS 3000" , "RS 6000" , "RS 55500" , "RS 10000" ,"RS 2500" , "RS 3000" , "RS 6000" , "RS 55500"
        , "RS 10000" ,"RS 2500" , "RS 3000" , "RS 6000" , "RS 55500" , "RS 10000" ,"RS 2500" , "RS 3000" , "RS 6000" , "RS 55500"};
        String [] des = {"Iphone" , "Iphone" , "Iphone" , "Iphone" , "Iphone" , "Iphone" , "Iphone" , "Iphone" , "Iphone" , "Iphone" ,
                "Iphone" , "Iphone" , "Iphone" , "Iphone" , "Iphone" , "Iphone" , "Iphone" , "Iphone" , "Iphone" , "Iphone"};
        int [] images = {R.drawable.log , R.drawable.log ,R.drawable.log  , R.drawable.log  , R.drawable.log , R.drawable.log , R.drawable.log ,R.drawable.log  , R.drawable.log  , R.drawable.log ,
                R.drawable.log , R.drawable.log ,R.drawable.log  , R.drawable.log  , R.drawable.log , R.drawable.log , R.drawable.log ,R.drawable.log  , R.drawable.log  , R.drawable.log};





        recyclerView = findViewById(R.id.recyclerview);
        fullname= (TextView) findViewById(R.id.fullname);
        fullname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NavigationDrawer.this, AdPost.class));
            }

        });


        recyclerView.hasFixedSize();
        gridLayoutManager = new GridLayoutManager(this , 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerAdaptor = new RecyclerAdaptor(rupees ,des , images , this);
        recyclerView.setAdapter(recyclerAdaptor);
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
//
//

}
