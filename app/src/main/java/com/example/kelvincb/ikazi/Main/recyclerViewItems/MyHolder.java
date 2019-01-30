package com.example.kelvincb.ikazi.Main.recyclerViewItems;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kelvincb.ikazi.R;

public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //OUR VIEWS
    ImageView img;
    TextView nameTxt;

    ItemClickListener itemClickListener;

    public MyHolder(View itemView) {
        super(itemView);

        this.img=  itemView.findViewById(R.id.playerImage);
        this.nameTxt=  itemView.findViewById(R.id.nameTxt);



        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v,getLayoutPosition());

    }

    public void setItemClickListener(ItemClickListener ic)
    {
        this.itemClickListener=ic;
    }
}
