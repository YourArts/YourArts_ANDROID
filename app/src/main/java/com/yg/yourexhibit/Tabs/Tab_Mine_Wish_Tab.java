package com.yg.yourexhibit.Tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yg.yourexhibit.R;

/**
 * Created by 김민경 on 2017-10-15.
 */

public class Tab_Mine_Wish_Tab extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_mine_wish, container, false);

        return view;
    }
}
