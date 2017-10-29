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

//    @Override
//    public void onAttachFragment(Fragment childFragment) {
//        super.onAttachFragment(childFragment);
//        Call<TabWatchResult> tabWatchResultCall = networkService.getWatch(ApplicationController.getToken());
//        tabWatchResultCall.enqueue(new Callback<TabWatchResult>() {
//            @Override
//            public void onResponse(Call<TabWatchResult> call, Response<TabWatchResult> response) {
//                for (TabMineWatchData data:response.body().result) {
//                    dataList.add(data);
//                    Log.d("myTag",data.exhibition_name);
//                }
//                watchCount.setText(String.valueOf(dataList.size()));
//                watchList.setLayoutManager(linearLayoutManager);
//
//                watchList.setAdapter(tabWatchAdapter);
//            }
//
//            @Override
//            public void onFailure(Call<TabWatchResult> call, Throwable t) {
//
//            }
//        });
//    }

    @BindView(R.id.tabWatch)
    LinearLayout tabWatch;



    NetworkService networkService;



    ArrayList<TabMineWatchData> dataList;
    TabWatchAdapter tabWatchAdapter;
    RequestManager requestManager;
    LinearLayoutManager linearLayoutManager;
    NetworkController networkController;

    public void initTab(){
        networkService = ApplicationController.getInstance().getNetworkService();
        dataList = new ArrayList<TabMineWatchData>();
        watchList.setHasFixedSize(true);
        requestManager = Glide.with(this);
        linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        networkController = new NetworkController();
        tabWatchAdapter = new TabWatchAdapter(dataList,clickListener,requestManager);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(getContext(),"ㅎㅇㅎㅇ",Toast.LENGTH_SHORT).show();
            int itemPosition = watchList.getChildPosition(view);
//            int idx = ApplicationController.getInstance().getCollectionIdx();
            int idx = dataList.get(itemPosition).exhibition_idx;
            networkController.getDetailData(1, ApplicationController.getInstance().token, idx);
        }
    };


//
//    @Override
//    public void onStart() {
//        super.onStart();
//        Call<TabWatchResult> tabWatchResultCall = networkService.getWatch(ApplicationController.getToken());
//        tabWatchResultCall.enqueue(new Callback<TabWatchResult>() {
//            @Override
//            public void onResponse(Call<TabWatchResult> call, Response<TabWatchResult> response) {
//                for (TabMineWatchData data:response.body().result) {
//                    dataList.add(data);
//                    Log.d("myTag",data.exhibition_name);
//                }
//                watchCount.setText(String.valueOf(dataList.size()));
//                watchList.setLayoutManager(linearLayoutManager);
//
//                watchList.setAdapter(tabWatchAdapter);
//            }
//
//            @Override
//            public void onFailure(Call<TabWatchResult> call, Throwable t) {
//
//            }
//        });
//
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_mine_watch, container, false);
        ButterKnife.bind(this, view);
        initTab();

        Log.d("myTag","onCreateView 호출");

        final Call<TabWatchResult> tabWatchResultCall = networkService.getWatch(ApplicationController.getToken());
        tabWatchResultCall.enqueue(new Callback<TabWatchResult>() {
            @Override
            public void onResponse(Call<TabWatchResult> call, Response<TabWatchResult> response) {
                if(response.body().result.size() == 0){
                    tabWatch.removeAllViewsInLayout();
                    LinearLayout newLayout = new LinearLayout(getContext());
//                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                    lp.gravity = Gravity.CENTER;
                    TextView tv = new TextView(newLayout.getContext());
                    tv.setText("아직 본 전시가 없습니다!");
                    tv.setTextSize(18);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(450,500,0,0);
                    tv.setLayoutParams(lp);

//                    tv.setGravity(Gravity.CENTER);
//                    tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
//                    tv.setLayoutParams(lp);
                    newLayout.addView(tv);
                    tabWatch.addView(newLayout);
                    //마진 속성을 부여합니다. 마진은 addView 이후에 지정해주어야 제대로 적용됩니다.
//                    ViewGroup.MarginLayoutParams margin =
//                            new ViewGroup.MarginLayoutParams(tv.getLayoutParams());
//                    margin.setMargins(0, 30, 0, 0);
//                    tv.setLayoutParams(new LinearLayout.LayoutParams(margin));


                }else{
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
    @Override
    public void onResume() {
        super.onResume();
        Log.d("lifeCheck","onResume");
//        HomeTabAdapter homeTabAdapter = new HomeTabAdapter(getFragmentManager(),3);
//        HomeTabAdapter.flagChange = true;
//        homeTabAdapter.notifyDataSetChanged();
        tabWatchAdapter.notifyDataSetChanged();
    }
}
