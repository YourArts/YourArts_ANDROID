package com.yg.yourexhibit.Tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.yg.yourexhibit.Adapter.Mine.TabWishAdapter;
import com.yg.yourexhibit.App.ApplicationController;
import com.yg.yourexhibit.Datas.TabMineWishData;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Retrofit.NetworkService;
import com.yg.yourexhibit.Retrofit.RetrofitGet.TabWishResult;
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

public class Tab_Mine_Wish_Tab extends Fragment{

    @BindView(R.id.rcyWish)
    RecyclerView wishList;

    @BindView(R.id.txtWishCount)
    TextView wishCount;

    NetworkService networkService;
    ArrayList<TabMineWishData> dataList;
    TabWishAdapter tabWishAdapter;
    RequestManager requestManager;
    GridLayoutManager gridLayoutManager;
    private NetworkController networkController;

    public void initTab(){
        networkService = ApplicationController.getInstance().getNetworkService();
        dataList = new ArrayList<TabMineWishData>();
        wishList.setHasFixedSize(true);
        requestManager = Glide.with(this);
        gridLayoutManager = new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false);
        networkController = new NetworkController();
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(getContext(),"위시위시",Toast.LENGTH_SHORT).show();
            int itemPosition = wishList.getChildPosition(view);
            Log.d("positionCheck",String.valueOf(itemPosition));
            int idx = dataList.get(itemPosition).exhibition_idx;
            int status=0;

            if(dataList.get(itemPosition).flag.toString().equals("done")) status=0;
            else if(dataList.get(itemPosition).flag.toString().equals("doing")) status=1;
            else if(dataList.get(itemPosition).flag.toString().equals("todo")) status=2;

            Log.d("statusCheck",String.valueOf(status));
            Log.d("tokenCheck",String.valueOf(ApplicationController.getToken()));
            networkController.getDetailData(status, ApplicationController.getToken(), idx);
        }
    };

//    public void setStatus(){
//        for(int i=0;i<dataList.size();i++){
//            switch(dataList.get(i).flag)
//        }
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_mine_wish, container, false);
        ButterKnife.bind(this, view);
        initTab();
        Call<TabWishResult> tabWishResultCall = networkService.getWish(ApplicationController.getToken());
        tabWishResultCall.enqueue(new Callback<TabWishResult>() {
            @Override
            public void onResponse(Call<TabWishResult> call, Response<TabWishResult> response) {
                for (TabMineWishData data:response.body().result) {
                    dataList.add(data);
                    Log.d("myTag",data.exhibition_name);
                }
                wishCount.setText(String.valueOf(dataList.size()));
                wishList.setLayoutManager(gridLayoutManager);
                tabWishAdapter = new TabWishAdapter(dataList,clickListener,requestManager);
                wishList.setAdapter(tabWishAdapter);
            }

            @Override
            public void onFailure(Call<TabWishResult> call, Throwable t) {

            }
        });
        return view;
    }
}
