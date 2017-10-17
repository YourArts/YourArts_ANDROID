package com.yg.yourexhibit.Tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yg.yourexhibit.App.ApplicationController;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitWorkResult;
import com.yg.yourexhibit.Util.EventBus;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by 2yg on 2017. 10. 17..
 */

public class Tab_Work extends Fragment{


    private ArrayList<ExhibitWorkResult> workResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_work, container, false);
        ButterKnife.bind(this, v);
        EventBus.getInstance().register(this);
        //EventBus.getInstance().post(EventCode.EVENT_CODE_GOING_PREVIEW);
        workResult = ApplicationController.getInstance().getExhibitWorkResult();
        //exhibitDetailResult = ApplicationController.getInstance().getExhibitDetailResult();
//        firstList = new ArrayList<ExhibitDetailImages>();
//        secondList = new ArrayList<ExhibitDetailImages>();
//        setDatas();
        initFragment();
        return v;
    }

    public void initFragment(){

    }
}
