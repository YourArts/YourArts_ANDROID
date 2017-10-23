package com.yg.yourexhibit.Adapter.Mine;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yg.yourexhibit.R;

/**
 * Created by 김민경 on 2017-10-22.
 */

public class TabWatchAdapter extends RecyclerView.Adapter<TabWatchAdapter.WatchViewHolder>{

    @Override
    public WatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_mine_watch_item, parent,false);
        WatchViewHolder viewHolder = new WatchViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WatchViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class WatchViewHolder extends RecyclerView.ViewHolder{
        TextView txtWatchDate;
        TextView txtWatchName;
        View WatchViewFirst;
        View WatchViewSecond;
        View WatchViewThird;
        View WatchViewFourth;
        View WatchViewFifth;

        public WatchViewHolder(View itemView) {
            super(itemView);
            txtWatchDate = (TextView)itemView.findViewById(R.id.txtWatchItemDate);
            txtWatchName = (TextView)itemView.findViewById(R.id.txtWatchItemName);
            WatchViewFirst = itemView.findViewById(R.id.watchViewFirst);
            WatchViewSecond = itemView.findViewById(R.id.watchViewSecond);
            WatchViewThird = itemView.findViewById(R.id.watchViewThird);
            WatchViewFourth = itemView.findViewById(R.id.watchViewThird);
            WatchViewFifth = itemView.findViewById(R.id.watchViewFifth);
        }
    }


}
