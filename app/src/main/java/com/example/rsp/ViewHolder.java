package com.example.rsp;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    View mview;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        mview=itemView;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view,getAdapterPosition());
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListener.onItemLongClick(view,getAdapterPosition());
                return true;
            }


        });
    }

public void setOnDetails(Context ctx,String title, String description, String ownername, String owneraddress, String mobilenumber, String category, String subcategory, String condition, String price) {
    TextView txtTitle = mview.findViewById(R.id.txt_title);
    TextView  txtDescription = mview.findViewById(R.id.txt_description);
    TextView txtOwnername = mview .findViewById(R.id.txt_ownername);
    TextView txtOwneraddress=mview .findViewById(R.id.txt_owneraddress);
     TextView txtMobilenumber=mview .findViewById(R.id.txt_mobileno);
    Spinner sp_category=mview. findViewById(R.id.sp_category);
    Spinner sp_subcategory=mview. findViewById(R.id.sp_subcategory);
    Spinner sp_condition=mview. findViewById(R.id.sp_condition);
    Spinner sp_price=mview.findViewById(R.id.sp_price);
    txtTitle.setText(title);
    txtTitle.setText(description);
    txtTitle.setText(ownername);
    txtTitle.setText(owneraddress);
    txtTitle.setText(mobilenumber);
    sp_category.setSelection(Integer.parseInt(category));
    sp_subcategory.setSelection(Integer.parseInt(subcategory));
    sp_condition.setSelection(Integer.parseInt(condition));
    sp_price.setSelection(Integer.parseInt(price));


    }
    private ViewHolder.ClickListener mClickListener;
    public  interface  ClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }
    public void setOnClickListener(ViewHolder.ClickListener clickListener){
        mClickListener=clickListener;
    }

}
