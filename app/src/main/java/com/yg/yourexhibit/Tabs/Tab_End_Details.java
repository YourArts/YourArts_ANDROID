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
import com.yg.yourexhibit.Adapter.TabEndDetailAdapter;
import com.yg.yourexhibit.App.ApplicationController;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitDetailResult;
import com.yg.yourexhibit.Util.EventBus;
import com.yg.yourexhibit.Util.NetworkController;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 2yg on 2017. 10. 10..
 */

public class Tab_End_Details extends Fragment{

    @BindView(R.id.end_details_preview)
    RecyclerView preViewList;

    private ExhibitDetailResult exhibitDetailResult;
    private RequestManager requestManager;
    private LinearLayoutManager linearLayoutManager;
    private TabEndDetailAdapter tabEndDetailAdapter;
    private NetworkController networkController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_home_end_details, container, false);
        ButterKnife.bind(this, v);
        EventBus.getInstance().register(this);
        Log.v("들어ㅇ","ㅇㅇㅇ");
        exhibitDetailResult = ApplicationController.getInstance().getExhibitDetailResult();
        //networkController = new NetworkController();
        initFragment();
        return v;
    }

    public void initFragment(){
        requestManager = Glide.with(this);
        preViewList.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        preViewList.setLayoutManager(linearLayoutManager);
        tabEndDetailAdapter = new TabEndDetailAdapter(exhibitDetailResult.getImages(), requestManager);
        //tabEndAdapter.setOnItemClickListener(this.getView().OnClickListener);
        preViewList.setAdapter(tabEndDetailAdapter);
        //Intent intent = new Intent(getActivity().getApplicationContext(), Tab_End.class);
    }

}
