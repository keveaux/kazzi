package com.example.kelvincb.ikazi.Main.mainFragments.sendRequestPackage;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kelvincb.ikazi.R;
import com.example.kelvincb.ikazi.fetchPhoneNumber;
import com.example.kelvincb.ikazi.fetchUserName;
import com.example.kelvincb.ikazi.mPicasso.PicassoClient;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class sendRequest extends Fragment {


    private OnFragmentInteractionListener mListener;
    EditText etDate,etTime,jobDesc, etlandmark;
    ImageView workerImage;
    View view;
    Button sendRequest;
    Typeface font;
    TextView workerName,skillset,occupationTV;
    ProgressBar progressBar;
    String user_name,user_phone_no,name,skill,occupation,token,imageurl;
    String workerId ,location;
    private InterstitialAd mInterstitialAd;
    String formattedDate;




    public sendRequest() {
        // Required empty public constructor
    }


    @Override
    public void onStart() {
        super.onStart();
        etDate =view.findViewById(R.id.dateET);
        etDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    DateDialog dialog=new DateDialog();
                    dialog.DateDialog2(v);
                    FragmentTransaction ft=getFragmentManager().beginTransaction();
                    dialog.show(ft,"DatePicker");
                }
            }
        });
        etTime=view.findViewById(R.id.timeET);

        etTime.setTypeface(font);
        etDate.setTypeface(font);

        etTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){

                    // Get Current Time
                    final Calendar c = Calendar.getInstance();
                    int mHour = c.get(Calendar.HOUR_OF_DAY);
                    int mMinute = c.get(Calendar.MINUTE);

                    // Launch Time Picker Dialog
                    TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {

                                    etTime.setText(hourOfDay + ":" + minute);
                                }
                            }, mHour, mMinute, false);
                    timePickerDialog.show();
                }
            }
        });


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_send_request, container, false);



        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
         formattedDate = df.format(c);



        final fetchPhoneNumber fetchPhoneNumber=new fetchPhoneNumber(getContext());
        fetchPhoneNumber.mynumber();

        final fetchUserName fetchUserName=new fetchUserName(getContext());
        fetchUserName.fetchname();


        workerId= getArguments().getString("id");

        name=getArguments().getString("name");
        location=getArguments().getString("location");
        token=getArguments().getString("token");
        imageurl=getArguments().getString("url");
        skill=getArguments().getString("skill");
        occupation=getArguments().getString("occupation");


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                user_name = fetchUserName.getMname();
                user_phone_no = fetchPhoneNumber.getPhone_no();

            }
        }, 2000);


        jobDesc=view.findViewById(R.id.jobDescET);
        etlandmark =view.findViewById(R.id.landmarkET);
        sendRequest=view.findViewById(R.id.btn_send_request);
        progressBar=view.findViewById(R.id.send_request_progressBar);
        workerImage=view.findViewById(R.id.worker_profile_image);
        workerName=view.findViewById(R.id.workername);
        skillset=view.findViewById(R.id.description);
        occupationTV=view.findViewById(R.id.proffession);


        PicassoClient.loadImage(imageurl,workerImage);
        workerName.setText(name);
        skillset.setText(skill);
        occupationTV.setText(occupation);






        font=Typeface.createFromAsset(getActivity().getAssets(),"Roboto-Light.ttf");

        jobDesc.setTypeface(font);
        etlandmark.setTypeface(font);
        sendRequest.setTypeface(font);

        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(etDate.getText()) && !TextUtils.isEmpty(etTime.getText())
                        && !TextUtils.isEmpty(etlandmark.getText()) &&
                        !TextUtils.isEmpty(jobDesc.getText()) ){

                    sendData();

                }else if(TextUtils.isEmpty(etDate.getText())){
                    etDate.setError("required");
                }
                else if(TextUtils.isEmpty(etTime.getText())){
                    etTime.setError("required");
                }
                else if(TextUtils.isEmpty(etlandmark.getText())){
                    etlandmark.setError("required");
                }else {
                    jobDesc.setError("required");
                }
            }
        });
        return view;
    }

    private void sendData() {


        progressBar.setVisibility(View.VISIBLE);
        String MyURL="http://104.248.124.210/android/iKazi/phpFiles/sendRequest.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, MyURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);

                Toast.makeText(getActivity(), ""+response, Toast.LENGTH_SHORT).show();

                if(response.equals("successful")){
                    Toast.makeText(getContext(), "Request sent wait for confirmation", Toast.LENGTH_SHORT).show();


                    showDialog(getActivity(), "SUCCESSFUL\n Request sent to "+name+"\nPlease wait for response");


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error+"",Toast.LENGTH_LONG).show();
                error.printStackTrace();

            }
        }){

            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();


                String date = etDate.getText().toString();
                String time = etTime.getText().toString();
                String jobDesctiption = jobDesc.getText().toString();
                String landmark = etlandmark.getText().toString();

                params.put("name", user_name);
                params.put("phone", user_phone_no);
                params.put("date", date);
                params.put("time", time);
                params.put("job", jobDesctiption);
                params.put("location",location);
                params.put("landmark", landmark);
                params.put("workerId",workerId);
                params.put("status","0");
                params.put("currentdate",formattedDate);
                params.put("token",token);
                params.put("datehandled","");


                return params;


            }

        };

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


    }

    public void showDialog(Activity activity, String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog);



        TextView text = dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        Typeface font1=Typeface.createFromAsset(getActivity().getAssets(),"Roboto-Bold.ttf");
        text.setTypeface(font1);

        Button dialogButton =  dialog.findViewById(R.id.btn_dialog);
        dialogButton.setTypeface(font1);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                }

                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        Toast.makeText(getActivity(), "closed", Toast.LENGTH_SHORT).show();
                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
                        }
                        }

                );
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                }

                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
                        }
                        }

                );

            }
        });

        dialog.show();

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
