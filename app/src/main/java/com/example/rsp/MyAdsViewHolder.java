package com.example.rsp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdsViewHolder extends RecyclerView.ViewHolder {
    public TextView rupees , title ,description;
    public ImageView imageView ;
    public MyAdsViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.image);
        rupees = itemView.findViewById(R.id.textPrice);
        description=itemView.findViewById(R.id.textDesription);
        title = itemView.findViewById(R.id.textTitle);

    }
}
