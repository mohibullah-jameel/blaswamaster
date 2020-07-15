package com.example.rsp;

import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class PostViewholder extends RecyclerView.ViewHolder {
    public TextView rupees , description , title,ownernername,owneraddress,mobilenumber;
    public ConstraintLayout constraintLayout ;
    public ImageView imageView ;
   public
   TextView availble ;
    public PostViewholder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.image);
        availble = itemView.findViewById(R.id.isavaialble);
        constraintLayout = itemView.findViewById(R.id.one);
        rupees = itemView.findViewById(R.id.rupees);
        description = itemView.findViewById(R.id.description);
        title = itemView.findViewById(R.id.title);
        ownernername = itemView.findViewById(R.id.txt_ownername);
        owneraddress= itemView.findViewById(R.id.txt_owneraddress);
        mobilenumber = itemView.findViewById(R.id.txt_mobileno);





    }
}
