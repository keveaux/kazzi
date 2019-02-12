package com.example.kelvincb.ikazi.UserLoginAndRegister.userLogin.firebaseStuff;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.kelvincb.ikazi.Main.MainActivity;
import com.example.kelvincb.ikazi.R;
import com.example.kelvincb.ikazi.UserLoginAndRegister.userRegistration.userRegistrationFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;


public class beforeLogin extends Fragment {

    View view;
    TextView details,registerlink;
    EditText phone_no;
    Button next;
    ProgressBar progressBar;
    public static String token = "";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_before_login, container, false);

        token= FirebaseInstanceId.getInstance().getToken();

        details=view.findViewById(R.id.detes);
        phone_no=view.findViewById(R.id.phonenumber);
        next=view.findViewById(R.id.submit1);
        registerlink=view.findViewById(R.id.register_link);
        progressBar=view.findViewById(R.id.progressBarbeforeLogin);




        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Light.ttf");
        details.setTypeface(font);
        phone_no.setTypeface(font);
        next.setTypeface(font);
        registerlink.setTypeface(font);


        if(FirebaseAuth.getInstance().getCurrentUser()==null){

        }else {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.putExtra("EXTRA","openMain");
            startActivity(intent);
            getActivity().finish();
        }


        Bundle bundle = this.getArguments();

        if(bundle != null){
            String strtext=getArguments().getString("key");

            phone_no.setText(strtext);

        }


        registerlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRegistrationFragment fragment2=new userRegistrationFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.userFragmentContainer, fragment2,"findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phone_no.getText().toString().isEmpty()){
                    phone_no.setError("enter a valid phone number");
                }else {
                    checkPhoneNumberisRegistered();
                }
            }
        });

        return view;
    }

    public void checkPhoneNumberisRegistered(){
        progressBar.setVisibility(View.VISIBLE);

        String MyURL="http://104.248.124.210/android/iKazi/phpFiles/checkPhoneNumber.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, MyURL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if(response.equals("yes")){



                    Bundle bundle = new Bundle();
                    bundle.putString("key",phone_no.getText().toString()); // Put anything what you want

                    //mStatusText.setText("Authenticating....!");
                    userLogin fragment2=new userLogin();
                    fragment2.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.userFragmentContainer, fragment2,"findThisFragment")
                            .addToBackStack(null)
                            .commit();



                }else if(response.equals("no")){
                    progressBar.setVisibility(View.GONE);
                    phone_no.setError("Please Register first");
                    Toast.makeText(getContext(), "Please Register", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error+"",Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);

            }
        }){

            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                String user_phone_no=phone_no.getText().toString();
                params.put("phone",user_phone_no);
                params.put("token",token);

                return params;
            }

        };

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
