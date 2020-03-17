package com.example.rps;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rsp.MyListData;
import com.example.rsp.R;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.Viewholder> {
    private MyListData[] listdata;
    public MyListAdapter(MyListData[] listdata){
        this.listdata=listdata;
    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View listitem= layoutInflater.inflate(R.layout.list_item,parent,false);
        Viewholder viewholder= new Viewholder(listitem);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        final MyListData myListData=listdata[position];
        holder.textView.setText(listdata[position].getDescription());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"click on item:"+myListData.getDescription(),Toast.LENGTH_LONG ).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public RelativeLayout relativeLayout;


        public Viewholder(@NonNull View itemView) {
            super(itemView);
            this.textView= itemView.findViewById(R.id.textview);
            this.relativeLayout= itemView.findViewById(R.id.relativelayout);


        }
    }
}
