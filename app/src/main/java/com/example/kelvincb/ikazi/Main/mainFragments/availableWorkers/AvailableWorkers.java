package com.example.kelvincb.ikazi.Main.mainFragments.availableWorkers;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import com.example.kelvincb.ikazi.Main.MainActivity;
import com.example.kelvincb.ikazi.R;

public class AvailableWorkers extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_workers);

        Bundle bundle = new Bundle();
        String occupation=getIntent().getStringExtra("occupation");


        toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });


        switch (getIntent().getStringExtra("EXTRA")) {

            case "openAvailableWorkers":
                String latitude = getIntent().getStringExtra("lat");
                String longitude = getIntent().getStringExtra("lon");
                bundle.putString("EXTRA","second");
                bundle.putString("lat", latitude);
                bundle.putString("lon", longitude);
                bundle.putString("occupation",occupation);

                availableWorkersFragment ob = new availableWorkersFragment();
                ob.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.available_workers_frame_container, ob)
                        .addToBackStack(null).commit();
                break;
            default:
                AppCompatActivity activity =  AvailableWorkers.this;
                availableWorkersFragment myFragment = new availableWorkersFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.available_workers_frame_container, myFragment).addToBackStack(null).commit();

                bundle.putString( "occupation", occupation);
                bundle.putString("EXTRA","first");
                // set Fragmentclass Arguments
                myFragment.setArguments(bundle);


        }
    }

    @Override
    public void onBackPressed() {
        finish();

        Intent intent = new Intent(AvailableWorkers.this, MainActivity.class);
        intent.putExtra("EXTRA", "openMain");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
