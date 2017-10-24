package com.yg.yourexhibit.Tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.squareup.otto.Subscribe;
import com.yg.yourexhibit.Adapter.Home.TabGoingPreviewFirstAdapter;
import com.yg.yourexhibit.Adapter.Home.TabGoingPreviewSecondAdapter;
import com.yg.yourexhibit.App.ApplicationController;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitDetailImages;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitDetailResult;
import com.yg.yourexhibit.Util.CustomLinearLayout;
import com.yg.yourexhibit.Util.EventBus;
import com.yg.yourexhibit.Util.EventCode;
import com.yg.yourexhibit.Util.NetworkController;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 2yg on 2017. 10. 17..
 */

public class Tab_Going_Preview extends Fragment{

    @BindView(R.id.tab_preview_first)
    RecyclerView first;

    @BindView(R.id.tab_preview_second)
    RecyclerView second;

    private ExhibitDetailResult exhibitDetailResult;
    private RequestManager requestManagerFirst, requestManagerSecond;
    private CustomLinearLayout linearLayoutManagerFirst, linearLayoutManagerSecond;
    private TabGoingPreviewFirstAdapter firstAdapter;
    private TabGoingPreviewSecondAdapter secondAdapter;
    private NetworkController networkController;
    private ArrayList<ExhibitDetailImages> firstList, secondList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_home_going_preview, container, false);
        ButterKnife.bind(this, v);
        networkController = new NetworkController();
        EventBus.getInstance().register(this);
        //EventBus.getInstance().post(EventCode.EVENT_CODE_GOING_PREVIEW);
        exhibitDetailResult = ApplicationController.getInstance().getExhibitDetailResult();
        firstList = new ArrayList<ExhibitDetailImages>();
        secondList = new ArrayList<ExhibitDetailImages>();
        setDatas();
        initFragment();
        return v;
    }

    public void setDatas(){
        for(int i = 0; i<exhibitDetailResult.getImages().size();i++){
            switch (i%2){
                case 0:
                    firstList.add(new ExhibitDetailImages(exhibitDetailResult.getImages().get(i).getWork_idx(),
                            exhibitDetailResult.getImages().get(i).getWork_image()));
                    break;
                case 1:
                    secondList.add(new ExhibitDetailImages(exhibitDetailResult.getImages().get(i).getWork_idx(),
                            exhibitDetailResult.getImages().get(i).getWork_image()));
                    break;
            }
        }
    }

    public void initFragment(){
        if(!firstList.isEmpty()) {
            requestManagerFirst = Glide.with(this);
            first.setHasFixedSize(true);
            linearLayoutManagerFirst = new CustomLinearLayout(getActivity());
            linearLayoutManagerFirst.setOrientation(LinearLayout.VERTICAL);
            first.setLayoutManager(linearLayoutManagerFirst);
            firstAdapter = new TabGoingPreviewFirstAdapter(firstList, requestManagerFirst, clickEventFirst);
            first.setAdapter(firstAdapter);
        }

        if(!secondList.isEmpty()) {
            requestManagerSecond = Glide.with(this);
            second.setHasFixedSize(true);
            linearLayoutManagerSecond = new CustomLinearLayout(getActivity());
            linearLayoutManagerSecond.setOrientation(LinearLayout.VERTICAL);
            second.setLayoutManager(linearLayoutManagerSecond);
            secondAdapter = new TabGoingPreviewSecondAdapter(secondList, requestManagerSecond, clickEventSecond);
            second.setAdapter(secondAdapter);
        }
    }

    public View.OnClickListener clickEventFirst = new View.OnClickListener() {
        public void onClick(View v) {
            int itemPosition = first.getChildPosition(v);
            int idx = firstList.get(itemPosition).getWork_idx();
            networkController.getWorkData(idx);
            //ApplicationController.getInstance().makeToast(String.valueOf(idx));
        }
    };

    public View.OnClickListener clickEventSecond = new View.OnClickListener() {
        public void onClick(View v) {
            int itemPosition = first.getChildPosition(v);
            int idx = secondList.get(itemPosition).getWork_idx();
            networkController.getWorkData(idx);
            //ApplicationController.getInstance().makeToast(String.valueOf(idx));
        }
    };

    @Subscribe
    public void onEventLoad(Integer code){
        switch (code){
            case EventCode.EVENT_CODE_GOING_WORK:
                getFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.tab_preview_container, new Tab_Work())
                        .commit();
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getInstance().unregister(this);
    }
}
