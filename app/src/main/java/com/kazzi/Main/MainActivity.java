package com.kazzi.Main;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.kazzi.Main.mainFragments.AboutFragment;
import com.kazzi.Main.mainFragments.mainFragment;
import com.kazzi.Main.mainFragments.userHistory.myHistoryFragment;
import com.kazzi.Main.mainFragments.userProfile;
import com.kazzi.R;
import com.kazzi.UserLoginAndRegister.userRegistration.RequestHandler;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Objects;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;
import q.rorbin.badgeview.QBadgeView;

import static com.kazzi.Main.mainFragments.userProfile.UPDATE_URL;
import static com.kazzi.Main.mainFragments.userProfile.first_name;
import static com.kazzi.Main.mainFragments.userProfile.last_name;
import static com.kazzi.Main.mainFragments.userProfile.name;
import static com.kazzi.Main.mainFragments.userProfile.phone_no;
import static com.kazzi.Main.mainFragments.userProfile.userImage;


public class MainActivity extends AppCompatActivity {

//    AdView mAdView;
    Fragment fragment;
    BottomNavigationView navigation;

    Deque<Integer> mStack = new ArrayDeque<>();
    boolean isBackPressed  = false;

    Bitmap bitmap;
    Toolbar toolbar;

//    TextView title;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=findViewById(R.id.main_toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);






        navigation =  findViewById(R.id.navigation);

        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) navigation.getChildAt(0);
        View v = bottomNavigationMenuView.getChildAt(1); // number of menu from left
        new QBadgeView(this).bindTarget(v).setBadgeNumber(1);

        navigation.getMenu().getItem(0).setChecked(true);

        setBottomNavigationView();

        switch (getIntent().getStringExtra("EXTRA")) {

                default:
            fragment = new mainFragment();
            loadFragment(fragment);


        }
    }

    public void confetti(){
        new ads(getApplicationContext());
        KonfettiView viewKonfetti=findViewById(R.id.viewKonfetti);
        viewKonfetti.build()
                .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA,Color.BLUE,Color.RED,Color.CYAN)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .addSizes(new Size(7,8))
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(Shape.RECT, Shape.CIRCLE)
                .setPosition(-50f, viewKonfetti.getWidth() + 50f, -50f, -50f)
                .streamFor(300, 5000L);
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

            String pno=phone_no.getText().toString();

            String username=name.getText().toString();

            String fname=first_name.getText().toString();
            String lname=last_name.getText().toString();

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
                data.put("phone",pno);
                data.put("name",username);
                data.put("fname",fname);
                data.put("lname",lname);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_page:
                loadFragment(new AboutFragment());
                break;
            case R.id.ads:
                confetti();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
