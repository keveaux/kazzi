package com.example.kelvincb.ikazi.WorkerProfile;

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

import com.example.kelvincb.ikazi.R;
import com.example.kelvincb.ikazi.mPicasso.PicassoClient;
import com.viewpagerindicator.CirclePageIndicator;

public class WorkerProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private String[] imageUrls ;

    View view;
    String worker_Id,worker_name,occupation,skillset,rating,url,job_url_one,job_url_two,job_url_three;
    TextView nameTV,occupationTV,skillTV,ratingTV;
    ImageView workerImage;

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

        worker_Id= getArguments().getString("id");
        worker_name=getArguments().getString("name");
        occupation=getArguments().getString("occupation");
        skillset=getArguments().getString("skill");
        rating=getArguments().getString("rating");
        url=getArguments().getString("url");
        job_url_one=getArguments().getString("url_one");
        job_url_two=getArguments().getString("url_two");
        job_url_three=getArguments().getString("url_three");


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
