package com.yg.yourexhibit.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.yg.yourexhibit.App.ApplicationController;
import com.yg.yourexhibit.Datas.TabComingData;
import com.yg.yourexhibit.R;

import java.util.ArrayList;

/**
 * Created by 2yg on 2017. 10. 10..
 */

public class TabComingAdapter extends RecyclerView.Adapter<TabComingViewHolder>{

    private ArrayList<TabComingData> comingResult;
    private RequestManager requestManager;

    public TabComingAdapter(ArrayList<TabComingData> comingResult, RequestManager requestManager) {
        this.comingResult = comingResult;
        this.requestManager = requestManager;
    }


    @Override
    public TabComingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_home_coming_items, parent,false);
        TabComingViewHolder viewHolder = new TabComingViewHolder(itemView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(TabComingViewHolder holder, final int position) {
        requestManager.load(comingResult.get(position).getExhibitImage()).into(holder.comingItemImage);
        holder.comingItemPerioid.setText(comingResult.get(position).getExhibitPeriod());
        holder.comingItemName.setText(comingResult.get(position).getExhibitName());
        holder.comingItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationController.getInstance().makeToast(comingResult.get(position).getExhibitName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return (comingResult != null) ? comingResult.size():0;
    }
}
