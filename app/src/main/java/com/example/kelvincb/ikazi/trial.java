package com.example.kelvincb.ikazi;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.shashank.sony.fancyaboutpagelib.FancyAboutPage;

public class trial extends AppCompatActivity {

//    ViewPager viewPager;
//    LinearLayout sliderDotspanel;
//    private int dotscount;
//    private ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial);



        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        FancyAboutPage fancyAboutPage=findViewById(R.id.fancyaboutpage);
        fancyAboutPage.setCover(R.drawable.background);
        fancyAboutPage.setCoverTintColor(getResources().getColor(R.color.colorPrimaryDark)); //Optional
        fancyAboutPage.setName("Kevin Mirera");
        fancyAboutPage.setDescription("Google Certified Associate Android Developer | Android App, Game, Web and Software Developer.");
        fancyAboutPage.setAppIcon(R.drawable.cakepop);
        fancyAboutPage.setAppName("4.5 Rating\n30 Tasks Completed");
        fancyAboutPage.setAppDescription("");
        fancyAboutPage.addEmailLink("kevmirera@gmail.com");
        fancyAboutPage.addFacebookLink("https://www.facebook.com/shashanksinghal02");
        fancyAboutPage.addTwitterLink("https://twitter.com/shashank020597");
        fancyAboutPage.addLinkedinLink("https://www.linkedin.com/in/shashank-singhal-a87729b5/");
        fancyAboutPage.addGitHubLink("https://github.com/Shashank02051997");



//        viewPager = (ViewPager) findViewById(R.id.viewPager);
//
//        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
//
//        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
//
//        viewPager.setAdapter(viewPagerAdapter);
//
//        dotscount = viewPagerAdapter.getCount();
//        dots = new ImageView[dotscount];
//
//        for(int i = 0; i < dotscount; i++){
//
//            dots[i] = new ImageView(this);
//            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
//
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//
//            params.setMargins(8, 0, 8, 0);
//
//            sliderDotspanel.addView(dots[i], params);
//
//        }
//
//        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
//
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//                for(int i = 0; i< dotscount; i++){
//                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
//                }
//
//                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

    }



}

