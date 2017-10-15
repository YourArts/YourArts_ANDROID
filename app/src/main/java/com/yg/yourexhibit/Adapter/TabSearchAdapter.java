package com.yg.yourexhibit.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitSearchResponse;

import java.util.ArrayList;

/**
 * Created by 2yg on 2017. 10. 13..
 */

public class TabSearchAdapter extends RecyclerView.Adapter<TabSearchViewHolder>{

    private ArrayList<ExhibitSearchResponse> searchList;
    private View.OnClickListener onItemClick = null;

    public TabSearchAdapter(ArrayList<ExhibitSearchResponse> searchList, View.OnClickListener onItemClick) {
        this.searchList = searchList;
        this.onItemClick = onItemClick;
    }


    @Override
    public TabSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_search_items, parent,false);
        TabSearchViewHolder viewHolder = new TabSearchViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TabSearchViewHolder holder, int position) {
        holder.search.setText(searchList.get(position).getExhibition_name());
    }

    @Override
    public int getItemCount() {
        return (searchList!=null)? searchList.size() : 0;
    }
}
