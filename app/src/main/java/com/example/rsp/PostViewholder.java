package com.example.rsp;

import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

class PostViewholder extends RecyclerView.ViewHolder {
    TextView rupees , description , title,ownernername,owneraddress,mobilenumber,parameter;
    ConstraintLayout constraintLayout ;
    ImageView imageView ;
    public PostViewholder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.image);
        constraintLayout = itemView.findViewById(R.id.one);
        rupees = itemView.findViewById(R.id.rupees);
        description = itemView.findViewById(R.id.description);
        title = itemView.findViewById(R.id.title);
        parameter = itemView.findViewById(R.id.sp_parameter);
        ownernername = itemView.findViewById(R.id.txt_ownername);
        owneraddress= itemView.findViewById(R.id.txt_owneraddress);
        mobilenumber = itemView.findViewById(R.id.txt_mobileno);





    }
}
