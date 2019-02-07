package com.example.kelvincb.ikazi.Main;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kelvincb.ikazi.Main.mainFragments.availableWorkers.availableWorkersFragment;
import com.example.kelvincb.ikazi.Main.mainFragments.mainFragment;
import com.example.kelvincb.ikazi.Main.mainFragments.userHistory.myHistoryFragment;
import com.example.kelvincb.ikazi.Main.mainFragments.userProfile;
import com.example.kelvincb.ikazi.R;
import com.example.kelvincb.ikazi.UserLoginAndRegister.LoginRegisterActivity;
import com.example.kelvincb.ikazi.UserLoginAndRegister.userLogin.firebaseStuff.beforeLogin;
import com.example.kelvincb.ikazi.UserLoginAndRegister.userRegistration.RequestHandler;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.iid.FirebaseInstanceId;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Objects;

import static com.example.kelvincb.ikazi.Main.mainFragments.userProfile.UPDATE_URL;
import static com.example.kelvincb.ikazi.Main.mainFragments.userProfile.name;
import static com.example.kelvincb.ikazi.Main.mainFragments.userProfile.phone_no;
import static com.example.kelvincb.ikazi.Main.mainFragments.userProfile.userImage;
import static com.example.kelvincb.ikazi.UserLoginAndRegister.userRegistration.userRegistrationFragment.UPLOAD_KEY;
import static com.example.kelvincb.ikazi.UserLoginAndRegister.userRegistration.userRegistrationFragment.UPLOAD_URL;
import static com.example.kelvincb.ikazi.UserLoginAndRegister.userRegistration.userRegistrationFragment.nameET;
import static com.example.kelvincb.ikazi.UserLoginAndRegister.userRegistration.userRegistrationFragment.phoneET;


public class MainActivity extends AppCompatActivity {

    AdView mAdView;
    Fragment fragment;
    BottomNavigationView navigation;

    Deque<Integer> mStack = new ArrayDeque<>();
    boolean isBackPressed  = false;

    Bitmap bitmap;


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
                    bitmap= MediaStore.Images.Media.getBitmap(Objects.requireNonNull(this).getContentResolver(),result.getUri());
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 10, bytes);
                    userImage.setImageBitmap(bitmap);
                    userImage.setTag("UpdatedTag");

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void updateData(){

        bitmap = ((BitmapDrawable)userImage.getDrawable()).getBitmap();


        class UploadData extends AsyncTask<Bitmap,Void,String> {



            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Updating...", null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(s.equals("Successfully Uploaded")){

                }

                Toast.makeText(MainActivity.this, ""+s, Toast.LENGTH_SHORT).show();

            }

            @Override
            protected String doInBackground(Bitmap... params) {

                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();

                data.put("image", uploadImage);
                data.put("phone",phone_no.getText().toString());
                data.put("name",name.getText().toString());
                String result = rh.sendPostRequest(UPDATE_URL,data);

                return result;
            }
        }

        UploadData ui = new UploadData();
        ui.execute(bitmap);
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

}
