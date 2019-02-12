package com.example.kelvincb.ikazi.UserLoginAndRegister.userRegistration;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kelvincb.ikazi.UserLoginAndRegister.LoginRegisterActivity;
import com.example.kelvincb.ikazi.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class userRegistrationFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    View view;
    TextView loginLink,signuptxt;
    Button signup;
    public static  EditText nameET,phoneET;
    ProgressBar progressBar;
    ImageView user_profile_photo;

    public static final String UPLOAD_URL = "http://104.248.124.210/android/iKazi/phpFiles/sendRegistrationDetails.php";
    public static final String UPLOAD_KEY = "image";
    public static String token = "";





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.activity_user_registration, container, false);

        token= FirebaseInstanceId.getInstance().getToken();
        Log.d("token",token);
        Toast.makeText(getActivity(), ""+token, Toast.LENGTH_SHORT).show();

        user_profile_photo=view.findViewById(R.id.user_profile_photo2);

        progressBar=view.findViewById(R.id.reg_progressBar);

        signuptxt=view.findViewById(R.id.signuptxt);

        nameET=view.findViewById(R.id.input_name_reg);
        phoneET=view.findViewById(R.id.input_phone_number_reg);
        loginLink=view.findViewById(R.id.link_login);


        signup=view.findViewById(R.id.btn_signup);



        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Light.ttf");
        signuptxt.setTypeface(font);
        nameET.setTypeface(font);
        phoneET.setTypeface(font);
        signup.setTypeface(font);
        loginLink.setTypeface(font);

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), LoginRegisterActivity.class);
                getActivity().startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(user_profile_photo.getDrawable() == null){

                    Toast.makeText(getActivity(), "please add a photo", Toast.LENGTH_SHORT).show();

                }else {
                    String regexStr = "^[+][0-9]{10,13}$";

                    if(phoneET.getText().toString().matches(regexStr)){
                        ((LoginRegisterActivity)getActivity()).uploadData();

                        }else {

                        phoneET.setError("enter valid phone number  ");

                    }


                }

                if(!TextUtils.isEmpty(nameET.getText()) && !TextUtils.isEmpty(phoneET.getText()) ){



                }else if(TextUtils.isEmpty(nameET.getText())){
                    nameET.setError("required");
                }else {
                    phoneET.setError("required");
                }
            }
        });

        user_profile_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkversions();
            }
        });

        return view;
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

        CropImage.startPickImageActivity(Objects.requireNonNull(getActivity()));

    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
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
