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

public class TabEndViewHolder extends RecyclerView.ViewHolder{
    RelativeLayout endItemBtn;
    ImageView endItemImage;
    TextView endItemPerioid;
    TextView endItemName;


    public TabEndViewHolder(View itemView) {
        super(itemView);
        endItemBtn = (RelativeLayout) itemView.findViewById(R.id.end_items_btn);
        endItemImage = (ImageView) itemView.findViewById(R.id.end_items_img);
        endItemPerioid = (TextView) itemView.findViewById(R.id.end_items_date_txt);
        endItemName = (TextView) itemView.findViewById(R.id.end_items_name_txt);
    }
}
