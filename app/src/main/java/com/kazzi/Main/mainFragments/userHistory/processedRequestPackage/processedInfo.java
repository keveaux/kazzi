package com.kazzi.Main.mainFragments.userHistory.processedRequestPackage;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kazzi.R;
import com.kazzi.UserLoginAndRegister.LoginRegisterActivity;
import com.kazzi.fetchPhoneNumber;
import com.kazzi.mPicasso.PicassoClient;

import java.util.HashMap;
import java.util.Map;

public class processedInfo extends AppCompatActivity {

   private TextView worker_name, acceptedTV, timeTV, dateTV, jobDescriptionTV,occupationtextview,datetextview,timetextview,jobdesctv,TVskillset;
   private String workerId,rating,name,user_name;
    ImageView workerImage;
    Toolbar toolbar;
    RatingBar ratingBar;
    EditText commenttxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processed_info);

        occupationtextview=findViewById(R.id.textView_occupation);
        datetextview=findViewById(R.id.textView_date);
        timetextview=findViewById(R.id.textView_time);
        jobdesctv=findViewById(R.id.textView_jobdesc);

        worker_name = findViewById(R.id.worker_name);
        timeTV = findViewById(R.id.time);
        dateTV = findViewById(R.id.date);
        jobDescriptionTV = findViewById(R.id.jobDescription);
        workerImage=findViewById(R.id.worker_profile_image);
        TVskillset=findViewById(R.id.description);
        toolbar=findViewById(R.id.toolbar);
        acceptedTV=findViewById(R.id.acceptedTV);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });



        FloatingActionButton fab=findViewById(R.id.fab);


        Typeface boldfont=Typeface.createFromAsset(getAssets(),"Roboto-Bold.ttf");
        occupationtextview.setTypeface(boldfont);
        datetextview.setTypeface(boldfont);
        timetextview.setTypeface(boldfont);
        jobdesctv.setTypeface(boldfont);
        TVskillset.setTypeface(boldfont);
        acceptedTV.setTypeface(boldfont);


        Typeface font=Typeface.createFromAsset(getAssets(),"Roboto-Bold.ttf");
        worker_name.setTypeface(font);
        timeTV.setTypeface(font);
        dateTV.setTypeface(font);
        jobDescriptionTV.setTypeface(font);


        Bundle bundle = this.getIntent().getExtras();

        if (bundle != null) {
            //ObtainBundleData in the object
             name = bundle.getString("worker_name");
            String skillset=bundle.getString("skill");
            String imageUrl=bundle.getString("url");
            String occupation = bundle.getString("occupation");
            String time = bundle.getString("time");
            String date = bundle.getString("date");
            String jobDescription = bundle.getString("jobDescription");
            final String worker_pno = bundle.getString("worker_pno");
            workerId=bundle.getString("workerId");
            user_name=bundle.getString("user_name");


            worker_name.setText(name);
            timeTV.setText(time);
            dateTV.setText(date);
            jobDescriptionTV.setText(jobDescription);
            occupationtextview.setText(occupation);

            PicassoClient.loadImage(imageUrl,workerImage);

            TVskillset.setText(skillset);


            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", worker_pno, null));
                    startActivity(intent);
                }
            });
        }





        Button mSendFeedback =  findViewById(R.id.btnSubmit);


        mSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog(processedInfo.this,"please rate "+name);

            }
        });

        Button report=findViewById(R.id.report);

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

                /* Fill it with Data */
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"creativebrandslimited@gmail.com"});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Complaint on worker: "+workerId);
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");

                /* Send it off to the Activity-Chooser */
                startActivity(Intent.createChooser(emailIntent, "click on Gmail to send us an email"));
            }
        });

    }

    public void showDialog(Activity activity, String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.ratingdialog);



        final TextView text = dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        commenttxt=dialog.findViewById(R.id.comment);


        Typeface font1=Typeface.createFromAsset(getAssets(),"Roboto-Bold.ttf");
        text.setTypeface(font1);

         ratingBar=dialog.findViewById(R.id.ratingBar);

                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                text.setText(String.valueOf(v));
                switch ((int) ratingBar.getRating()) {
                    case 1:
                        text.setText("Very bad");
                        break;
                    case 2:
                        text.setText("Need some improvement");
                        break;
                    case 3:
                        text.setText("Good");
                        break;
                    case 4:
                        text.setText("Great");
                        break;
                    case 5:
                        text.setText("Awesome. I love it");
                        break;
                    default:
                        text.setText("Please rate "+worker_name.getText().toString());
                }
            }
        });



        Button dialogButton =  dialog.findViewById(R.id.btn_dialog);
        dialogButton.setTypeface(font1);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (ratingBar.getRating()==0) {
                    Toast.makeText(processedInfo.this, "Please fill in the rating", Toast.LENGTH_LONG).show();
                } else {
                    rating= String.valueOf(ratingBar.getRating());
                    sendData();
                    Toast.makeText(processedInfo.this, ""+ratingBar.getRating(), Toast.LENGTH_SHORT).show();
                    ratingBar.setRating(0);
                    dialog.dismiss();

                }

            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });

        dialog.show();

    }

    private void sendData() {
//        progressBar.setVisibility(View.VISIBLE);
        String MyURL="http://104.248.124.210/android/iKazi/phpFiles/sendRating.php";
        final String comment=commenttxt.getText().toString();

        final fetchPhoneNumber fetchPhoneNumber=new fetchPhoneNumber(processedInfo.this);
        fetchPhoneNumber.mynumber();


        StringRequest stringRequest=new StringRequest(Request.Method.POST, MyURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                progressBar.setVisibility(View.GONE);

                if(response.equals("successful")){
                    Toast.makeText(processedInfo.this, "Thank you for sharing your feedback", Toast.LENGTH_SHORT).show();
                }

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
                params.put("comment",comment);
                params.put("user_name",user_name);
                params.put("pno",fetchPhoneNumber.getPhone_no());

                return params;
            }

        };

        RequestQueue requestQueue= Volley.newRequestQueue(processedInfo.this);
        requestQueue.add(stringRequest);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
