package com.example.kelvincb.ikazi.UserLoginAndRegister;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kelvincb.ikazi.UserLoginAndRegister.userLogin.firebaseStuff.beforeLogin;
import com.example.kelvincb.ikazi.R;
import com.example.kelvincb.ikazi.UserLoginAndRegister.userRegistration.RequestHandler;
import com.example.kelvincb.ikazi.splashScreen.SplashScreenActivity;
import com.google.firebase.iid.FirebaseInstanceId;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Objects;

import static com.example.kelvincb.ikazi.UserLoginAndRegister.userRegistration.userRegistrationFragment.UPLOAD_KEY;
import static com.example.kelvincb.ikazi.UserLoginAndRegister.userRegistration.userRegistrationFragment.UPLOAD_URL;
import static com.example.kelvincb.ikazi.UserLoginAndRegister.userRegistration.userRegistrationFragment.nameET;
import static com.example.kelvincb.ikazi.UserLoginAndRegister.userRegistration.userRegistrationFragment.phoneET;
import static com.example.kelvincb.ikazi.UserLoginAndRegister.userRegistration.userRegistrationFragment.token;

public class LoginRegisterActivity extends AppCompatActivity {

    Bitmap bitmap;

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
                     bitmap= MediaStore.Images.Media.getBitmap(Objects.requireNonNull(this).getContentResolver(),result.getUri());
                    ImageView image = findViewById(R.id.user_profile_photo2);
                    image.setImageBitmap(bitmap);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void uploadData(){

        class UploadData extends AsyncTask<Bitmap,Void,String> {

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LoginRegisterActivity.this, "Uploading...", null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(LoginRegisterActivity.this, ""+s, Toast.LENGTH_SHORT).show();
                if(s.equals("\"Successfully Uploaded\"")){

                    Bundle bundle = new Bundle();
                    bundle.putString("key",phoneET.getText().toString()); // Put anything what you want

                    //mStatusText.setText("Authenticating....!");
                    beforeLogin fragment2=new beforeLogin();
                    fragment2.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.userFragmentContainer, fragment2,"findThisFragment")
                            .addToBackStack(null)
                            .commit();
                }

            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();


                data.put(UPLOAD_KEY, uploadImage);
                data.put("phone",phoneET.getText().toString());
                data.put("name",nameET.getText().toString());
                data.put("token",token);
                data.put("fname","");
                data.put("lname","");
                String result = rh.sendPostRequest(UPLOAD_URL,data);

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
    public void onBackPressed() {
        Intent intent=new Intent(LoginRegisterActivity.this, SplashScreenActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
