package com.example.kelvincb.ikazi.Main;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.kelvincb.ikazi.Main.mainFragments.availableWorkers.availableWorkersFragment;
import com.example.kelvincb.ikazi.Main.mainFragments.mainFragment;
import com.example.kelvincb.ikazi.Main.mainFragments.userHistory.myHistoryFragment;
import com.example.kelvincb.ikazi.Main.mainFragments.userProfile;
import com.example.kelvincb.ikazi.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    AdView mAdView;
    Fragment fragment;
    BottomNavigationView navigation;

    Deque<Integer> mStack = new ArrayDeque<>();
    boolean isBackPressed  = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        MobileAds.initialize(this,
                "ca-app-pub-3940256099942544~3347511713");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        navigation =  findViewById(R.id.navigation);
        navigation.getMenu().getItem(0).setChecked(true);

        setBottomNavigationView();

        switch (getIntent().getStringExtra("EXTRA")) {

            case "openAvailableWorkers":
            String latitude = getIntent().getStringExtra("lat");
            String longitude = getIntent().getStringExtra("lon");
            String occupation=getIntent().getStringExtra("occupation");
            Bundle bundle = new Bundle();
            bundle.putString("EXTRA","second");
            bundle.putString("lat", latitude);
            bundle.putString("lon", longitude);
            bundle.putString("occupation",occupation);
            availableWorkersFragment ob = new availableWorkersFragment();
            ob.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, ob)
                    .addToBackStack(null).commit();
                break;
                default:
            fragment = new mainFragment();
            loadFragment(fragment);
        }
    }



    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment,"ONE");
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private void setBottomNavigationView() {
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        if(!isBackPressed) {
                            pushFragmentIntoStack(R.id.navigation_home);
                        }
                        isBackPressed = false;
                        fragment=new mainFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_history:
                        if(!isBackPressed) {
                            pushFragmentIntoStack(R.id.navigation_history);
                        }
                        isBackPressed = false;
                        fragment=new myHistoryFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_profile:
                        if(!isBackPressed) {
                            pushFragmentIntoStack(R.id.navigation_profile);
                        }
                        isBackPressed = false;
                        fragment=new userProfile();
                        loadFragment(fragment);
                        return true;

                    default:
                        return false;
                }
            }
        });

        navigation.setSelectedItemId(R.id.navigation_home);
        pushFragmentIntoStack(R.id.navigation_home);
    }

    private void pushFragmentIntoStack(int id)
    {
        if(mStack.size() < 3)
        {
            mStack.push(id);
        }
        else
        {
            mStack.removeLast();
            mStack.push(id);
        }
    }



    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);

        if (currentFragment instanceof mainFragment) {

            fragment=new mainFragment();
            loadFragment(fragment);

        }


        if(mStack.size() > 1)
        {
            isBackPressed = true;
            mStack.pop();
            navigation.setSelectedItemId(mStack.peek());
        }
        else
        {
            super.onBackPressed();
        }
    }

    private void cropRequest(Uri uri){
        CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(Objects.requireNonNull(this));
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode== Activity.RESULT_OK){
            Uri imageuri=CropImage.getPickImageResultUri(Objects.requireNonNull(this),data);
            cropRequest(imageuri);
            Log.d("something", imageuri.toString());
        }

        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK){
                try {
                    Bitmap bitmap= MediaStore.Images.Media.getBitmap(Objects.requireNonNull(this).getContentResolver(),result.getUri());
                    ImageView image = findViewById(R.id.user_profile_photo2);
                    image.setImageBitmap(bitmap);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

}
