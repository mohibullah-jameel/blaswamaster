package com.example.rsp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rsp.R;

public class FurnitureListAdapter extends RecyclerView.Adapter<FurnitureListAdapter.Viewholder> {
    private FurnitureListData[] listdata2;

    public FurnitureListAdapter(FurnitureListData[] listdata2) {
        this.listdata2 = listdata2;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View listitem2= layoutInflater.inflate(R.layout.furniturelist_item,parent,false);
        Viewholder viewholder= new Viewholder(listitem2);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        final FurnitureListData myListData2=listdata2[position];
        holder.textView.setText(listdata2[position].getDescription());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"click on item:"+myListData2.getDescription(),Toast.LENGTH_LONG ).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata2.length;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        public TextView textView;
        public RelativeLayout relativeLayout;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            this.textView= itemView.findViewById(R.id.textview);
            this.relativeLayout= itemView.findViewById(R.id.relativelayout);


        }
    }
}
