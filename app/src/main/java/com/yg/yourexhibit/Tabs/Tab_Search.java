package com.yg.yourexhibit.Tabs;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.otto.Subscribe;
import com.yg.yourexhibit.Adapter.Search.TabSearchAdapter;
import com.yg.yourexhibit.App.ApplicationController;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitSearchResponse;
import com.yg.yourexhibit.Util.EventBus;
import com.yg.yourexhibit.Util.EventCode;
import com.yg.yourexhibit.Util.NetworkController;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 2yg on 2017. 10. 8..
 */

public class Tab_Search extends Fragment{

    @BindView(R.id.tab_search_search)
    EditText search;

    @BindView(R.id.tab_search_result)
    RecyclerView result;

    @BindView(R.id.search_result_image)
    ImageView image;

    @BindView(R.id.tab_search_text)
    TextView text;

    private TabSearchAdapter tabSearchAdapter;
    private LinearLayoutManager linearLayoutManager;
    private NetworkController networkController;
    private ArrayList<ExhibitSearchResponse> searchList;
    private int idx;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_search, container, false);
        ButterKnife.bind(this, v);
        EventBus.getInstance().register(this);
        networkController = new NetworkController();
        search.clearComposingText();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                result.setVisibility(View.VISIBLE);
                image.setVisibility(View.GONE);
                text.setVisibility(View.GONE);
                if(search.getText().length()!=0) {
                    networkController.getSearchData(search.getText().toString());
//                }else{
//                    searchList.clear();
                }else{
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return v;
    }

    public void initFragment(){
        Log.v("search","옴");
        searchList = ApplicationController.getInstance().getExhibitSearchResult();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        result.setLayoutManager(linearLayoutManager);

        tabSearchAdapter = new TabSearchAdapter(searchList, clickEvent);
        result.setAdapter(tabSearchAdapter);
    }

    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            result.setVisibility(View.GONE);
            image.setVisibility(View.VISIBLE);
            text.setVisibility(View.VISIBLE);
            //search.setVisibility(View.INVISIBLE);

            int itemPosition = result.getChildPosition(v);
            //idx = searchList.get(itemPosition).getExhibition_idx();
            text.setText(searchList.get(itemPosition).getExhibition_name());
//            Glide.with(ApplicationController.getInstance().getApplicationContext())
//                    .load(searchList.get(itemPosition).getExhibition_picture()).into(image);

            //search.setText(searchList.get(itemPosition).getExhibition_name());
            Glide.with(ApplicationController.getInstance().getApplicationContext())
                    .load(searchList.get(itemPosition).getExhibition_picture()).into(image);

            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
            search.setCursorVisible(false);
            //search.setText("");
            //searchList.clear();

            //networkController.getDetailData(0, idx);
        }
    };

    @Subscribe
    public void onEventLoad(Integer code){
        switch (code){
            case EventCode.EVENT_CODE_SEARCH:
                initFragment();
                break;
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getInstance().unregister(this);
    }
}
