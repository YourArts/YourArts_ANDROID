package com.yg.yourexhibit.Adapter.Home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.yg.yourexhibit.Datas.TabComingData;
import com.yg.yourexhibit.R;

import java.util.ArrayList;

/**
 * Created by 2yg on 2017. 10. 10..
 */

public class TabComingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<TabComingData> comingResult;
    private RequestManager requestManager;
    private View.OnClickListener onItemClick = null;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;


    public TabComingAdapter(ArrayList<TabComingData> comingResult, RequestManager requestManager, View.OnClickListener onItemClick) {
        this.comingResult = comingResult;
        this.requestManager = requestManager;
        this.onItemClick = onItemClick;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == TYPE_HEADER) {
            //View itemView = LayoutInflater.from (parent.getContext()).inflate (R.layout.tab_home_items_header, parent, false);
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_home_items_header, parent,false);
            HomeHeaderViewHolder viewHolder = new HomeHeaderViewHolder(itemView);

            return viewHolder;
        }else if(viewType == TYPE_ITEM){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_home_coming_items, parent,false);
            TabComingViewHolder viewHolder = new TabComingViewHolder(itemView);

            itemView.setOnClickListener(onItemClick);

            return viewHolder;
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof HomeHeaderViewHolder) {
            HomeHeaderViewHolder headerViewHolder = (HomeHeaderViewHolder) holder;

        }else if(holder instanceof TabComingViewHolder) {
            TabComingViewHolder tabComingViewHolder = (TabComingViewHolder) holder;
            requestManager
                    .load(comingResult.get(position - 1).getExhibitImage())
                    .centerCrop()
                    .into(tabComingViewHolder.comingItemImage);

            tabComingViewHolder.comingItemPerioid.setText(comingResult.get(position - 1).getExhibitPeriod());
            tabComingViewHolder.comingItemName.setText(comingResult.get(position - 1).getExhibitName());

        }
    }
    @Override
    public int getItemViewType(int position) {
        if(position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }
    @Override
    public int getItemCount() {
        return (comingResult != null) ? comingResult.size()+1:0;
    }
}
