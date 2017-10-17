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
 * Created by 2yg on 2017. 10. 17..
 */

public class TabGoingPreviewFirstAdapter extends RecyclerView.Adapter<TabGoingPreviewViewHolder>{

    private ArrayList<ExhibitDetailImages> previewList;
    private RequestManager requestManager;
    private View.OnClickListener onItemClick;

    public TabGoingPreviewFirstAdapter(ArrayList<ExhibitDetailImages> previewList, RequestManager requestManager, View.OnClickListener onItemClick) {
        this.previewList = previewList;
        this.requestManager = requestManager;
        this.onItemClick = onItemClick;
    }

    @Override
    public TabGoingPreviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_home_going_preview_items, parent,false);
        TabGoingPreviewViewHolder viewHolder = new TabGoingPreviewViewHolder(itemView);
        itemView.setOnClickListener(onItemClick);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TabGoingPreviewViewHolder holder, int position) {
        requestManager.load(previewList.get(position).getWork_image()).into(holder.preView);
    }

    @Override
    public int getItemCount() {
        return (previewList!=null)? previewList.size() : 0;
    }
}
