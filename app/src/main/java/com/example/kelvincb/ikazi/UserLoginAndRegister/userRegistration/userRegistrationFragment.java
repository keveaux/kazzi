package com.example.kelvincb.ikazi.UserLoginAndRegister.userRegistration;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Base64;
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
import com.example.kelvincb.ikazi.UserLoginAndRegister.userLogin.firebaseStuff.beforeLogin;
import com.example.kelvincb.ikazi.R;
import com.theartofdev.edmodo.cropper.CropImage;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class userRegistrationFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    View view;
    TextView loginLink,appname,signuptxt;
    Button signup;
    EditText nameET,phoneET;
    ProgressBar progressBar;
    ImageView user_profile_photo;
    private static final String SERVER_ADDRESS="http://ecolaneventures.co.ke/";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.activity_user_registration, container, false);

        user_profile_photo=view.findViewById(R.id.user_profile_photo2);

        progressBar=view.findViewById(R.id.reg_progressBar);

        appname=view.findViewById(R.id.appname);
        signuptxt=view.findViewById(R.id.signuptxt);

        nameET=view.findViewById(R.id.input_name_reg);
        phoneET=view.findViewById(R.id.input_phone_number_reg);
        loginLink=view.findViewById(R.id.link_login);


        signup=view.findViewById(R.id.btn_signup);

        Typeface font1 = Typeface.createFromAsset(getActivity().getAssets(), "RobotoSlab-Bold.ttf");


        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "RobotoSlab-Light.ttf");
        appname.setTypeface(font1);
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
                        sendData();
                        Bitmap image=((BitmapDrawable)user_profile_photo.getDrawable()).getBitmap();

                        new UploadImage(image,phoneET.getText().toString());
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

    private void sendData() {
        progressBar.setVisibility(View.VISIBLE);
        String MyURL="http://104.248.124.210/android/iKazi/phpFiles/sendRegistrationDetails.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, MyURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                if (response.equals("phone number already exists")) {
                    Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                    phoneET.setError(""+response);

                }else if(response.equals("successful")){
//                    Intent intent = new Intent(getActivity().getBaseContext(),
//                            LoginRegisterActivity.class);
//
//                    getActivity().startActivity(intent);\

                    Bundle bundle = new Bundle();
                    bundle.putString("key",phoneET.getText().toString()); // Put anything what you want

                    //mStatusText.setText("Authenticating....!");
                    beforeLogin fragment2=new beforeLogin();
                    fragment2.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.userFragmentContainer, fragment2,"findThisFragment")
                            .addToBackStack(null)
                            .commit();
                }
                Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error+"",Toast.LENGTH_LONG).show();


            }
        }){

            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                String user_name=nameET.getText().toString();
                String user_phone_no=phoneET.getText().toString();
                params.put("name",user_name);
                params.put("phone",user_phone_no);

                return params;
            }

        };

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

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


}
