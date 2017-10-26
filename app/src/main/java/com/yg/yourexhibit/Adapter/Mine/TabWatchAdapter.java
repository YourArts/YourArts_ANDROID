package com.yg.yourexhibit.Adapter.Mine;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.squareup.picasso.Picasso;
import com.yg.yourexhibit.App.ApplicationController;
import com.yg.yourexhibit.Datas.TabMineWatchData;
import com.yg.yourexhibit.R;

import java.util.ArrayList;

/**
 * Created by 김민경 on 2017-10-22.
 */

public class TabWatchAdapter extends RecyclerView.Adapter<TabWatchAdapter.WatchViewHolder>{

    ArrayList<TabMineWatchData> dataList;
    Integer likeCount;

    public TabWatchAdapter(ArrayList<TabMineWatchData> dataList, View.OnClickListener clickListener, RequestManager requestManager) {
        this.dataList = dataList;
        this.clickListener = clickListener;
        this.requestManager = requestManager;
    }

    View.OnClickListener clickListener;
    RequestManager requestManager;


    @Override
    public WatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_mine_watch_item, parent,false);
        WatchViewHolder viewHolder = new WatchViewHolder(itemView);
        itemView.setOnClickListener(clickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WatchViewHolder holder, int position) {
        String from = dataList.get(position).exhibition_stard_date.substring(5,10);
        String to = dataList.get(position).exhibition_end_date.substring(5,10);
        String date = from + " - " + to;

        holder.txtWatchDate.setText(date);
        holder.txtWatchName.setText(dataList.get(position).exhibition_name);
//        requestManager.load(dataList.get(position).exhibition_picture).into(holder.WatchItemPicture);
        Picasso.with(ApplicationController.getInstance().getApplicationContext())
                .load(dataList.get(position).exhibition_picture)
                .resize(90, 90)
                .into(holder.WatchItemPicture);
//        for (int i = 0; i <dataList.size() ; i++) {
            switch(dataList.get(position).like_count){
                case 5:
                    holder.WatchViewFirst.setBackgroundColor(Color.parseColor("#00FFC4"));
                    holder.WatchViewSecond.setBackgroundColor(Color.parseColor("#00FFC4"));
                    holder.WatchViewThird.setBackgroundColor(Color.parseColor("#00FFC4"));
                    holder.WatchViewFourth.setBackgroundColor(Color.parseColor("#00FFC4"));
                    holder.WatchViewFifth.setBackgroundColor(Color.parseColor("#00FFC4"));
                    break;
                case 4:
                    holder.WatchViewFirst.setBackgroundColor(Color.parseColor("#00FFC4"));
                    holder.WatchViewSecond.setBackgroundColor(Color.parseColor("#00FFC4"));
                    holder.WatchViewThird.setBackgroundColor(Color.parseColor("#00FFC4"));
                    holder.WatchViewFourth.setBackgroundColor(Color.parseColor("#00FFC4"));
                    holder.WatchViewFifth.setBackgroundColor(Color.parseColor("#666666"));
                    break;
                case 3:
                    holder.WatchViewFirst.setBackgroundColor(Color.parseColor("#00FFC4"));
                    holder.WatchViewSecond.setBackgroundColor(Color.parseColor("#00FFC4"));
                    holder.WatchViewThird.setBackgroundColor(Color.parseColor("#00FFC4"));
                    holder.WatchViewFourth.setBackgroundColor(Color.parseColor("#666666"));
                    holder.WatchViewFifth.setBackgroundColor(Color.parseColor("#666666"));
                    break;
                case 2:
                    holder.WatchViewFirst.setBackgroundColor(Color.parseColor("#00FFC4"));
                    holder.WatchViewSecond.setBackgroundColor(Color.parseColor("#00FFC4"));
                    holder.WatchViewThird.setBackgroundColor(Color.parseColor("#666666"));
                    holder.WatchViewFourth.setBackgroundColor(Color.parseColor("#666666"));
                    holder.WatchViewFifth.setBackgroundColor(Color.parseColor("#666666"));
                    break;
                case 1:
                    holder.WatchViewFirst.setBackgroundColor(Color.parseColor("#00FFC4"));
                    holder.WatchViewSecond.setBackgroundColor(Color.parseColor("#666666"));
                    holder.WatchViewThird.setBackgroundColor(Color.parseColor("#666666"));
                    holder.WatchViewFourth.setBackgroundColor(Color.parseColor("#666666"));
                    holder.WatchViewFifth.setBackgroundColor(Color.parseColor("#666666"));
                    break;
                case 0:
                    break;
                default:
                    break;
            }
//        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class WatchViewHolder extends RecyclerView.ViewHolder{
        TextView txtWatchDate;
        TextView txtWatchName;
        ImageView WatchItemPicture;
        View WatchViewFirst;
        View WatchViewSecond;
        View WatchViewThird;
        View WatchViewFourth;
        View WatchViewFifth;

        public WatchViewHolder(View itemView) {
            super(itemView);
            txtWatchDate = (TextView)itemView.findViewById(R.id.txtWatchItemDate);
            txtWatchName = (TextView)itemView.findViewById(R.id.txtWatchItemName);
            WatchItemPicture = (ImageView)itemView.findViewById(R.id.imgWatchItemPicture);
            WatchViewFirst = itemView.findViewById(R.id.watchViewFirst);
            WatchViewSecond = itemView.findViewById(R.id.watchViewSecond);
            WatchViewThird = itemView.findViewById(R.id.watchViewThird);
            WatchViewFourth = itemView.findViewById(R.id.watchViewFourth);
            WatchViewFifth = itemView.findViewById(R.id.watchViewFifth);
        }
    }


}
