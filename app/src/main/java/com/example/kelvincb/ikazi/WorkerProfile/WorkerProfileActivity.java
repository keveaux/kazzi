package com.example.kelvincb.ikazi.WorkerProfile;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.kelvincb.ikazi.Main.mainFragments.sendRequestPackage.sendRequest;
import com.example.kelvincb.ikazi.Main.mainFragments.sendRequestPackage.sendRequestActivity;
import com.example.kelvincb.ikazi.R;
import com.example.kelvincb.ikazi.WorkerProfile.WorkerProfileFragment;

public class WorkerProfileActivity extends AppCompatActivity {

    Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_profile);


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





                myFragment.setArguments(bundle);

        }


    }


    @Override
    public void onBackPressed() {
        this.finish();
    }
}
