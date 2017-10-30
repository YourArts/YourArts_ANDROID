package com.yg.yourexhibit.Adapter.Home;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yg.yourexhibit.R;

/**
 * Created by 2yg on 2017. 10. 30..
 */

public class HomeHeaderViewHolder extends RecyclerView.ViewHolder{

    View view;
    public HomeHeaderViewHolder(View itemView) {
        super(itemView);
        view = (View) itemView.findViewById(R.id.tab_home_header);

    }
}
