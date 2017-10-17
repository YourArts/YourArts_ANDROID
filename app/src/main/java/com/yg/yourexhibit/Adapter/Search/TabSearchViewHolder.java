package com.yg.yourexhibit.Adapter.Search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yg.yourexhibit.R;

/**
 * Created by 2yg on 2017. 10. 13..
 */

public class TabSearchViewHolder extends RecyclerView.ViewHolder{

    TextView search;
    public TabSearchViewHolder(View itemView) {
        super(itemView);
        search = (TextView) itemView.findViewById(R.id.tab_search_result_items);

    }
}
