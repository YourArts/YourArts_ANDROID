package com.yg.yourexhibit.Tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yg.yourexhibit.R;

import butterknife.ButterKnife;

/**
 * Created by 2yg on 2017. 10. 9..
 */

public class Tab_Coming extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_home_coming, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

}
