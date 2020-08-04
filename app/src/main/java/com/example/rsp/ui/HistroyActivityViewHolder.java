package com.example.rsp.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rsp.R;

public class HistroyActivityViewHolder extends RecyclerView.ViewHolder {
    public TextView rupees , title ;
    public ImageView imageView ;
    public HistroyActivityViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.ItemImage);
        rupees = itemView.findViewById(R.id.Itemrupees);
        title = itemView.findViewById(R.id.tvTitle);

    }
}
