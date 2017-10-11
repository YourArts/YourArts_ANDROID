package com.yg.yourexhibit.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitDetailImages;

import java.util.ArrayList;

/**
 * Created by 2yg on 2017. 10. 11..
 */

public class TabEndDetailAdapter extends RecyclerView.Adapter<TabEndDetailViewHolder>{

    private ArrayList<ExhibitDetailImages> previewList;
    private RequestManager requestManager;

    public TabEndDetailAdapter(ArrayList<ExhibitDetailImages> previewList, RequestManager requestManager) {
        this.previewList = previewList;
        this.requestManager = requestManager;
    }


    @Override
    public TabEndDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_home_end_details_items, parent,false);
        TabEndDetailViewHolder viewHolder = new TabEndDetailViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TabEndDetailViewHolder holder, int position) {
        requestManager.load(previewList.get(position).getUrl()).into(holder.preView);
    }

    @Override
    public int getItemCount() {
        return (previewList !=null)? previewList.size() : 0;
    }
}
