package com.kazzi.Main.mainFragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.kazzi.R;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;


public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Element versionElement = new Element();
        versionElement.setTitle("Version 1.0.0");

        Element adelement=new Element();
        adelement.setTitle("Advertise Job Openings");
        adelement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Element signup=new Element();
        signup.setTitle("Sign Up as a worker");
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        Element hireus=new Element();
        hireus.setTitle("Hire us to make an app");
        hireus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "+25422146246", null));
                startActivity(intent);
            }
        });



        String description=getActivity().getResources().getString(R.string.slide_1_desc);
        View aboutPage = new AboutPage(getContext())
                .isRTL(false)
                .addItem(versionElement)
                .addItem(signup)
                .addItem(adelement)
                .addItem(hireus )
                .setDescription(description)
                .addGroup("Connect with us")
                .addEmail("creativebrandslimited@gmail.com")
                .addWebsite("http://kazzi.co.ke")
                .addFacebook("kazzi")
                .addTwitter("kazzi")
                .addPlayStore("")
//                .addGitHub("medyo")
                .addInstagram("kazzi")

                .create();


        return aboutPage;
    }

}
