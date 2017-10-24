package com.yg.yourexhibit.Adapter.Home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.squareup.picasso.Picasso;
import com.yg.yourexhibit.App.ApplicationController;
import com.yg.yourexhibit.Datas.TabGoingData;
import com.yg.yourexhibit.R;

import java.util.ArrayList;

/**
 * Created by 2yg on 2017. 10. 9..
 */

public class TabGoingAdapter extends RecyclerView.Adapter<TabGoingViewHolder> {

    private ArrayList<TabGoingData> goingResult;
    private RequestManager requestManager;
    private View.OnClickListener onItemClick = null;

    public TabGoingAdapter(ArrayList<TabGoingData> goingResult, RequestManager requestManager, View.OnClickListener onItemClick) {
        this.goingResult = goingResult;
        this.requestManager = requestManager;
        this.onItemClick = onItemClick;
    }

    @Override
    public TabGoingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_home_going_items, parent,false);
        TabGoingViewHolder viewHolder = new TabGoingViewHolder(itemView);

        itemView.setOnClickListener(onItemClick);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TabGoingViewHolder holder, final int position) {
        //requestManager.load(goingResult.get(position).getExhibitImage()).into(holder.goingItemImage);
        Picasso.with(ApplicationController.getInstance().getApplicationContext())
                .load(goingResult.get(position).getExhibitImage())
                .resize(360, 500)
                .into(holder.goingItemImage);
        holder.goingItemPerioid.setText(goingResult.get(position).getExhibitPeriod());
        holder.goingItemName.setText(goingResult.get(position).getExhibitName());
    }

    @Override
    public int getItemCount() {
        return (goingResult != null) ? goingResult.size():0;

    }

}
