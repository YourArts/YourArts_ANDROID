package com.yg.yourexhibit.Tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.squareup.otto.Subscribe;
import com.yg.yourexhibit.Adapter.Mine.TabWatchAdapter;
import com.yg.yourexhibit.App.ApplicationController;
import com.yg.yourexhibit.Datas.TabMineWatchData;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Retrofit.NetworkService;
import com.yg.yourexhibit.Retrofit.RetrofitGet.TabWatchResult;
import com.yg.yourexhibit.Util.EventBus;
import com.yg.yourexhibit.Util.EventCode;
import com.yg.yourexhibit.Util.NetworkController;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 김민경 on 2017-10-15.
 */

public class Tab_Mine_Watch_Tab extends Fragment {
    @BindView(R.id.rcyWatch)
    RecyclerView watchList;

    @BindView(R.id.txtWatchCount)
    TextView watchCount;

    @BindView(R.id.tabWatch)
    LinearLayout tabWatch;

    @BindView(R.id.tab_watch_message)
    TextView message;

    @BindView(R.id.tab_watch_count_message)
    ConstraintLayout countmessage;

    NetworkService networkService;
    ArrayList<TabMineWatchData> dataList;
    TabWatchAdapter tabWatchAdapter;
    RequestManager requestManager;
    LinearLayoutManager linearLayoutManager;
    NetworkController networkController;
    Boolean isRegistered;

    public void initTab(){
        networkService = ApplicationController.getInstance().getNetworkService();
        dataList = new ArrayList<TabMineWatchData>();
        watchList.setHasFixedSize(true);
        requestManager = Glide.with(this);
        linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        networkController = new NetworkController();
        tabWatchAdapter = new TabWatchAdapter(dataList,clickListener,requestManager);
        isRegistered = true;
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int itemPosition = watchList.getChildPosition(view);
            int idx = dataList.get(itemPosition).exhibition_idx;
            int status=0;

            if(dataList.get(itemPosition).flag.toString().equals("done")) status=0;
            else if(dataList.get(itemPosition).flag.toString().equals("doing")) status=1;
            else if(dataList.get(itemPosition).flag.toString().equals("todo")) status=2;

            NetworkController.setIsFrom("watch");
            networkController.getDetailData(status, ApplicationController.getInstance().token, idx);

        }
    };

    private void refresh(){
        Log.d("eventCheck","refresh()");
//        EventBus.getInstance().register(this);
//        EventBus.getInstance().unregister(this);
        FragmentTransaction transaction = (getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.detach(this).attach(new Tab_Mine_Watch_Tab()).commitAllowingStateLoss();
//        transaction.detach(this)
//        EventBus.getInstance().unregister(this);
//        EventBus.getInstance().register(this);

    }
    @Subscribe
    public void onEventLoad(Integer code) {
        if(code == EventCode.EVENT_CODE_EDIT_WATCH){
            Log.d("eventCheck","onEventLoad()");
            refresh();
            EventBus.getInstance().unregister(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_mine_watch, container, false);
        ButterKnife.bind(this, view);
        initTab();
        EventBus.getInstance().register(this);


        Log.d("myTag","onCreateView 호출");

        final Call<TabWatchResult> tabWatchResultCall = networkService.getWatch(ApplicationController.getToken());
        tabWatchResultCall.enqueue(new Callback<TabWatchResult>() {
            @Override
            public void onResponse(Call<TabWatchResult> call, Response<TabWatchResult> response) {
                if(response.body().result.size() == 0){
                    countmessage.setVisibility(View.GONE);
                    message.setVisibility(View.VISIBLE);
                }else{
                    countmessage.setVisibility(View.VISIBLE);
                    message.setVisibility(View.GONE);
                    for (TabMineWatchData data:response.body().result) {
                        dataList.add(data);
                        Log.d("myTag",data.exhibition_name);
                    }
                    watchCount.setText(String.valueOf(dataList.size()));
                    watchList.setLayoutManager(linearLayoutManager);
                    tabWatchAdapter.notifyDataSetChanged();
                    watchList.setAdapter(tabWatchAdapter);
                }
            }

            @Override
            public void onFailure(Call<TabWatchResult> call, Throwable t) {

            }
        });


        return view;
    }
//    @Override
//    public void onResume() {
//        Log.d("lifeCheck","onResume");
////        HomeTabAdapter homeTabAdapter = new HomeTabAdapter(getFragmentManager(),3);
////        HomeTabAdapter.flagChange = true;
////        homeTabAdapter.notifyDataSetChanged();
////        tabWatchAdapter.notifyDataSetChanged();
//        refresh();
//    }

    @Override
    public void onPause() {
        super.onPause();
    }



}
