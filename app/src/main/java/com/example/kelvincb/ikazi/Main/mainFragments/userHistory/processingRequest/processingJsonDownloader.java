package com.example.kelvincb.ikazi.Main.mainFragments.userHistory.processingRequest;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class processingJsonDownloader {

    private final Context c;
    private processingAdapterClass adapter;
    RequestQueue requestQueue;

    public processingJsonDownloader(Context c) {
        this.c = c;
    }

    public void retrieveRequestInfo(String URL, final ListView listView, final TextView tv, final ProgressBar myProgressBar) {

        final ArrayList<processingGetterSetters> requestList = new ArrayList<>();

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


                            processingGetterSetters GettersSetters;
                            JSONArray ja = response.getJSONArray("results");
                            for (int i = 0; i < ja.length(); i++) {

                                JSONObject jsonObject = ja.getJSONObject(i);

                                String id =jsonObject.getString("workerId");
                                String status=jsonObject.getString("status");
                                String name=jsonObject.getString("name");
                                String occupation=jsonObject.getString("occupation");
                                String imageUrl=jsonObject.getString("imageUrl");
                                String datesent=jsonObject.getString("currentdate");



                                GettersSetters = new processingGetterSetters();
                                GettersSetters.setWorkerId(id);
                                GettersSetters.setName(name);
                                GettersSetters.setOccupation(occupation);
                                GettersSetters.setStatus(status);
                                GettersSetters.setImageUrl(imageUrl);
                                GettersSetters.setDatesent(datesent);

                                requestList.add(GettersSetters);

                            }
                            adapter = new processingAdapterClass(c,requestList);
                            listView.setAdapter(adapter);
                            myProgressBar.setVisibility(View.GONE);


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


    }}
