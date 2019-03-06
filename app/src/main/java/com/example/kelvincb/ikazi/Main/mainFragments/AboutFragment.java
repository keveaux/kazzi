package com.example.kelvincb.ikazi.Main.mainFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kelvincb.ikazi.R;

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
        

        String description=getActivity().getResources().getString(R.string.slide_1_desc);
        View aboutPage = new AboutPage(getContext())
                .isRTL(false)
                .addItem(versionElement)
                .addItem(adelement)
                .setDescription(description)
                .setImage(R.drawable.ic_launcher_background)

                .addGroup("Connect with us")
                .addEmail("elmehdi.sakout@gmail.com")
                .addWebsite("http://medyo.github.io/")
                .addFacebook("the.medy")
                .addTwitter("medyo80")
//                .addYoutube("UCdPQtdWIsg7_pi4mrRu46vA")
                .addPlayStore("com.ideashower.readitlater.pro")
//                .addGitHub("medyo")
                .addInstagram("medyo80")

                .create();


        return aboutPage;
    }

}
