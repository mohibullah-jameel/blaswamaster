package com.example.rsp;
import android.accounts.Account;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.rsp.ui.Adds.AdsDetail;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.Constraints;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.rsp.ui.Adds.MyAds;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import de.hdodenhof.circleimageview.CircleImageView;
public class ManageAccount extends AppCompatActivity {
    private EditText et_Current_pw,et_New_pw,et_Full_nm,et_email_address;
    private Button btn_Confirm;
    DatabaseReference mRef;
    private SharedPreferences sharedPreferences;
    private  String cPass,nPass;
    private  String getPassword,getFullName,getEmailAddress,getNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageaccount);
        initViews();
        initListener();

    }
    private void initViews() {

        et_Current_pw=findViewById(R.id.current_password);
        et_New_pw=findViewById(R.id.new_password);
        et_Full_nm=findViewById(R.id.full_name);
        et_email_address=findViewById(R.id.email_address);
        btn_Confirm=findViewById(R.id.btnconfirm);
        sharedPreferences=getSharedPreferences(Constraints.AUTOFILL_HINT_USERNAME,MODE_PRIVATE);

    }

    private void initListener() {
        btn_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid()){
                    Update();
                }
            }
        });
    }
    private void Update(){
        cPass=et_Current_pw.getText().toString();
        nPass=et_New_pw.getText().toString();
        getPassword=sharedPreferences.getString(Constraints.AUTOFILL_HINT_PASSWORD,"");
        getFullName=sharedPreferences.getString(Constraints.AUTOFILL_HINT_NAME,"");
        getEmailAddress = sharedPreferences.getString(Constraints.AUTOFILL_HINT_EMAIL_ADDRESS,"");

        mRef=FirebaseDatabase.getInstance().getReference("User");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapShot:dataSnapshot.getChildren()){
                    User user=childDataSnapShot.getValue(User.class);

                    if (user.Number.equals(getNumber)&&user.Password.equals(cPass)){
                        mRef.child(user.Number).child("password").setValue(nPass);
                        Toast.makeText(ManageAccount.this,"Successfully Updated",Toast.LENGTH_LONG).show();
                    }
                    else if (!user.Password.equals(cPass)){
                        Snackbar.make(getWindow().getDecorView().getRootView(),"Current Paaword",Snackbar.LENGTH_LONG);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
private boolean isValid() {
    cPass = et_Current_pw.getText().toString();
    nPass = et_New_pw.getText().toString();
    if (cPass.equals("")) {
        et_Current_pw.setError(Constraints.AUTOFILL_HINT_PASSWORD);
        return false;
    }
    if (nPass.equals("")) {
        et_New_pw.setError(Constraints.AUTOFILL_HINT_PASSWORD);
        return false;
    }
    return true;
}

}