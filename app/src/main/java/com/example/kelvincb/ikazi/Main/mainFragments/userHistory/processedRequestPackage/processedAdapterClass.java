package com.example.kelvincb.ikazi.Main.mainFragments.userHistory.processedRequestPackage;

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
import android.widget.Toast;

import com.example.kelvincb.ikazi.Main.mainFragments.userHistory.processingRequest.processingGetterSetters;
import com.example.kelvincb.ikazi.R;
import com.example.kelvincb.ikazi.mPicasso.PicassoClient;

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
        ImageView image=convertView.findViewById(R.id.processed_worker_image);

        Typeface font=Typeface.createFromAsset(c.getAssets(),"RobotoSlab-Light.ttf");
        occupation.setTypeface(font);
        nametxt.setTypeface(font);



        final processedGetterSetters getterSetterClass= (processedGetterSetters) this.getItem(position);

        nametxt.setText(getterSetterClass.getWorker_name());
        occupation.setText(getterSetterClass.getOccupation());

        //fetch image from online using picasso
        PicassoClient.loadImage(getterSetterClass.getImageUrl(),image);


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

                Bundle sendData=new Bundle();
                sendData.putString("worker_name",worker_name);
                sendData.putString("occupation",occupation);
                sendData.putString("date",date);
                sendData.putString("time",time);
                sendData.putString("jobDescription",jobDescription);
                sendData.putString("worker_pno",worker_pno);
                sendData.putString("workerId",workerId);

                Intent intent=new Intent(c,processedInfo.class);
                intent.putExtras(sendData);
                c.startActivity(intent);

            }
        });


        return convertView;
    }
}