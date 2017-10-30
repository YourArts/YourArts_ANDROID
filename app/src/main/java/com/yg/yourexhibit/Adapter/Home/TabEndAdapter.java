package com.yg.yourexhibit.Adapter.Home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.yg.yourexhibit.Datas.TabEndData;
import com.yg.yourexhibit.R;

import java.util.ArrayList;

/**
 * Created by 2yg on 2017. 10. 9..
 */

public class TabEndAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<TabEndData> endResult;
    private RequestManager requestManager;
    private View.OnClickListener onItemClick = null;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public TabEndAdapter(ArrayList<TabEndData> endResult, RequestManager requestManager, View.OnClickListener onItemClick) {
        this.endResult = endResult;
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
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_home_end_items, parent,false);
            TabEndViewHolder viewHolder = new TabEndViewHolder(itemView);

            itemView.setOnClickListener(onItemClick);

            return viewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        if(holder instanceof HomeHeaderViewHolder) {
            HomeHeaderViewHolder headerViewHolder = (HomeHeaderViewHolder) holder;

        }else if(holder instanceof TabEndViewHolder) {
            TabEndViewHolder tabEndViewHolder = (TabEndViewHolder) holder;
            requestManager
                    .load(endResult.get(position - 1).getExhibitImage())
                    .centerCrop()
                    .into(tabEndViewHolder.endItemImage);

            tabEndViewHolder.endItemPerioid.setText(endResult.get(position - 1).getExhibitPeriod());
            tabEndViewHolder.endItemName.setText(endResult.get(position - 1).getExhibitName());

        }
    }

    public void setOnItemClickListener(View.OnClickListener l){
        onItemClick = l;
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
        return (endResult != null) ? endResult.size()+1:0;
    }
}
