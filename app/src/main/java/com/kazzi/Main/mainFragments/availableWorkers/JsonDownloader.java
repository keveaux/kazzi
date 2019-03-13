package com.kazzi.Main.mainFragments.availableWorkers;

import android.content.Context;
import android.location.Location;
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

public class JsonDownloader {

    private final Context c;
    private adapterClass adapter;
    RequestQueue requestQueue;


    public JsonDownloader(Context c) {
        // Code goes here.
        this.c = c;
    }

    public void retrieveWorkerInfo(final String streetName, final String URL, final ListView listView, final ProgressBar myProgressBar,  final TextView tv, final Double mlongitude, final Double mlatitude) {

        final ArrayList<getterSetterClass> workerList = new ArrayList<>();

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


                            getterSetterClass GettersSetters;
                            JSONArray ja = response.getJSONArray("results");

                            for (int i = 0; i < ja.length(); i++) {

                                JSONObject jsonObject = ja.getJSONObject(i);

                                String id =jsonObject.getString("id");
                                String name = jsonObject.getString("name");
                                String rating=jsonObject.getString("rating");
                                String numberOfRates=jsonObject.getString("numberOfRates");
                                String imageUrl=jsonObject.getString("imageUrl");
                                String occupation=jsonObject.getString("occupation");
                                String longitude=jsonObject.getString("longitude");
                                String latitude=jsonObject.getString("latitude");
                                String skillSet=jsonObject.getString("skillSet");
                                String phoneNumber=jsonObject.getString("phonenumber");
                                String token=jsonObject.getString("token");
                                String work_done_one=jsonObject.getString("work_done_one");
                                String work_done_two=jsonObject.getString("work_done_two");
                                String work_done_three=jsonObject.getString("work_done_three");
                                String availability=jsonObject.getString("availability");
                                String facebook=jsonObject.getString("facebook");
                                String twitter=jsonObject.getString("twitter");
                                String linkedin=jsonObject.getString("linkedin");
                                String github=jsonObject.getString("github");
                                String email=jsonObject.getString("email");








                                GettersSetters = new getterSetterClass();
                                GettersSetters.setId(id);
                                GettersSetters.setName(name);
                                GettersSetters.setRating(rating);
                                GettersSetters.setNumberOfRates(numberOfRates);
                                GettersSetters.setImageUrl(imageUrl);
                                GettersSetters.setOccupation(occupation);
                                GettersSetters.setLongitude(longitude);
                                GettersSetters.setLatitude(latitude);
                                GettersSetters.setSkillSet(skillSet);
                                GettersSetters.setPhoneNumber(phoneNumber);
                                GettersSetters.setLocation(streetName);
                                GettersSetters.setToken(token);
                                GettersSetters.setWork_done_one(work_done_one);
                                GettersSetters.setWork_done_two(work_done_two);
                                GettersSetters.setWork_done_three(work_done_three);
                                GettersSetters.setFacebook(facebook);
                                GettersSetters.setTwitter(twitter);
                                GettersSetters.setLinkedin(linkedin);
                                GettersSetters.setGithub(github);
                                GettersSetters.setEmail(email);





                                Location locationA = new Location("point A");

                                locationA.setLatitude(mlatitude);
                                locationA.setLongitude(mlongitude);

                                Location locationB = new Location("point B");

                                locationB.setLatitude(Double.parseDouble(latitude));
                                locationB.setLongitude(Double.parseDouble(longitude));

                                float distance = locationA.distanceTo(locationB);

                                GettersSetters.setDistance(distance);




                                if (distance<15000&&availability.equals("1")){
                                    workerList.add(GettersSetters);

                                }
                                else {

                                    tv.setVisibility(View.VISIBLE);
                                }

                            }
                            adapter = new adapterClass(c,workerList);
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


    }
}
