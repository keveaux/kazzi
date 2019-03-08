package com.example.kelvincb.ikazi.Main.mainFragments.availableWorkers;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import com.example.kelvincb.ikazi.Main.MainActivity;
import com.example.kelvincb.ikazi.R;

public class AvailableWorkers extends AppCompatActivity {

    Toolbar toolbar;
    String occupation;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_workers);

        bundle= new Bundle();

        occupation=getIntent().getStringExtra("occupation");


        toolbar=findViewById(R.id.toolbar);

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


    }

    @Override
    public void onBackPressed() {
        finish();

        Intent intent = new Intent(AvailableWorkers.this, MainActivity.class);
        intent.putExtra("EXTRA", "openMain");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.android_action_bar_spinner_menu, menu);

        MenuItem item = menu.findItem(R.id.spinner);
        final Spinner spinner = (Spinner) item.getActionView();

        ArrayAdapter<CharSequence> adapter = null;
        if(occupation.equals("Computer Services")){
            adapter = ArrayAdapter.createFromResource(this,
                    R.array.spinner_list_item_array_graphics, R.layout.layout_drop_title);
            adapter.setDropDownViewResource(R.layout.layout_drop_list);
        }else if(occupation.equals("Cleaning")){
            adapter = ArrayAdapter.createFromResource(this,
                    R.array.spinner_list_item_array_cleaning, R.layout.layout_drop_title);
            adapter.setDropDownViewResource(R.layout.layout_drop_list);
        }else if(occupation.equals("Photographer")){
            adapter = ArrayAdapter.createFromResource(this,
                    R.array.spinner_list_item_array_photographer, R.layout.layout_drop_title);
            adapter.setDropDownViewResource(R.layout.layout_drop_list);
        }else if(occupation.equals("Handy Person")){
            adapter = ArrayAdapter.createFromResource(this,
                    R.array.spinner_list_item_array_handy, R.layout.layout_drop_title);
            adapter.setDropDownViewResource(R.layout.layout_drop_list);
        }else if(occupation.equals("Transport")){
            adapter = ArrayAdapter.createFromResource(this,
                    R.array.spinner_list_item_array_transport, R.layout.layout_drop_title);
            adapter.setDropDownViewResource(R.layout.layout_drop_list);
        }else if(occupation.equals("Event Planning")){
            adapter = ArrayAdapter.createFromResource(this,
                    R.array.spinner_list_item_array_caterer, R.layout.layout_drop_title);
            adapter.setDropDownViewResource(R.layout.layout_drop_list);
        }else if(occupation.equals("Gym Services")){
            adapter = ArrayAdapter.createFromResource(this,
                    R.array.spinner_list_item_array_gym, R.layout.layout_drop_title);
            adapter.setDropDownViewResource(R.layout.layout_drop_list);
        }else if(occupation.equals("Internet Solutions")){
            adapter = ArrayAdapter.createFromResource(this,
                    R.array.spinner_list_item_array_internet, R.layout.layout_drop_title);
            adapter.setDropDownViewResource(R.layout.layout_drop_list);
        }
        else if(occupation.equals("Grocery Delivery")){
             adapter = ArrayAdapter.createFromResource(this,
                    R.array.spinner_list_item_array_grocery, R.layout.layout_drop_title);
            adapter.setDropDownViewResource(R.layout.layout_drop_list);
        }
        else if(occupation.equals("Gardening")){
            adapter = ArrayAdapter.createFromResource(this,
                    R.array.spinner_list_item_array_gardening, R.layout.layout_drop_title);
            adapter.setDropDownViewResource(R.layout.layout_drop_list);
        }
        else if(occupation.equals("Mechanical Services")){
            adapter = ArrayAdapter.createFromResource(this,
                    R.array.spinner_list_item_array_car_service, R.layout.layout_drop_title);
            adapter.setDropDownViewResource(R.layout.layout_drop_list);
        }
        else if(occupation.equals("Beauty Services")){
            adapter = ArrayAdapter.createFromResource(this,
                    R.array.spinner_list_item_array_beauty, R.layout.layout_drop_title);
            adapter.setDropDownViewResource(R.layout.layout_drop_list);
        }



        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);

                String spinner_items = spinner.getSelectedItem().toString();


                switch (getIntent().getStringExtra("EXTRA")) {

                    case "openAvailableWorkers":
                        String latitude = getIntent().getStringExtra("lat");
                        String longitude = getIntent().getStringExtra("lon");
                        bundle.putString("EXTRA","second");
                        bundle.putString("lat", latitude);
                        bundle.putString("lon", longitude);
                        bundle.putString("occupation",occupation);
                        bundle.putString("subservice",spinner_items);

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
                        bundle.putString("subservice",spinner_items);
                        bundle.putString("EXTRA","first");
                        // set Fragmentclass Arguments
                        myFragment.setArguments(bundle);


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return true;
    }
}