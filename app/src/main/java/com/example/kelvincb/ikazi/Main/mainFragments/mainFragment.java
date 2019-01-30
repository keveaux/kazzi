package com.example.kelvincb.ikazi.Main.mainFragments;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kelvincb.ikazi.Main.recyclerViewItems.MyAdapter;
import com.example.kelvincb.ikazi.Main.recyclerViewItems.Worker;

import com.example.kelvincb.ikazi.R;
import com.example.kelvincb.ikazi.fetchUserName;

import java.util.ArrayList;


public class mainFragment extends Fragment {
    private RecyclerView rv;

    ProgressBar progressBar;
    View view;
    SearchView sv;
    private View rootView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_main, container, false);

        rootView = view.findViewById(R.id.root_layout);
        rootView.requestFocus();

        if (getActivity()==null){

        }

        progressBar=view.findViewById(R.id.mainprogressBar);
        progressBar.getProgressDrawable().setColorFilter(
                Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);



        final fetchUserName fetchUserName=new fetchUserName(getContext());
        fetchUserName.fetchname();

        final TextView welcome=view.findViewById(R.id.welcometxt);

        Typeface font=Typeface.createFromAsset(getActivity().getAssets(),"RobotoSlab-Bold.ttf");
        welcome.setTypeface(font);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                progressBar.setVisibility(View.GONE);


                welcome.setText("Hey "+fetchUserName.getMname()+" let us help you look for a worker");


            }
        }, 1000);


        rv =  view.findViewById(R.id.recycler_view);
        sv= view.findViewById(R.id.mSearch);

        //SET ITS PROPETRIES
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        rv.setLayoutManager(mLayoutManager);
        rv.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        rv.setItemAnimator(new DefaultItemAnimator());

        //ADAPTER
        final MyAdapter adapter=new MyAdapter(getActivity(), getWorkers());



        rv.setAdapter(adapter);

        //SEARCH
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //FILTER AS YOU TYPE
                adapter.getFilter().filter(query);
                if(adapter.getItemCount()==0){

                    ImageView noworker = view.findViewById(R.id.noworker);
                    noworker.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });


        return view;
    }



    //ADD PLAYERS TO ARRAYLIST
    private ArrayList<Worker> getWorkers()
    {

        ArrayList<Worker> workers =new ArrayList<>();
        Worker p=new Worker();
        p.setName("Phone Repair");
        p.setImg(R.drawable.phonerepair);
        workers.add(p);

        p=new Worker();
        p.setName("Painter");
        p.setImg(R.drawable.painter);
        workers.add(p);

        p=new Worker();
        p.setName("Plumber");
        p.setImg(R.drawable.plumber2);
        workers.add(p);

        p=new Worker();
        p.setName("Wifi Installer");
        p.setImg(R.drawable.wifi);
        workers.add(p);

        p=new Worker();
        p.setName("Electronics Repair");
        p.setImg(R.drawable.eletronicsrepair);
        workers.add(p);

        p=new Worker();
        p.setName("Electronics Repair");
        p.setImg(R.drawable.eletronicsrepair);
        workers.add(p);

        return workers;
    }






    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
