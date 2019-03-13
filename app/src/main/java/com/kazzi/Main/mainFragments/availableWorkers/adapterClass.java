package com.kazzi.Main.mainFragments.availableWorkers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.kazzi.Main.mainFragments.sendRequestPackage.sendRequestActivity;
import com.kazzi.Main.mainFragments.availableWorkers.WorkerProfile.*;
import com.kazzi.R;
import com.kazzi.mPicasso.PicassoClient;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class adapterClass extends BaseAdapter {

    Context c;
    ArrayList<getterSetterClass> availableWorkersList;
    double worker_rating;



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
        ImageButton viewprofile=convertView.findViewById(R.id.viewprofile);
//        Button book_worker_btn =convertView.findViewById(R.id.book_worker);





        Typeface bold_font=Typeface.createFromAsset(c.getAssets(),"Roboto-Bold.ttf");
        ratingtxt.setTypeface(bold_font);
        distancetxt.setTypeface(bold_font);
        nametxt.setTypeface(bold_font);


        Typeface font=Typeface.createFromAsset(c.getAssets(),"Roboto-Light.ttf");
        skill_set.setTypeface(font);




        final getterSetterClass getterSetterClass= (getterSetterClass) this.getItem(position);

        nametxt.setText(" "+getterSetterClass.getName());
        skill_set.setText(getterSetterClass.getSkillSet());
        int distance= (int) (getterSetterClass.getDistance() / 1000);
        distancetxt.setText(distance+" km away");
        double rating= Double.parseDouble(getterSetterClass.getRating());

        if(rating==0) {
            ratingtxt.setText("" + 0.0);
        }else {
            double numberofrates= Double.parseDouble(getterSetterClass.getNumberOfRates());
            worker_rating=rating/numberofrates;
            DecimalFormat df = new DecimalFormat("#.##");
            ratingtxt.setText(""+df.format(worker_rating));
        }




        //fetch image from online using picasso
        PicassoClient.loadImage(getterSetterClass.getImageUrl(),image);

        DecimalFormat df = new DecimalFormat("#.##");

        final String ratingstr= String.valueOf(df.format(worker_rating));

        viewprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(c,WorkerProfileActivity.class);
                intent.putExtra("EXTRA","first");
                intent.putExtra("id",getterSetterClass.getId());
                intent.putExtra("name",getterSetterClass.getName());
                intent.putExtra("skill",getterSetterClass.getSkillSet());
                intent.putExtra("occupation",getterSetterClass.getOccupation());
                intent.putExtra("url",getterSetterClass.getImageUrl());
                intent.putExtra("rating", ratingstr);
                intent.putExtra("url_one", getterSetterClass.getWork_done_one());
                intent.putExtra("url_two", getterSetterClass.getWork_done_two());
                intent.putExtra("url_three", getterSetterClass.getWork_done_three());
                intent.putExtra("facebook", getterSetterClass.getFacebook());
                intent.putExtra("twitter", getterSetterClass.getTwitter());
                intent.putExtra("linkedin", getterSetterClass.getLinkedin());
                intent.putExtra("github", getterSetterClass.getGithub());
                intent.putExtra("email", getterSetterClass.getEmail());



                c.startActivity(intent);

            }
        });


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i=new Intent(c,sendRequestActivity.class);
                i.putExtra("EXTRA","first");
                i.putExtra("id",getterSetterClass.getId());
                i.putExtra("name",getterSetterClass.getName());
                i.putExtra("skill",getterSetterClass.getSkillSet());
                i.putExtra("occupation",getterSetterClass.getOccupation());
                i.putExtra("location",getterSetterClass.getLocation());
                i.putExtra("token",getterSetterClass.getToken());
                i.putExtra("url",getterSetterClass.getImageUrl());

                c.startActivity(i);


            }
        });

        return convertView;
    }
}
