package com.example.kelvincb.ikazi.UserLoginAndRegister;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.kelvincb.ikazi.UserLoginAndRegister.userLogin.firebaseStuff.beforeLogin;
import com.example.kelvincb.ikazi.R;
import com.example.kelvincb.ikazi.splashScreen.SplashScreenActivity;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Objects;

public class LoginRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.userFragmentContainer, new beforeLogin());
        fragmentTransaction.commit();



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

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(LoginRegisterActivity.this, SplashScreenActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
