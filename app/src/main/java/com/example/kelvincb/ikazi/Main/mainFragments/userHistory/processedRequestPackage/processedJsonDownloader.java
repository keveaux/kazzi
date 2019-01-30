package com.example.kelvincb.ikazi.Main.mainFragments.userHistory.processedRequestPackage;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.kelvincb.ikazi.Main.mainFragments.userHistory.processingRequest.processingAdapterClass;
import com.example.kelvincb.ikazi.Main.mainFragments.userHistory.processingRequest.processingGetterSetters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class processedJsonDownloader {
    private final Context c;
    private processedAdapterClass adapter;
    RequestQueue requestQueue;

    public processedJsonDownloader(Context c) {
        this.c = c;
    }

    public void retrieveRequestInfo(String URL, final ListView listView, final TextView tv, final ProgressBar myProgressBar) {

        final ArrayList<processedGetterSetters> requestList = new ArrayList<>();

        myProgressBar.setIndeterminate(true);
        myProgressBar.setVisibility(View.VISIBLE);

        requestQueue = Volley.newRequestQueue(c);
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        if(response.isNull("results")){
                            myProgressBar.setVisibility(View.GONE);
                            tv.setVisibility(View.VISIBLE);
                        }
                        try {


                            processedGetterSetters GettersSetters;
                            JSONArray ja = response.getJSONArray("results");
                            for (int i = 0; i < ja.length(); i++) {

                                JSONObject jsonObject = ja.getJSONObject(i);

                                String id =jsonObject.getString("workerId");
                                String user_name=jsonObject.getString("user_name");
                                String user_phone_number=jsonObject.getString("user_phone_number");
                                String date=jsonObject.getString("mdate");
                                String time=jsonObject.getString("mtime");
                                String jobDescription=jsonObject.getString("jobDescription");
                                String landmark=jsonObject.getString("landmark");
                                String worker_name=jsonObject.getString("name");
                                String occupation=jsonObject.getString("occupation");
                                String imageUrl=jsonObject.getString("imageUrl");
                                String worker_pno=jsonObject.getString("phonenumber");





                                GettersSetters = new processedGetterSetters();
                                GettersSetters.setWorkerId(id);
                                GettersSetters.setUser_name(user_name);
                                GettersSetters.setUser_phone_number(user_phone_number);
                                GettersSetters.setMdate(date);
                                GettersSetters.setMtime(time);
                                GettersSetters.setJobDescription(jobDescription);
                                GettersSetters.setLandmark(landmark);
                                GettersSetters.setWorker_name(worker_name);
                                GettersSetters.setOccupation(occupation);
                                GettersSetters.setImageUrl(imageUrl);
                                GettersSetters.setWorker_phone_number(worker_pno);


                                requestList.add(GettersSetters);



                            }
                            adapter = new processedAdapterClass(c,requestList);
                            listView.setAdapter(adapter);
                            myProgressBar.setVisibility(View.GONE);

                            if(adapter.getCount()==0){
                                Toast.makeText(c, "empty", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(c, "" + error, Toast.LENGTH_SHORT).show();
                    }
                }

        );
        requestQueue.add(jor);


    }
}
