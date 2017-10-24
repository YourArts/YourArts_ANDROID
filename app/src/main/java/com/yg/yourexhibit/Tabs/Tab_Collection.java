package com.yg.yourexhibit.Tabs;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.squareup.otto.Subscribe;
import com.yg.yourexhibit.Adapter.Collection.TabCollectionFirstAdapter;
import com.yg.yourexhibit.Adapter.Collection.TabCollectionSecondAdapter;
import com.yg.yourexhibit.Adapter.Collection.TabCollectionThirdAdapter;
import com.yg.yourexhibit.App.ApplicationController;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitCollectionResult;
import com.yg.yourexhibit.Util.CustomLinearLayout;
import com.yg.yourexhibit.Util.EventBus;
import com.yg.yourexhibit.Util.EventCode;
import com.yg.yourexhibit.Util.NetworkController;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 2yg on 2017. 10. 8..
 */

public class Tab_Collection extends Fragment{

    @BindView(R.id.tab_collection_fisrt)
    RecyclerView first;

    @BindView(R.id.tab_collection_second)
    RecyclerView second;

    @BindView(R.id.tab_collection_third)
    RecyclerView third;

    @BindView(R.id.tab_collection_message)
    TextView text;

    @BindView(R.id.tab_collection_edit)
    ImageView edit;

    private NetworkController networkController;
    private TabCollectionFirstAdapter tabCollectionFirstAdapter;
    private TabCollectionSecondAdapter tabCollectionSecondAdapter;
    private TabCollectionThirdAdapter tabCollectionThirdAdapter;
    private RequestManager requestManagerFirst;
    private RequestManager requestManagerSecond;
    private RequestManager requestManagerThird;

    private CustomLinearLayout linearLayoutManagerFirst;
    private CustomLinearLayout linearLayoutManagerSecond;
    private CustomLinearLayout linearLayoutManagerThird;

    private ArrayList<ExhibitCollectionResult> firstResult;
    private ArrayList<ExhibitCollectionResult> secondResult;
    private ArrayList<ExhibitCollectionResult> thirdResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_collection, container, false);
        ButterKnife.bind(this, v);
        EventBus.getInstance().register(this);
        ApplicationController.getInstance().setInDetail(false);
        networkController = new NetworkController();
        requestManagerFirst = Glide.with(this);
        requestManagerSecond = Glide.with(this);
        requestManagerThird = Glide.with(this);
        Log.v("탭콜1", "탭콜1");
        first.clearOnScrollListeners();
        second.clearOnScrollListeners();
        third.clearOnScrollListeners();
        //TODO : 여기에 원래 Shared에 저장한 아이디 들어가야 함
        networkController.getCollectionData(ApplicationController.getInstance().token);



        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v("탭콜2", "탭콜2");
    }

    @Subscribe
    public void onEventLoad(Integer code){
        switch(code){
            case EventCode.EVENT_CODE_COLLECTION_GET:
                initFragment();
                break;
            case EventCode.EVENT_CODE_COLLECTION_DETAIL:
                //Fragment fragment = Tab_Collection_Detail.newInstance();

                getFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .add(R.id.tab_collection_container, new Tab_Collection())
                        .replace(R.id.tab_collection_container, new Tab_Collection_Detail(), "detial")
                        .commit();
                break;
        }
    }

    public void initFragment(){
        firstResult = ApplicationController.getInstance().getExhibitCollectionResultFirst();
        secondResult = ApplicationController.getInstance().getExhibitCollectionResultSecond();
        thirdResult = ApplicationController.getInstance().getExhibitCollectionResultThird();



        linearLayoutManagerFirst = new CustomLinearLayout(getActivity());
        linearLayoutManagerFirst.setOrientation(LinearLayout.VERTICAL);

        linearLayoutManagerSecond = new CustomLinearLayout(getActivity());
        linearLayoutManagerSecond.setOrientation(LinearLayout.VERTICAL);

        linearLayoutManagerThird = new CustomLinearLayout(getActivity());
        linearLayoutManagerThird.setOrientation(LinearLayout.VERTICAL);
        if(!firstResult.isEmpty()){
            first.setNestedScrollingEnabled(false);
            first.setVisibility(View.VISIBLE);
            text.setVisibility(View.GONE);
            first.setLayoutManager(linearLayoutManagerFirst);
            tabCollectionFirstAdapter = new TabCollectionFirstAdapter(firstResult, requestManagerFirst, clickEventFirst);
            first.setAdapter(tabCollectionFirstAdapter);
        }else{
            first.setVisibility(View.GONE);
            second.setVisibility(View.GONE);
            third.setVisibility(View.GONE);
            text.setVisibility(View.VISIBLE);
        }

        if(!secondResult.isEmpty()){
            second.setNestedScrollingEnabled(false);
            second.setVisibility(View.VISIBLE);
            second.setLayoutManager(linearLayoutManagerSecond);
            tabCollectionSecondAdapter = new TabCollectionSecondAdapter(secondResult, requestManagerSecond, clickEventSecond);
            second.setAdapter(tabCollectionSecondAdapter);

        }

        if(!thirdResult.isEmpty()){
            third.setNestedScrollingEnabled(false);
            third.setVisibility(View.VISIBLE);
            third.setLayoutManager(linearLayoutManagerThird);
            tabCollectionThirdAdapter = new TabCollectionThirdAdapter(thirdResult, requestManagerThird, clickEventThird);
            third.setAdapter(tabCollectionThirdAdapter);

        }
    }

    public View.OnClickListener clickEventFirst = new View.OnClickListener() {
        public void onClick(View v) {
            int itemPosition = first.getChildPosition(v);
            ApplicationController.getInstance().setFromEdit(false);
            ApplicationController.getInstance().makeToast(String.valueOf(itemPosition));
            int idx = firstResult.get(itemPosition).getCollection_idx();
            ApplicationController.getInstance().setCollectionIdx(idx);
            networkController.getCollectionDetailData(ApplicationController.getInstance().token, idx);

        }
    };

    public View.OnClickListener clickEventSecond = new View.OnClickListener() {
        public void onClick(View v) {
            int itemPosition = second.getChildPosition(v);
            ApplicationController.getInstance().setFromEdit(false);
            ApplicationController.getInstance().makeToast(String.valueOf(itemPosition));
            int idx = secondResult.get(itemPosition).getCollection_idx();
            ApplicationController.getInstance().setCollectionIdx(idx);
            networkController.getCollectionDetailData(ApplicationController.getInstance().token, idx);

        }
    };

    public View.OnClickListener clickEventThird = new View.OnClickListener() {
        public void onClick(View v) {
            int itemPosition = third.getChildPosition(v);
            ApplicationController.getInstance().setFromEdit(false);
            ApplicationController.getInstance().makeToast(String.valueOf(itemPosition));
            int idx = thirdResult.get(itemPosition).getCollection_idx();
            ApplicationController.getInstance().setCollectionIdx(idx);
            networkController.getCollectionDetailData(ApplicationController.getInstance().token, idx);
        }
    };

    @OnClick(R.id.tab_collection_edit)
    public void toEdit(){
        ApplicationController.getInstance().setFromDetail(false);

        getFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .add(R.id.tab_collection_container, new Tab_Collection())
                .replace(R.id.tab_collection_container, new Tab_Collection_Edit(), "edit")
                .commit();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //EventBus.getInstance().unregister(this);
    }
}
