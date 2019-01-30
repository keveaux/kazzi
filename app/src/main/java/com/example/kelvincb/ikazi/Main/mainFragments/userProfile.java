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
import com.theartofdev.edmodo.cropper.CropImage;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;

import android.text.TextUtils;
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
    EditText name,phone_no;
    Button update;
    ProgressBar progressBar;

    TextView myprofile;

    ImageView userImage;

    private static final String SERVER_ADDRESS="http://ecolaneventures.co.ke/";

    /**
     * Persist URI image to crop URI if specific permissions are required
     */
    private static int RESULT_LOAD_IMG = 1;
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

        new downloadphoto(fetchPhoneNumber.getPhone_no()).execute();

        phone_no.setText(fetchPhoneNumber.getPhone_no());
        phone_no.setEnabled(false);

                final fetchUserName fetchUserName=new fetchUserName(getContext());
                fetchUserName.fetchname();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                name.setText(fetchUserName.getMname());

            }},1000);




        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               checkversions();

            }
        });

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(userImage.getDrawable() == null){
                            Toast.makeText(getActivity(), "you have not added an image", Toast.LENGTH_SHORT).show();
                        }else {
                            Bitmap image=((BitmapDrawable)userImage.getDrawable()).getBitmap();
                            new UploadImage(image,fetchPhoneNumber.getPhone_no()).execute();
                        }


                        if (!TextUtils.isEmpty(name.getText())) {
                            updateProfile();
                        } else {
                            name.setError("Please Enter your Name");
                        }
                    }
                });





        return view;
    }

    private void updateProfile() {
        progressBar.setVisibility(View.VISIBLE);
        String MyURL = "http://104.248.124.210/android/iKazi/phpFiles/updateProfile.php";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, MyURL, new com.android.volley.Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error+"",Toast.LENGTH_LONG).show();

                }
        }){

            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                String user_name=name.getText().toString();
                String user_phone_no=phone_no.getText().toString();
                params.put("name",user_name);
                params.put("phone",user_phone_no);

                return params;
            }

        };

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    private class UploadImage extends AsyncTask<Void,Void,Void> {

        Bitmap image;
        String name;



        public UploadImage(Bitmap image, String name) {

            this.image=image;
            this.name=name;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //compress the image into bytearrayoutputstream and then encode it
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.WEBP,100,byteArrayOutputStream);
            String encodeimage= Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);

            //put the encoded image and its name to a list and send it to the server
            ArrayList<NameValuePair> datatosend=new ArrayList<>();
            datatosend.add(new BasicNameValuePair("image",encodeimage));
            datatosend.add(new BasicNameValuePair("name",name));

            HttpParams httprequestparams=getHttpParams();
            //client helps us send data to the server
            DefaultHttpClient httpClient=new DefaultHttpClient(httprequestparams);
            HttpPost httpPost=new HttpPost(SERVER_ADDRESS+"savepicture.php");


            //post the data to the server
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(datatosend));
                httpClient.execute(httpPost);
            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getActivity(),"image uploaded successfully",Toast.LENGTH_LONG).show();


            super.onPostExecute(aVoid);
        }

    }

    private HttpParams getHttpParams(){
        HttpParams httprequestparams=new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httprequestparams,1000*30);
        HttpConnectionParams.setSoTimeout(httprequestparams,1000*30);
        return httprequestparams;
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


    private class downloadphoto extends AsyncTask<Void,Void,Bitmap> {

        String name;
        public downloadphoto(String name) {
            this.name=name;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            String url=SERVER_ADDRESS+"pictures/"+name+".jpg";

            try {
                //establish a connection to the image we want
                URLConnection urlConnection=new URL(url).openConnection();
                urlConnection.setConnectTimeout(1000*30);
                urlConnection.setReadTimeout(1000*30);
                //convert it into an input stream and decode it which returns the bitmap
                return BitmapFactory.decodeStream((InputStream)urlConnection.getContent(),null,null);
            }catch (Exception e){
                e.printStackTrace();
                return null;

            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            userImage.setImageBitmap(bitmap);
            super.onPostExecute(bitmap);
        }
    }


}
