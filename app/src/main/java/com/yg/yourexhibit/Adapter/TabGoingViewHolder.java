package com.yg.yourexhibit.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yg.yourexhibit.R;

/**
 * Created by 2yg on 2017. 10. 9..
 */

public class TabGoingViewHolder extends RecyclerView.ViewHolder{

    RelativeLayout goingItemBtn;
    ImageView goingItemImage;
    TextView goingItemPerioid;
    TextView goingItemName;

    public TabGoingViewHolder(View itemView) {
        super(itemView);
        goingItemBtn = (RelativeLayout) itemView.findViewById(R.id.going_items_btn);
        goingItemImage = (ImageView) itemView.findViewById(R.id.going_items_img);
        goingItemPerioid = (TextView) itemView.findViewById(R.id.going_items_perioid);
        goingItemName = (TextView) itemView.findViewById(R.id.going_items_name);
    }
}
