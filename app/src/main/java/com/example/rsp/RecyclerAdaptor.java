package com.example.rsp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class RecyclerAdaptor extends RecyclerView.Adapter<RecyclerAdaptor.Viewholder> {
    private String [] price ;
    private String [] descriptions ;
    private int [] images ;
    private Context context ;

    public RecyclerAdaptor(String[] price, String[] descriptions, int[] images, Context context) {
        this.price = price;
        this.descriptions = descriptions;
        this.images = images;
        this.context = context;
    }




    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview , parent , false);
        Viewholder viewholder = new Viewholder(view);
        return viewholder ;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        holder.rupees.setText(price[position]);
        holder.descerip.setText(descriptions[position]);
        Picasso.get().load(images[position]).into(holder.imageView);
        int margin = dptopx(12);

        int top = dptopx(3);
        int left = dptopx(3);
        int right = dptopx(3);
        int bottom = dptopx(3);

        int spancount = 2 ;

        boolean isfirst2items = position < spancount ;
        boolean isislast2items = position > getItemCount() - spancount ;

        if (isfirst2items)
        {
            top = dptopx(3);
        }
        if (isislast2items)
        {
            bottom = dptopx(3);
        }

        boolean isleftside = (position+1) % spancount != 0 ;
        boolean isrightside = !isleftside ;

        if (isleftside)
        {
            right = dptopx(3);
        }
        if (isrightside)
        {
            left = dptopx(3);
        }



        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.constraintLayout.getLayoutParams();
        layoutParams.setMargins(left , top , right , bottom);
        holder.constraintLayout.setLayoutParams(layoutParams);




    }

    private int dptopx ( int dp )
    {
        float px = dp * context.getResources().getDisplayMetrics().density;
        return (int) px ;
    }



    @Override
    public int getItemCount() {
        return price.length;
    }

    public static class  Viewholder extends RecyclerView.ViewHolder {
        ImageView imageView ;
        TextView rupees , descerip ;
        ConstraintLayout constraintLayout ;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.one);
            imageView = itemView.findViewById(R.id.image);
            rupees = itemView.findViewById(R.id.rupees);
            descerip = itemView.findViewById(R.id.description);
        }
    }
}
