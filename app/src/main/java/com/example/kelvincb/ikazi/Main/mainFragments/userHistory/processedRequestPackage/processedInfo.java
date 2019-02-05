package com.example.kelvincb.ikazi.Main.mainFragments.userHistory.processedRequestPackage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kelvincb.ikazi.R;
import com.example.kelvincb.ikazi.UserLoginAndRegister.LoginRegisterActivity;

import java.util.HashMap;
import java.util.Map;

public class processedInfo extends AppCompatActivity {

    TextView worker_name, occupationTV, timeTV, dateTV, jobDescriptionTV,nametv,occupationtextview,datetextview,timetextview,jobdesctv;
    String workerId,rating;
    ProgressBar progressBar;
    RatingBar mRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processed_info);


        nametv=findViewById(R.id.textView_name);
        occupationtextview=findViewById(R.id.textView_occupation);
        datetextview=findViewById(R.id.textView_date);
        timetextview=findViewById(R.id.textView_time);
        jobdesctv=findViewById(R.id.textView_jobdesc);

        worker_name = findViewById(R.id.worker_name);
        occupationTV = findViewById(R.id.occupation);
        timeTV = findViewById(R.id.time);
        dateTV = findViewById(R.id.date);
        jobDescriptionTV = findViewById(R.id.jobDescription);
        progressBar=findViewById(R.id.feedback_progressBar);



        FloatingActionButton fab=findViewById(R.id.fab);


        Typeface myfont=Typeface.createFromAsset(getAssets(),"RobotoSlab-Bold.ttf");
        nametv.setTypeface(myfont);
        occupationtextview.setTypeface(myfont);
        datetextview.setTypeface(myfont);
        timetextview.setTypeface(myfont);
        jobdesctv.setTypeface(myfont);


        Typeface font=Typeface.createFromAsset(getAssets(),"RobotoSlab-Light.ttf");
        worker_name.setTypeface(font);
        occupationTV.setTypeface(font);
        timeTV.setTypeface(font);
        dateTV.setTypeface(font);
        jobDescriptionTV.setTypeface(font);


        Bundle bundle = this.getIntent().getExtras();

        if (bundle != null) {
            //ObtainBundleData in the object
            String name = bundle.getString("worker_name");
            String occupation = bundle.getString("occupation");
            String time = bundle.getString("time");
            String date = bundle.getString("date");
            String jobDescription = bundle.getString("jobDescription");
            final String worker_pno = bundle.getString("worker_pno");
            workerId=bundle.getString("workerId");


            worker_name.setText(name);
            occupationTV.setText(occupation);
            timeTV.setText(time);
            dateTV.setText(date);
            jobDescriptionTV.setText(jobDescription);




            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", worker_pno, null));
                    startActivity(intent);
                }
            });
            //Do something here if data  received
        }
        else
        {
            //Do something here if data not received
        }


         mRatingBar =  findViewById(R.id.ratingBar);

        final TextView mRatingScale =  findViewById(R.id.tvRatingScale);

        mRatingScale.setText("Please rate "+worker_name.getText().toString());

        Button mSendFeedback =  findViewById(R.id.btnSubmit);

        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                mRatingScale.setText(String.valueOf(v));
                switch ((int) ratingBar.getRating()) {
                    case 1:
                        mRatingScale.setText("Very bad");
                        break;
                    case 2:
                        mRatingScale.setText("Need some improvement");
                        break;
                    case 3:
                        mRatingScale.setText("Good");
                        break;
                    case 4:
                        mRatingScale.setText("Great");
                        break;
                    case 5:
                        mRatingScale.setText("Awesome. I love it");
                        break;
                    default:
                        mRatingScale.setText("Please rate "+worker_name.getText().toString());
                }
            }
        });

        mSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRatingBar.getRating()==0) {
                    Toast.makeText(processedInfo.this, "Please fill in the rating", Toast.LENGTH_LONG).show();
                } else {
                    rating= String.valueOf(mRatingBar.getRating());
                    sendData();
                    Toast.makeText(processedInfo.this, ""+mRatingBar.getRating(), Toast.LENGTH_SHORT).show();
                    mRatingBar.setRating(0);

                }
            }
        });

    }

    private void sendData() {
        progressBar.setVisibility(View.VISIBLE);
        String MyURL="http://104.248.124.210/android/iKazi/phpFiles/sendRating.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, MyURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);

                if(response.equals("successful")){
                    Toast.makeText(processedInfo.this, "Thank you for sharing your feedback", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(processedInfo.this, response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(processedInfo.this,error+"",Toast.LENGTH_LONG).show();


            }
        }){

            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("workerId",workerId);
                params.put("rating", rating);

                return params;
            }

        };

        RequestQueue requestQueue= Volley.newRequestQueue(processedInfo.this);
        requestQueue.add(stringRequest);

    }
}
