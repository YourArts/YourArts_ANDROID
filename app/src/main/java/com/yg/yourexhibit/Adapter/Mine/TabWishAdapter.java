package com.yg.yourexhibit.Adapter.Mine;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.yg.yourexhibit.Datas.TabMineWishData;
import com.yg.yourexhibit.R;

import java.util.ArrayList;

/**
 * Created by 김민경 on 2017-10-24.
 */

public class TabWishAdapter extends RecyclerView.Adapter<TabWishAdapter.WishViewHolder>{

    ArrayList<TabMineWishData> dataList;

    public TabWishAdapter(ArrayList<TabMineWishData> dataList, View.OnClickListener clickListener, RequestManager requestManager) {
        this.dataList = dataList;
        this.clickListener = clickListener;
        this.requestManager = requestManager;
    }

    View.OnClickListener clickListener;
    RequestManager requestManager;

    @Override
    public WishViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_mine_wish_item,parent,false);
        WishViewHolder viewHolder = new WishViewHolder(itemView);
        itemView.setOnClickListener(clickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WishViewHolder holder, int position) {
        requestManager.load(dataList.get(position).exhibition_picture).into(holder.WishItemPicture);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class WishViewHolder extends RecyclerView.ViewHolder{

        ImageView WishItemPicture;

        public WishViewHolder(View itemView) {
            super(itemView);
           WishItemPicture = (ImageView)itemView.findViewById(R.id.imgWishItemPicture);
        }
    }
}
