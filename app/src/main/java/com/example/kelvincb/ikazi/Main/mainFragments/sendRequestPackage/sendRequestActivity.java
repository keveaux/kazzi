package com.example.kelvincb.ikazi.Main.mainFragments.sendRequestPackage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import com.example.kelvincb.ikazi.Main.MainActivity;
import com.example.kelvincb.ikazi.Main.mainFragments.availableWorkers.AvailableWorkers;
import com.example.kelvincb.ikazi.Main.mainFragments.availableWorkers.availableWorkersFragment;
import com.example.kelvincb.ikazi.R;

public class sendRequestActivity extends AppCompatActivity {

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_request);

        toolbar=findViewById(R.id.send_request_toolbar);

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
                String location=getIntent().getStringExtra("location");
                String token=getIntent().getStringExtra("token");
                String url=getIntent().getStringExtra("url");


                AppCompatActivity activity =  sendRequestActivity.this;
                sendRequest myFragment = new sendRequest();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.send_request_frame_container, myFragment).addToBackStack(null).commit();
                bundle.putString( "id", id);
                bundle.putString( "name", name);
                bundle.putString( "skill", skill);
                bundle.putString( "occupation", occupation);
                bundle.putString( "location", location);
                bundle.putString( "token", token);
                bundle.putString( "url", url);


                myFragment.setArguments(bundle);

        }

    }

    @Override
    public void onBackPressed() {
        finish();

        Intent intent=new Intent(sendRequestActivity.this, MainActivity.class);
        intent.putExtra("EXTRA","openMain");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}
