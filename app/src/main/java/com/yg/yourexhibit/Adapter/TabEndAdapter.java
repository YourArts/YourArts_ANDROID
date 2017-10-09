package com.yg.yourexhibit.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.yg.yourexhibit.App.ApplicationController;
import com.yg.yourexhibit.Datas.TabEndData;
import com.yg.yourexhibit.R;

import java.util.ArrayList;

/**
 * Created by 2yg on 2017. 10. 9..
 */

public class TabEndAdapter extends RecyclerView.Adapter<TabEndViewHolder>{

    private ArrayList<TabEndData> endResult;
    private RequestManager requestManager;

    public TabEndAdapter(ArrayList<TabEndData> endResult, RequestManager requestManager) {
        this.endResult = endResult;
        Log.v("어댑터", endResult.toString());
        this.requestManager = requestManager;
    }

    @Override
    public TabEndViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_home_end_items, parent,false);
        TabEndViewHolder viewHolder = new TabEndViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TabEndViewHolder holder, final int position) {
            requestManager.load(endResult.get(position).getExhibitImage()).into(holder.endItemImage);
            holder.endItemPerioid.setText(endResult.get(position).getExhibitPeriod());
            holder.endItemName.setText(endResult.get(position).getExhibitName());
            holder.endItemBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ApplicationController.getInstance().makeToast(endResult.get(position).getExhibitName());
                }
            });
    }

    @Override
    public int getItemCount() {
        return (endResult != null) ? endResult.size():0;
    }
}
