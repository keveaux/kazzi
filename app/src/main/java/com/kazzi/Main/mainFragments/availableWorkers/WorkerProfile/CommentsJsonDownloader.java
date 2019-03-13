package com.kazzi.Main.mainFragments.availableWorkers.WorkerProfile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

public class CommentsJsonDownloader {

    private final Context c;
    private CommentadapterClass adapter;
    RequestQueue requestQueue;

    public CommentsJsonDownloader(Context c) {
        this.c = c;
    }

    public void retrieveRequestInfo(String URL, final RecyclerView recyclerView, final TextView tv, final ProgressBar myProgressBar) {

        final ArrayList<CommentgetterSetterClass> requestList = new ArrayList<>();

        myProgressBar.setIndeterminate(true);
        myProgressBar.setVisibility(View.VISIBLE);

        requestQueue = Volley.newRequestQueue(c);
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        if(response.isNull("results")){
                            myProgressBar.setVisibility(View.GONE);
                            tv.setText("0 Tasks Completed");

                        }
                        try {


                            CommentgetterSetterClass GettersSetters;
                            JSONArray ja = response.getJSONArray("results");
                            for (int i = 0; i < ja.length(); i++) {

                                JSONObject jsonObject = ja.getJSONObject(i);

                                String username =jsonObject.getString("username");
                                String rating=jsonObject.getString("rating");
                                String comment=jsonObject.getString("comment");
                                String tasksdone=jsonObject.getString("jobs");
                                String imageurl=jsonObject.getString("image");






                                GettersSetters = new CommentgetterSetterClass();
                                GettersSetters.setUsername(username);
                                GettersSetters.setRating(rating);
                                GettersSetters.setComment(comment);
                                GettersSetters.setTasksdone(tasksdone);
                                GettersSetters.setImageurl(imageurl);

                                tv.setText(GettersSetters.getTasksdone()+" Tasks Completed");

                                requestList.add(GettersSetters);



                            }
                            adapter = new CommentadapterClass(c,requestList);
                            recyclerView.setAdapter(adapter);
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


    }
}
