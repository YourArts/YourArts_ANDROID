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
import com.yg.yourexhibit.Adapter.Home.TabEndAdapter;
import com.yg.yourexhibit.App.ApplicationController;
import com.yg.yourexhibit.Datas.TabEndData;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitEndResult;
import com.yg.yourexhibit.Util.EventBus;
import com.yg.yourexhibit.Util.EventCode;
import com.yg.yourexhibit.Util.NetworkController;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 2yg on 2017. 10. 9..
 */

public class Tab_End extends Fragment{

    @BindView(R.id.tab_end_list)
    RecyclerView endList;
//
//    @BindView(R.id.tab_end_container)
//    FrameLayout container;

    private static final String TAG = "LOG::Tab_End";
    private TabEndAdapter tabEndAdapter;
    private RequestManager requestManager;
    private LinearLayoutManager linearLayoutManager;
    private NetworkController networkController;
    private ArrayList<TabEndData> endDatas;
    private int idx;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_home_end, container, false);
        ButterKnife.bind(this, v);
        EventBus.getInstance().register(this);
        EventBus.getInstance().post(EventCode.EVENT_CODE_TAB_END);
        networkController = new NetworkController();
        networkController.getEndData();
        return v;
    }

    public void getData(){
        networkController.getEndData();
        //initFragment();
    }

    public void initFragment(){
        requestManager = Glide.with(this);
        endList.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        endList.setLayoutManager(linearLayoutManager);
        editData();
        tabEndAdapter = new TabEndAdapter(endDatas, requestManager, clickEvent);
        //tabEndAdapter.setOnItemClickListener(this.getView().OnClickListener);
        endList.setAdapter(tabEndAdapter);
        //Intent intent = new Intent(getActivity().getApplicationContext(), Tab_End.class);
    }

    @Subscribe
    public void onEventLoad(ArrayList<ExhibitEndResult> exhibitEndResult){

        //initFragment();
        Log.v("통신 됨", "통신");
    }

    @Subscribe
    public void onEventLoad(Integer code){
        switch (code){
            case EventCode.EVENT_CODE_END_SUCESS:
                initFragment();
                break;
//            case EventCode.EVENT_CODE_END_DETAIL:
//                Log.v(TAG, "getToEndDetailEvent");
//                toEndDetail();
//                break;
        }
    }

    public void editData(){

        ArrayList<ExhibitEndResult> exhibitEndResults;
        exhibitEndResults = ApplicationController.getInstance().getExhibitEndResult();

        endDatas = new ArrayList<TabEndData>();
        for(int i = 0; i<exhibitEndResults.size(); i++){
            endDatas.add(new TabEndData(exhibitEndResults.get(i).getExhibition_picture(),
                    endPeriod(exhibitEndResults.get(i).getExhibition_stard_date(), exhibitEndResults.get(i).getExhibition_end_date()),
                    //exhibitEndResults.get(i).getExhibition_stard_date(),
                    exhibitEndResults.get(i).getExhibition_name()
                    ));
        }
    }

    public String endPeriod(String start, String end){
        String tempStart = start.split("\\.")[1] + "/" + start.split("\\.")[2];
        String tempEnd = end.split("\\.")[1] + "/" + start.split("\\.")[2];
        return " " + tempStart + " - " + tempEnd +" ";
    }

    @Override
    public void onStart(){
        super.onStart();
        EventBus.getInstance().post(EventCode.EVENT_CODE_TAB_END);

    }

    @Override
    public void onResume(){
        super.onResume();
        EventBus.getInstance().post(EventCode.EVENT_CODE_TAB_END);

    }

    @Override
    public void onDestroy() {
        EventBus.getInstance().unregister(this);
        super.onDestroy();
    }

    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            int itemPosition = endList.getChildPosition(v);
            idx = ApplicationController.getInstance().getExhibitEndResult().get(itemPosition).getExhibition_idx();
            networkController.getDetailData(0, ApplicationController.getInstance().token, idx);
        }
    };

    public void toEndDetail(){
        Log.v(TAG, "toEndDetail");
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.tab_end_fragment, new Tab_End_Details())
                .commit();
//        Intent intent = new Intent(getActivity().getApplicationContext(), Tab_End_Details.class);
//        startActivity(intent);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getInstance().unregister(this);
    }

}
