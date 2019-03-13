package com.kazzi.Main.mainFragments.userHistory.processedRequestPackage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kazzi.R;
import com.kazzi.RequestRejected;
import com.kazzi.mPicasso.PicassoClient;

import java.util.ArrayList;

public class processedAdapterClass extends BaseAdapter {

    Context c;
    ArrayList<processedGetterSetters> requestList;

    public processedAdapterClass(Context c, ArrayList<processedGetterSetters> requestList) {
        this.c = c;
        this.requestList = requestList;
    }

    @Override
    public int getCount() {
        return requestList.size();
    }

    @Override
    public Object getItem(int position) {
        return requestList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null)
        {
            convertView= LayoutInflater.from(c).inflate(R.layout.processed_row_model,parent,false);
        }

        TextView nametxt= convertView.findViewById(R.id.processed_nametxt);
        TextView occupation=convertView.findViewById(R.id.processed_occupation);
        TextView processeddate=convertView.findViewById(R.id.processed_date);
        ImageView image=convertView.findViewById(R.id.processed_worker_image);
        TextView status=convertView.findViewById(R.id.processed_status);

        Typeface font=Typeface.createFromAsset(c.getAssets(),"Roboto-Light.ttf");
        occupation.setTypeface(font);
        nametxt.setTypeface(font);
        processeddate.setTypeface(font);
        status.setTypeface(font);



        final processedGetterSetters getterSetterClass= (processedGetterSetters) this.getItem(position);

        nametxt.setText(getterSetterClass.getWorker_name());
        occupation.setText(getterSetterClass.getOccupation());
        processeddate.setText(getterSetterClass.getDaterequesthandled());

        //fetch image from online using picasso
        PicassoClient.loadImage(getterSetterClass.getImageUrl(),image);


        if(getterSetterClass.getStatus().equals("0")){
            status.setText("REJECTED ");
            status.setTextColor(Color.parseColor("#FFFFFF"));
            status.setBackgroundResource(R.drawable.rounde_edges_denied);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(c,RequestRejected.class);
                    c.startActivity(intent);
                }
            });




        }else {
            status.setText("ACCEPTED ");
            status.setBackgroundResource(R.drawable.rounded_edges_accepted);
            status.setTextColor(Color.parseColor("#FFFFFF"));

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String worker_name=getterSetterClass.getWorker_name();
                    String occupation=getterSetterClass.getOccupation();
                    String date=getterSetterClass.getMdate();
                    String time=getterSetterClass.getMtime();
                    String jobDescription=getterSetterClass.getJobDescription();
                    String worker_pno=getterSetterClass.getWorker_phone_number();
                    String workerId=getterSetterClass.getWorkerId();
                    String skillset=getterSetterClass.getSkillset();
                    String user_name=getterSetterClass.getUser_name();


                    Bundle sendData=new Bundle();
                    sendData.putString("worker_name",worker_name);
                    sendData.putString("occupation",occupation);
                    sendData.putString("date",date);
                    sendData.putString("time",time);
                    sendData.putString("jobDescription",jobDescription);
                    sendData.putString("worker_pno",worker_pno);
                    sendData.putString("workerId",workerId);
                    sendData.putString("skill",skillset);
                    sendData.putString("url",getterSetterClass.getImageUrl());
                    sendData.putString("user_name",user_name);

                    Intent intent=new Intent(c,processedInfo.class);
                    intent.putExtras(sendData);
                    c.startActivity(intent);

                }
            });
        }






        return convertView;
    }
}
