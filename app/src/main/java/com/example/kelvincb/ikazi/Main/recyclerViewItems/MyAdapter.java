package com.example.kelvincb.ikazi.Main.recyclerViewItems;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.kelvincb.ikazi.Main.mainFragments.availableWorkers.availableWorkersFragment;
import com.example.kelvincb.ikazi.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> implements Filterable{

    Context c;
    ArrayList<Worker> workers,filterList;
    CustomFilter filter;

    public MyAdapter(Context ctx,ArrayList<Worker> workers)
    {
        this.c=ctx;
        this.workers = workers;
        this.filterList= workers;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //CONVERT XML TO VIEW ONBJ
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.model,null);

        //HOLDER
        MyHolder holder=new MyHolder(v);

        return holder;
    }

    //DATA BOUND TO VIEWS
    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {

        //BIND DATA
        holder.nameTxt.setText(workers.get(position).getName());
        holder.img.setImageResource(workers.get(position).getImg());

        Typeface font=Typeface.createFromAsset(c.getAssets(),"Roboto-Bold.ttf");
        holder.nameTxt.setTypeface(font);


        //IMPLEMENT CLICK LISTENET
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                availableWorkersFragment myFragment = new availableWorkersFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, myFragment).addToBackStack(null).commit();
//                Toast.makeText(mContext, ""+workers.getName(), Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putString( "occupation", workers.get(position).getName());
                bundle.putString("EXTRA","first");
                // set Fragmentclass Arguments
                myFragment.setArguments(bundle);            }
        });

    }

    //GET TOTAL NUM OF PLAYERS
    @Override
    public int getItemCount() {

        return workers.size();
    }

    //RETURN FILTER OBJ
    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter=new CustomFilter(filterList,this);
        }

        return filter;
    }
}


