package com.kazzi.Main.mainFragments.userHistory.processedRequestPackage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kazzi.R;
import com.kazzi.fetchPhoneNumber;


public class ProcessedRequest extends Fragment {


    View view;
    private static String SITE_URL = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_processed_request, container, false);

        final fetchPhoneNumber fetchPhoneNumber=new fetchPhoneNumber(getContext());
        fetchPhoneNumber.mynumber();

        String pno=fetchPhoneNumber.getPhone_no();
        SITE_URL = "http://104.248.124.210/android/iKazi/phpFiles/processedRequest.php?phoneNumber=" +pno;

        fetchRequest();

        return view;
    }

    @Override
    public void onResume() {

        final fetchPhoneNumber fetchPhoneNumber=new fetchPhoneNumber(getContext());
        fetchPhoneNumber.mynumber();

        String pno=fetchPhoneNumber.getPhone_no();
        SITE_URL = "http://104.248.124.210/android/iKazi/phpFiles/processedRequest.php?phoneNumber=" +pno;

        fetchRequest();

        super.onResume();
    }

    public void fetchRequest(){
        final ProgressBar myProgressBar = view.findViewById(R.id.processed_progressbar);
        myProgressBar.setVisibility(View.VISIBLE);

        final ListView lv = view.findViewById(R.id.processed_ListView);
        final TextView tv=view.findViewById(R.id.norequesttxt);


        new processedJsonDownloader(getActivity()).retrieveRequestInfo(SITE_URL,lv,tv,myProgressBar);

    }



}
