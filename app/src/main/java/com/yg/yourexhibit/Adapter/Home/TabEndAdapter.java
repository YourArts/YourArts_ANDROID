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

public class TabEndAdapter extends RecyclerView.Adapter<TabEndViewHolder>{

    private ArrayList<TabEndData> endResult;
    private RequestManager requestManager;
    private View.OnClickListener onItemClick = null;


    public TabEndAdapter(ArrayList<TabEndData> endResult, RequestManager requestManager, View.OnClickListener onItemClick) {
        this.endResult = endResult;
        this.requestManager = requestManager;
        this.onItemClick = onItemClick;
    }

    @Override
    public TabEndViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_home_end_items, parent,false);
        TabEndViewHolder viewHolder = new TabEndViewHolder(itemView);

        itemView.setOnClickListener(onItemClick);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TabEndViewHolder holder, final int position) {
        requestManager
                .load(endResult.get(position).getExhibitImage())
                .centerCrop()
                .into(holder.endItemImage);
//        Picasso.with(ApplicationController.getInstance().getApplicationContext())
//                .load(endResult.get(position).getExhibitImage())
//                .centerCrop()
//                .fit()
//                .into(holder.endItemImage);


            holder.endItemPerioid.setText(endResult.get(position).getExhibitPeriod());
            holder.endItemName.setText(endResult.get(position).getExhibitName());
//            holder.endItemBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ApplicationController.getInstance().makeToast(endResult.get(position).getExhibitName());
//                    //Intent intent = new Intent(getApplicationContext(), )
//                }
//            });
    }

    public void setOnItemClickListener(View.OnClickListener l){
        onItemClick = l;
    }

    @Override
    public int getItemCount() {
        return (endResult != null) ? endResult.size():0;
    }
}
