package com.yg.yourexhibit.Tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.yg.yourexhibit.Adapter.Mine.TabWatchAdapter;
import com.yg.yourexhibit.App.ApplicationController;
import com.yg.yourexhibit.Datas.TabMineWatchData;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Retrofit.NetworkService;
import com.yg.yourexhibit.Retrofit.RetrofitGet.TabWatchResult;

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

    NetworkService networkService;
    ArrayList<TabMineWatchData> dataList;
    TabWatchAdapter tabWatchAdapter;
    RequestManager requestManager;
    LinearLayoutManager linearLayoutManager;


    public void initTab(){
        networkService = ApplicationController.getInstance().getNetworkService();
        dataList = new ArrayList<TabMineWatchData>();
        watchList.setHasFixedSize(true);
        requestManager = Glide.with(this);
        linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(getContext(),"ㅎㅇㅎㅇ",Toast.LENGTH_SHORT).show();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_mine_watch, container, false);
        ButterKnife.bind(this, view);
        initTab();

        Call<TabWatchResult> tabWatchResultCall = networkService.getWatch(ApplicationController.getToken());
        tabWatchResultCall.enqueue(new Callback<TabWatchResult>() {
            @Override
            public void onResponse(Call<TabWatchResult> call, Response<TabWatchResult> response) {
                for (TabMineWatchData data:response.body().result) {
                    dataList.add(data);
                    Log.d("myTag",data.exhibition_name);
                }
                watchCount.setText(String.valueOf(dataList.size()));
                watchList.setLayoutManager(linearLayoutManager);
                tabWatchAdapter = new TabWatchAdapter(dataList,clickListener,requestManager);
                watchList.setAdapter(tabWatchAdapter);
            }

            @Override
            public void onFailure(Call<TabWatchResult> call, Throwable t) {

            }
        });
        return view;
    }
}
