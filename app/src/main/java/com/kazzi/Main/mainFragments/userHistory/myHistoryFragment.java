package com.kazzi.Main.mainFragments.userHistory;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kazzi.Main.mainFragments.userHistory.processedRequestPackage.ProcessedRequest;
import com.kazzi.Main.mainFragments.userHistory.processingRequest.inProcessRequest;
import com.kazzi.R;

import java.util.ArrayList;
import java.util.List;


public class myHistoryFragment extends Fragment {


    private TabLayout tabLayout;
    private ViewPager viewPager;
//    private int[] tabIcons = {
//            R.drawable.inprocess,
//            R.drawable.processed,
//    };

    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_my_history, container, false);

        viewPager =  view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout =  view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        return view;
    }





    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabOne.setText("PROCESSED");
        Typeface font=Typeface.createFromAsset(getActivity().getAssets(),"Roboto-Bold.ttf");
        tabOne.setTypeface(font);
//        tabOne.setCompoundDrawablesWithIntrinsicBounds(R.drawable.processed, 0, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);
        tabLayout.setScrollBarSize((int) getActivity().getResources().getDimension(R.dimen.swipetabssize));

        TextView tabTwo = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabTwo.setText("PROCESSING");
        Typeface font2=Typeface.createFromAsset(getActivity().getAssets(),"Roboto-Bold.ttf");
        tabTwo.setTypeface(font2);
//        tabTwo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.inprocess, 0, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);


    }

    private void setupViewPager(ViewPager viewPager) {
        myHistoryFragment.ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new ProcessedRequest(), "ONE");
        adapter.addFrag(new inProcessRequest(), "TWO");
        viewPager.setAdapter(adapter);
    }



    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);

        }



}}
