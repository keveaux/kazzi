package com.kazzi.Main.mainFragments.availableWorkers.mLocation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.kazzi.Main.mainFragments.availableWorkers.AvailableWorkers;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.kazzi.R;


public class autoCompleteLocation extends AppCompatActivity implements PlaceSelectionListener{

    String occupation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autocomplete);

        occupation=getIntent().getStringExtra("occupation");


        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(this);


    }

    public void onPlaceSelected(Place place) {


        String mylat= String.valueOf(place.getLatLng());


        mylat = mylat.substring(mylat.indexOf("(") + 1);
        mylat = mylat.substring(0, mylat.indexOf(","));

        String mylon= String.valueOf(place.getLatLng());


        mylon = mylon.substring(mylon.indexOf(",") + 1);
        mylon = mylon.substring(0, mylon.indexOf(")"));


        Intent i=new Intent(getApplicationContext(),AvailableWorkers.class);
        i.putExtra("EXTRA","openAvailableWorkers");
        i.putExtra("occupation",occupation);
        i.putExtra("lat",mylat);
        i.putExtra("lon",mylon);
        startActivity(i);


    }

    public void onError(Status status) {
        Toast.makeText(getApplicationContext(), "" + status.toString(), Toast.LENGTH_LONG).show();
    }
}
