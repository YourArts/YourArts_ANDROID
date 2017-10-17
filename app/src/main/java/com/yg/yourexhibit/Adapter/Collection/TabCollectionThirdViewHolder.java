package com.yg.yourexhibit.Adapter.Collection;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.yg.yourexhibit.R;

/**
 * Created by 2yg on 2017. 10. 16..
 */

public class TabCollectionThirdViewHolder extends RecyclerView.ViewHolder{

    ImageView collectionImage;

    public TabCollectionThirdViewHolder(View itemView) {
        super(itemView);
        collectionImage = (ImageView) itemView.findViewById(R.id.tab_collection_items);
    }
}
