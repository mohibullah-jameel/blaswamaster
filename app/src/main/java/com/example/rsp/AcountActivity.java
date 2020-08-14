package com.example.rsp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class AcountActivity extends AppCompatActivity {

    EditText name , email ;
    FirebaseAuth firebaseAuth ;
    String currentuser ;
    DatabaseReference Useref;
    Button save ;
    String mail ;
    EditText cpass ,npass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acount);

        firebaseAuth = FirebaseAuth.getInstance();
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        save = findViewById(R.id.btnsave);
        cpass = findViewById(R.id.currentpassword);
        npass = findViewById(R.id.newpassword);

        currentuser = firebaseAuth.getCurrentUser().getUid();

        Useref = FirebaseDatabase.getInstance().getReference().child("User");

        Useref.child(currentuser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    String mail = dataSnapshot.child("EmailAddress").getValue().toString();
                    String n = dataSnapshot.child("FullName").getValue().toString();

                    name.setText(n);
                    email.setText(mail);
                    email.setEnabled(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mail = firebaseAuth.getCurrentUser().getEmail();
                ChangePassword();
                ChnageName();

            }
        });
    }

    private void ChnageName() {
        HashMap hashMap = new HashMap<>();
        hashMap.put("FullName" , name.getText().toString());
        Useref.child(currentuser).updateChildren(hashMap).isSuccessful();
    }

    private void ChangePassword(){
        class AsyncTaskUploadClass extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // Showing progress dialog at image upload time.
            }

            @Override
            protected String doInBackground(Void... voids) {


                AuthCredential credential = EmailAuthProvider.getCredential(mail,cpass.getText().toString());

                firebaseAuth.getCurrentUser().reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            firebaseAuth.getCurrentUser().updatePassword(npass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(!task.isSuccessful()){

                                        Toast.makeText(AcountActivity.this, "Some thing went wrong", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(AcountActivity.this, "changed",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }else {

                        }
                    }
                });
                return null;
            }

            @Override
            protected void onPostExecute(String string1) {
                super.onPostExecute(string1);
                // Dismiss the progress dialog after done uploading.
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
        AsyncTaskUploadClassOBJ.execute();
    }
}