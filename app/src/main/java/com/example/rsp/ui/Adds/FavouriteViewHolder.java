package com.example.rsp.ui.Adds;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rsp.R;

public class FavouriteViewHolder extends RecyclerView.ViewHolder {
    public TextView rupees , title ,description;
    public ImageView imageView ;


    public FavouriteViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.image);
        rupees = itemView.findViewById(R.id.textPrice);
        description=itemView.findViewById(R.id.textDesription);
        title = itemView.findViewById(R.id.textTitle);

    }
}
