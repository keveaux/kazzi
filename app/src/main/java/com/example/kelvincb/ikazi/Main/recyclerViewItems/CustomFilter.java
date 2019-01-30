package com.example.kelvincb.ikazi.Main.recyclerViewItems;


import android.widget.Filter;

import java.util.ArrayList;

public class CustomFilter extends Filter{

    MyAdapter adapter;
    ArrayList<Worker> filterList;

    public CustomFilter(ArrayList<Worker> filterList, MyAdapter adapter)
    {
        this.adapter=adapter;
        this.filterList=filterList;

    }

    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<Worker> filteredWorkers =new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getName().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredWorkers.add(filterList.get(i));
                }
            }

            results.count= filteredWorkers.size();
            results.values= filteredWorkers;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;

        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.workers = (ArrayList<Worker>) results.values;

        //REFRESH
        adapter.notifyDataSetChanged();
    }
}

