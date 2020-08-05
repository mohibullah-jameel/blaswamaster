package com.example.rsp.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rsp.Fragments.CategoryActivity;
import com.example.rsp.R;

public class DressesListAdapter extends RecyclerView.Adapter<DressesListAdapter.Viewholder> {
   private DressesListData[] listData;
   Context context ;

    public DressesListAdapter(DressesListData[] listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View dresseslist_item= layoutInflater.inflate(R.layout.dresseslist_item,parent,false);

        Viewholder viewholder= new Viewholder(dresseslist_item);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
       final DressesListData DressesListData=listData[position];
       holder.textView.setText(listData[position].getDescription());
       holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(context , CategoryActivity.class);
               intent.putExtra("cat" , DressesListData.getDescription());
               context.startActivity(intent);;
               Toast.makeText(view.getContext(),"click on item:"+DressesListData.getDescription(),Toast.LENGTH_LONG ).show();

          }
       });
    }

    @Override
    public int getItemCount() {
        return listData.length;
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
