package com.example.kelvincb.ikazi.Main.mainFragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.kelvincb.ikazi.Main.MainActivity;
import com.example.kelvincb.ikazi.fetchImage;
import com.example.kelvincb.ikazi.mPicasso.PicassoClient;
import com.theartofdev.edmodo.cropper.CropImage;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
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

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kelvincb.ikazi.R;
import com.example.kelvincb.ikazi.UserLoginAndRegister.LoginRegisterActivity;
import com.example.kelvincb.ikazi.fetchPhoneNumber;
import com.example.kelvincb.ikazi.fetchUserName;
import com.google.firebase.auth.FirebaseAuth;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;


public class userProfile extends Fragment {

    View view;
   public static EditText name,phone_no;
    Button update;
    ProgressBar progressBar;

    TextView myprofile;

    public static ImageView userImage;




    public static final String UPDATE_URL = "http://104.248.124.210/android/iKazi/phpFiles/updateProfile.php";

    /**
     * Persist URI image to crop URI if specific permissions are required
     */
    private FirebaseAuth mAuth;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_my_profile, container, false);


        mAuth = FirebaseAuth.getInstance();


        TextView logout=view.findViewById(R.id.logouttxt);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                Intent i = new Intent(getContext(),LoginRegisterActivity.class);
                startActivity(i);
            }
        });

        final fetchPhoneNumber fetchPhoneNumber=new fetchPhoneNumber(getContext());
        fetchPhoneNumber.mynumber();

        myprofile=view.findViewById(R.id.myProfile);
        name=view.findViewById(R.id.name_profile);
        phone_no=view.findViewById(R.id.phone_no_profile);
        update=view.findViewById(R.id.btn_update_profile);
        progressBar=view.findViewById(R.id.progressBar_update);
        userImage = view.findViewById(R.id.user_profile_photo2);


        Typeface font1=Typeface.createFromAsset(getActivity().getAssets(),"RobotoSlab-Bold.ttf");
        logout.setTypeface(font1);
        myprofile.setTypeface(font1);

        Typeface font=Typeface.createFromAsset(getActivity().getAssets(),"RobotoSlab-Light.ttf");
        phone_no.setTypeface(font);
        name.setTypeface(font);
        update.setTypeface(font);


        phone_no.setText(fetchPhoneNumber.getPhone_no());
        phone_no.setEnabled(false);

        final fetchUserName fetchUserName=new fetchUserName(getContext());
        fetchUserName.fetchname();

        final fetchImage fetchImage=new fetchImage(getContext());
        fetchImage.fetchimageurl();



        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                name.setText(fetchUserName.getMname());
                PicassoClient.loadImage(fetchImage.getImageurl(),userImage);

                userImage.setTag("0");
                name.setTag("0");



            }},2000);






        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name.setTag("changed");
                update.setEnabled(true);
                update.setAlpha(1f);
                update.setClickable(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               checkversions();

            }
        });

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if (!TextUtils.isEmpty(name.getText())) {
                            if(userImage.getDrawable() == null){
                                Toast.makeText(getActivity(), "you have not added an image", Toast.LENGTH_SHORT).show();
                            }else {
                                if (userImage.getTag().equals("UpdatedTag") || name.getTag().equals("changed")){
                                    Toast.makeText(getActivity(), ""+userImage.getTag().toString(), Toast.LENGTH_SHORT).show();
                                    ((MainActivity)getActivity()).updateData();
                                } else {
                                    Toast.makeText(getActivity(), "you have not changed anything", Toast.LENGTH_SHORT).show();

                                }

                            }
                        } else {
                            name.setError("Please Enter your Name");
                        }
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


    private void signOut() {
        mAuth.signOut();
    }





}
