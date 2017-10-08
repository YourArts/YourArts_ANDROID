package com.yg.yourexhibit.Tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yg.yourexhibit.R;

import butterknife.ButterKnife;

/**
 * Created by 2yg on 2017. 10. 8..
 */

public class Tab_Search extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_search, container, false);
        ButterKnife.bind(this, v);
        Log.v("프랙", "검색");

        return v;
    }
}
