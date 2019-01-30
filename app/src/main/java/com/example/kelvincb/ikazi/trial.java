package com.example.kelvincb.ikazi;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class trial extends AppCompatActivity {

    ImageView userImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial);

        userImage=findViewById(R.id.imageViewx);

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkversions();
            }
        });


    }

    public  void checkversions(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            try {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},555);

            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            pickImage();
        }
    }

    private void pickImage() {

        CropImage.startPickImageActivity(this);

    }

    private void cropRequest(Uri uri){
        CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode== Activity.RESULT_OK){
            Uri imageuri=CropImage.getPickImageResultUri(this,data);
            cropRequest(imageuri);

            Log.d("something_else", imageuri.toString());
        }

        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK){
                try {
                    Bitmap bitmap= MediaStore.Images.Media.getBitmap(this.getContentResolver(),result.getUri());
                    userImage.setImageBitmap(bitmap);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 555 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            pickImage();
        }
        else {
            checkversions();
        }
    }
}
