package com.yg.yourexhibit.Tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.squareup.otto.Subscribe;
import com.yg.yourexhibit.Adapter.Home.TabGoingAdapter;
import com.yg.yourexhibit.App.ApplicationController;
import com.yg.yourexhibit.Datas.TabGoingData;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitGoingResult;
import com.yg.yourexhibit.Util.EventBus;
import com.yg.yourexhibit.Util.EventCode;
import com.yg.yourexhibit.Util.NetworkController;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 2yg on 2017. 10. 9..
 */

public class Tab_Going extends Fragment{

    @BindView(R.id.tab_going_list)
    RecyclerView goingList;

    private TabGoingAdapter tabGoingAdapter;
    private RequestManager requestManager;
    private LinearLayoutManager linearLayoutManager;
    private NetworkController networkController;
    private ArrayList<TabGoingData> goingDatas;
    private int idx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_home_going, container, false);
        ButterKnife.bind(this, v);
        EventBus.getInstance().register(this);
        EventBus.getInstance().post(EventCode.EVENT_CODE_TAB_GOING);
        networkController = new NetworkController();
        networkController.getGoingData();
        return v;
    }

    public void initFragment(){
        requestManager = Glide.with(this);
        goingList.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        goingList.setLayoutManager(linearLayoutManager);
        editData();
        tabGoingAdapter = new TabGoingAdapter(goingDatas, requestManager, clickEvent);
        goingList.setAdapter(tabGoingAdapter);
    }

    @Subscribe
    public void onEventLoad(ArrayList<ExhibitGoingResult> exhibitGoingResults){

//        initFragment();
        Log.v("통신 됨", "통신");
    }

    @Subscribe
    public void onEventLoad(Integer code){
        if(code == EventCode.EVENT_CODE_GOING_SUCESS){
            initFragment();
        }
    }

    public void editData(){
        ArrayList<ExhibitGoingResult> exhibitGoingResults;
        exhibitGoingResults = ApplicationController.getInstance().getExhibitGoingResult();

        goingDatas = new ArrayList<TabGoingData>();
        for(int i = 0; i<exhibitGoingResults.size(); i++){
            goingDatas.add(new TabGoingData(exhibitGoingResults.get(i).getExhibition_picture(),
                    endPeriod(exhibitGoingResults.get(i).getExhibition_stard_date(), exhibitGoingResults.get(i).getExhibition_end_date()),
                    //exhibitGoingResults.get(i).getExhibition_stard_date(),
                    exhibitGoingResults.get(i).getExhibition_name()
            ));
        }
    }

    public String endPeriod(String start, String end){
        String tempStart = start.split("\\.")[1] + "/" + start.split("\\.")[2];
        String tempEnd = end.split("\\.")[1] + "/" + start.split("\\.")[2];
        return tempStart + " - " + tempEnd;
    }
    @Override
    public void onDestroy() {
        EventBus.getInstance().unregister(this);
        super.onDestroy();
    }
    @Override
    public void onStart(){
        super.onStart();
        EventBus.getInstance().post(EventCode.EVENT_CODE_TAB_GOING);

    }

    @Override
    public void onResume(){
        super.onResume();
        EventBus.getInstance().post(EventCode.EVENT_CODE_TAB_GOING);

    }

    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            int itemPosition = goingList.getChildPosition(v);
            idx = ApplicationController.getInstance().getExhibitGoingResult().get(itemPosition).getExhibition_idx();
            networkController.getDetailData(1, ApplicationController.getInstance().token, idx);
        }
    };


}
