package com.yg.yourexhibit.Adapter.Home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.yg.yourexhibit.R;

/**
 * Created by 2yg on 2017. 10. 17..
 */

public class TabGoingPreviewViewHolder extends RecyclerView.ViewHolder{
    ImageView preView;

    public TabGoingPreviewViewHolder(View itemView) {
        super(itemView);
        preView = (ImageView)itemView.findViewById(R.id.tab_preview_items);
    }
}
