package com.kazzi.Main.mainFragments.availableWorkers.WorkerProfile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kazzi.R;


public class WorkerProfileActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_profile);

        Toolbar toolbar=findViewById(R.id.profile_toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });


        switch (getIntent().getStringExtra("EXTRA")) {


            default:
                Bundle bundle = new Bundle();
                String id=getIntent().getStringExtra("id");
                String name=getIntent().getStringExtra("name");
                String skill=getIntent().getStringExtra("skill");
                String occupation=getIntent().getStringExtra("occupation");
                String url=getIntent().getStringExtra("url");
                String rating=getIntent().getStringExtra("rating");
                String url_one=getIntent().getStringExtra("url_one");
                String url_two=getIntent().getStringExtra("url_two");
                String url_three=getIntent().getStringExtra("url_three");
                String facebook=getIntent().getStringExtra("facebook");
                String twitter=getIntent().getStringExtra("twitter");
                String linkedin=getIntent().getStringExtra("linkedin");
                String github=getIntent().getStringExtra("github");
                String email=getIntent().getStringExtra("email");





                AppCompatActivity activity =  WorkerProfileActivity.this;
                WorkerProfileFragment myFragment = new WorkerProfileFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.worker_profile_frame_container, myFragment).addToBackStack(null).commit();
                bundle.putString( "id", id);
                bundle.putString("name",name);
                bundle.putString("occupation",occupation);
                bundle.putString("skill",skill);
                bundle.putString("url",url);
                bundle.putString("rating",rating);
                bundle.putString("url_one",url_one);
                bundle.putString("url_two",url_two);
                bundle.putString("url_three",url_three);
                bundle.putString("facebook",facebook);
                bundle.putString("twitter",twitter);
                bundle.putString("linkedin",linkedin);
                bundle.putString("github",github);
                bundle.putString("email",email);







                myFragment.setArguments(bundle);

        }


    }


    @Override
    public void onBackPressed() {
        this.finish();
    }
}
