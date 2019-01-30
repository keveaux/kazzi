package com.example.kelvincb.ikazi.Main.mainFragments.availableWorkers;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kelvincb.ikazi.Main.mainFragments.sendRequestPackage.sendRequest;
import com.example.kelvincb.ikazi.R;
import com.example.kelvincb.ikazi.mPicasso.PicassoClient;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class adapterClass extends BaseAdapter {

    Context c;
    ArrayList<getterSetterClass> availableWorkersList;



    public adapterClass(Context c, ArrayList<getterSetterClass> availableWorkersList) {
        this.c = c;
        this.availableWorkersList = availableWorkersList;
    }

    @Override
    public int getCount() {
        return availableWorkersList.size();
    }

    @Override
    public Object getItem(int position) {
        return availableWorkersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {






        if(convertView==null)
        {
            convertView= LayoutInflater.from(c).inflate(R.layout.row_model,parent,false);
        }

        TextView nametxt= convertView.findViewById(R.id.nametxt);
        TextView ratingtxt=convertView.findViewById(R.id.ratingtxt);
        TextView distancetxt=convertView.findViewById(R.id.distance);
        TextView skill_set=convertView.findViewById(R.id.skill_set);
        ImageView image=convertView.findViewById(R.id.worker_image);
//        Button book_worker_btn =convertView.findViewById(R.id.book_worker);

        Typeface font=Typeface.createFromAsset(c.getAssets(),"RobotoSlab-Light.ttf");
        ratingtxt.setTypeface(font);
        distancetxt.setTypeface(font);
        nametxt.setTypeface(font);
        skill_set.setTypeface(font);



        final getterSetterClass getterSetterClass= (getterSetterClass) this.getItem(position);

        nametxt.setText(getterSetterClass.getName());
        skill_set.setText(getterSetterClass.getSkillSet());
        int distance= (int) (getterSetterClass.getDistance() / 1000);
        distancetxt.setText(distance+" km away");
        double rating= Double.parseDouble(getterSetterClass.getRating());

        if(rating==0) {
            ratingtxt.setText("" + 0.0);
        }else {
            double numberofrates= Double.parseDouble(getterSetterClass.getNumberOfRates());
            double worker_rating=rating/numberofrates;
            DecimalFormat df = new DecimalFormat("#.##");
            ratingtxt.setText(""+df.format(worker_rating));
        }


        //fetch image from online using picasso
        PicassoClient.loadImage(getterSetterClass.getImageUrl(),image);



        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                sendRequest myFragment = new sendRequest();

                Bundle args = new Bundle();

                args.putString("id", getterSetterClass.getId());
                args.putString("name",getterSetterClass.getName());

                myFragment.setArguments(args);

                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, myFragment).addToBackStack(null).commit();


            }
        });

        return convertView;
    }
}
