package com.kazzi.Main.mainFragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.kazzi.Main.MainActivity;
import com.kazzi.R;
import com.kazzi.fetchImage;
import com.kazzi.mPicasso.PicassoClient;
import com.theartofdev.edmodo.cropper.CropImage;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kazzi.UserLoginAndRegister.LoginRegisterActivity;
import com.kazzi.fetchPhoneNumber;
import com.kazzi.fetchUserName;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class userProfile extends Fragment {

    View view;
   public static EditText name,phone_no,first_name,last_name;
    Button update;


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

        name=view.findViewById(R.id.name_profile);
        phone_no=view.findViewById(R.id.phone_no_profile);
        first_name=view.findViewById(R.id.first_name);
        last_name=view.findViewById(R.id.second_name);
        update=view.findViewById(R.id.btn_update_profile);
        userImage = view.findViewById(R.id.user_profile_photo2);


        Typeface boldfont=Typeface.createFromAsset(getActivity().getAssets(),"Roboto-Bold.ttf");
        logout.setTypeface(boldfont);

        Typeface font=Typeface.createFromAsset(getActivity().getAssets(),"Roboto-Bold.ttf");
        phone_no.setTypeface(font);
        name.setTypeface(font);
        first_name.setTypeface(font);
        last_name.setTypeface(font);
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
                first_name.setTag("0");
                last_name.setTag("0");



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

        first_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                first_name.setTag("changed");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        last_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                last_name.setTag("changed");

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
                                if (userImage.getTag().equals("UpdatedTag") || first_name.getTag().equals("change") ||name.getTag().equals("changed")
                                        ||last_name.getTag().equals("changed")){
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
