package com.yg.yourexhibit.Adapter.Home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yg.yourexhibit.R;

/**
 * Created by 2yg on 2017. 10. 10..
 */

public class TabComingViewHolder extends RecyclerView.ViewHolder{
    RelativeLayout comingItemBtn;
    ImageView comingItemImage;
    TextView comingItemPerioid;
    TextView comingItemName;

    public TabComingViewHolder(View itemView) {
        super(itemView);
        comingItemBtn = (RelativeLayout) itemView.findViewById(R.id.coming_item_btn);
        comingItemImage = (ImageView) itemView.findViewById(R.id.coming_item_img);
        comingItemPerioid = (TextView) itemView.findViewById(R.id.coming_item_period);
        comingItemName = (TextView) itemView.findViewById(R.id.coming_item_name);
    }
}
