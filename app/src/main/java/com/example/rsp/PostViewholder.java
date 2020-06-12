package com.example.rsp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

class PostViewholder extends RecyclerView.ViewHolder {
    TextView rupees , description , title;
    ConstraintLayout constraintLayout ;
    public PostViewholder(@NonNull View itemView) {
        super(itemView);
        constraintLayout = itemView.findViewById(R.id.one);
        rupees = itemView.findViewById(R.id.rupees);
        description = itemView.findViewById(R.id.description);
        title = itemView.findViewById(R.id.title);
    }
}
