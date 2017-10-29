package com.yg.yourexhibit.Adapter.Home;

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

public class TabComingDetailAdapter extends RecyclerView.Adapter<TabDetailViewHolder>{
    private ArrayList<ExhibitDetailImages> previewList;
    private RequestManager requestManager;

    public TabComingDetailAdapter(ArrayList<ExhibitDetailImages> previewList, RequestManager requestManager) {
        this.previewList = previewList;
        this.requestManager = requestManager;
    }


    @Override
    public TabDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_home_details_items, parent,false);
        TabDetailViewHolder viewHolder = new TabDetailViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TabDetailViewHolder holder, int position) {
        requestManager
                .load(previewList.get(position).getWork_image())
                .centerCrop()
                .into(holder.preView);
    }

    @Override
    public int getItemCount() {
        return (previewList != null)? previewList.size() : 0;
    }
}
