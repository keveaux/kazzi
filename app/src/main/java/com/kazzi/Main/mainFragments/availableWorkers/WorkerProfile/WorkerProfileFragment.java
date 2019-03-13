package com.kazzi.Main.mainFragments.availableWorkers.WorkerProfile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kazzi.R;
import com.kazzi.mPicasso.PicassoClient;
import com.viewpagerindicator.CirclePageIndicator;

public class WorkerProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private String[] imageUrls ;

    View view;
    String worker_Id,worker_name,occupation,skillset,rating,url,job_url_one,job_url_two,
            job_url_three,facebook,twitter,linkedin,github,email;
    TextView nameTV,occupationTV,skillTV,ratingTV;
    ImageView workerImage,emailIcon,facebookIcon,twitterIcon,linkedinIcon,githubIcon;

    private static String SITE_URL = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        view= inflater.inflate(R.layout.fragment_worker_profile, container, false);
        nameTV=view.findViewById(R.id.name);
        occupationTV=view.findViewById(R.id.occupation);
        skillTV=view.findViewById(R.id.description);
        ratingTV=view.findViewById(R.id.rating);
        workerImage=view.findViewById(R.id.circularWorkerImageView);
        facebookIcon=view.findViewById(R.id.facebook);
        twitterIcon=view.findViewById(R.id.twitter);
        githubIcon=view.findViewById(R.id.github);
        linkedinIcon=view.findViewById(R.id.linkedin);
        emailIcon=view.findViewById(R.id.email);


        worker_Id= getArguments().getString("id");
        worker_name=getArguments().getString("name");
        occupation=getArguments().getString("occupation");
        skillset=getArguments().getString("skill");
        rating=getArguments().getString("rating");
        url=getArguments().getString("url");
        job_url_one=getArguments().getString("url_one");
        job_url_two=getArguments().getString("url_two");
        job_url_three=getArguments().getString("url_three");
        facebook=getArguments().getString("facebook");
        twitter=getArguments().getString("twitter");
        linkedin=getArguments().getString("linkedin");
        github=getArguments().getString("github");
        email=getArguments().getString("email");

        emailIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

                /* Fill it with Data */
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{email});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");

                /* Send it off to the Activity-Chooser */
                startActivity(Intent.createChooser(emailIntent, "click on Gmail to send an email"));
            }
        });
        facebookIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://mobile.facebook.com/"+facebook));
                startActivity(intent);
            }
        });
        twitterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/"+twitter));
                startActivity(intent);
            }
        });
        linkedinIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/"+linkedin+"/"));
                startActivity(intent);
            }
        });
        githubIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/"+github));
                startActivity(intent);
            }
        });





        nameTV.setText(worker_name);
        nameTV.setAllCaps(true);
        occupationTV.setText(occupation);
        skillTV.setText(skillset);
        ratingTV.setText(rating+" Rating");

        PicassoClient.loadImage(url,workerImage);

        SITE_URL = "http://104.248.124.210/android/iKazi/phpFiles/fetchComments.php?id=" +worker_Id;



        imageUrls= new String[]{
                job_url_one,
                job_url_two,
                job_url_three
        };

        //initiate view pager
        ViewPager viewPager =view.findViewById(R.id.view_pager);
            ViewPagerAdapter adapter = new ViewPagerAdapter(getContext(), imageUrls);
        viewPager.setAdapter(adapter);

        CirclePageIndicator indicator =
                view.findViewById(R.id.indicator);

        indicator.setViewPager(viewPager);

        fetchRequest();


        return view;
    }


    public void fetchRequest(){


        TextView tasksdone=view.findViewById(R.id.tasksdone);
        RecyclerView lv=view.findViewById(R.id.comentsection);
        ProgressBar myProgressBar=view.findViewById(R.id.workerprofileprogressBar);
        new CommentsJsonDownloader(getActivity()).retrieveRequestInfo(SITE_URL,lv,tasksdone,myProgressBar);

    }




//    private void init() {
//
//        mPager = view.findViewById(R.id.pager);
//
//        mPager.setAdapter(new SlidingImage_Adapter(getContext(),imageModelArrayList));
//
//        CirclePageIndicator indicator =
//                view.findViewById(R.id.indicator);
//
//        indicator.setViewPager(mPager);
//
//        final float density = getResources().getDisplayMetrics().density;
//
////Set circle indicator radius
//        indicator.setRadius(5 * density);
//
//        NUM_PAGES =imageModelArrayList.size();
//
//        // Auto start of viewpager
////        final Handler handler = new Handler();
////        final Runnable Update = new Runnable() {
////            public void run() {
////                if (currentPage == NUM_PAGES) {
////                    currentPage = 0;
////                }
////                mPager.setCurrentItem(currentPage++, true);
////            }
////        };
////        Timer swipeTimer = new Timer();
////        swipeTimer.schedule(new TimerTask() {
////            @Override
////            public void run() {
////                handler.post(Update);
////            }
////        }, 3000, 3000);
//
//        // Pager listener over indicator
//        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//            @Override
//            public void onPageSelected(int position) {
//                currentPage = position;
//
//            }
//
//            @Override
//            public void onPageScrolled(int pos, float arg1, int arg2) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int pos) {
//
//            }
//        });
//
//    }


}
