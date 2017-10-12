package com.yg.yourexhibit.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.yg.yourexhibit.R;

/**
 * Created by 2yg on 2017. 10. 11..
 */

public class TabDetailViewHolder extends RecyclerView.ViewHolder{

    ImageView preView;

    public TabDetailViewHolder(View itemView) {
        super(itemView);
        preView = (ImageView)itemView.findViewById(R.id.end_details_item_preview);
    }
}
