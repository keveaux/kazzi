package com.example.kelvincb.ikazi.Main.mainFragments.userHistory.processingRequest;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kelvincb.ikazi.Main.mainFragments.sendRequestPackage.sendRequest;
import com.example.kelvincb.ikazi.R;
import com.example.kelvincb.ikazi.mPicasso.PicassoClient;

import java.util.ArrayList;

public class processingAdapterClass extends BaseAdapter{

    Context c;
    ArrayList<processingGetterSetters> requestList;

    public processingAdapterClass(Context c, ArrayList<processingGetterSetters> requestList) {
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
            convertView= LayoutInflater.from(c).inflate(R.layout.processing_row_model,parent,false);
        }

        TextView nametxt= convertView.findViewById(R.id.processing_nametxt);
        TextView occupation=convertView.findViewById(R.id.processing_occupation);
        TextView status=convertView.findViewById(R.id.status);
        TextView date=convertView.findViewById(R.id.processing_date);
        ImageView image=convertView.findViewById(R.id.processing_worker_image);

        Typeface font=Typeface.createFromAsset(c.getAssets(),"Roboto-Light.ttf");
        occupation.setTypeface(font);
        status.setTypeface(font);
        nametxt.setTypeface(font);
        date.setTypeface(font);



        final processingGetterSetters getterSetterClass= (processingGetterSetters) this.getItem(position);

        nametxt.setText(getterSetterClass.getName());
        occupation.setText(getterSetterClass.getOccupation());
        date.setText(getterSetterClass.getDatesent());
        if(getterSetterClass.getStatus().equals("0")){
            status.setText(" PENDING ");
            status.setTextColor(Color.parseColor("#FFFFFF"));
            status.setBackgroundResource(R.drawable.rounded_edges);
        }else {
            status.setText(" REJECTED");
            status.setBackgroundResource(R.drawable.rounde_edges_denied);

            status.setTextColor(Color.parseColor("#FFFFFF"));
        }

        //fetch image from online using picasso
        PicassoClient.loadImage(getterSetterClass.getImageUrl(),image);


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        return convertView;
    }
}
