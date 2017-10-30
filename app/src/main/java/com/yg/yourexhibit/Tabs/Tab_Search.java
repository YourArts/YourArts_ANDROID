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
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitSearchResult;
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
    private ArrayList<ExhibitSearchResult> searchList;
    private ArrayList<ExhibitSearchResult> searchListShowing;
    private boolean clickList = false;
    private int idx = 0;
    private String flag = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_search, container, false);
        ButterKnife.bind(this, v);
        EventBus.getInstance().register(this);
        networkController = new NetworkController();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                    if (search.getText().length() != 0) {
                        result.setVisibility(View.VISIBLE);
                        image.setVisibility(View.GONE);
                        text.setVisibility(View.GONE);
                        networkController.getSearchData(search.getText().toString());
                    }else{
                        searchList.clear();
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
            Log.v("택", "택");
            clickList = true;
            searchListShowing = new ArrayList<>();
            searchListShowing.addAll(searchList);
            result.setVisibility(View.GONE);
            image.setVisibility(View.VISIBLE);
            text.setVisibility(View.VISIBLE);
            //search.setVisibility(View.INVISIBLE);

            int itemPosition = result.getChildPosition(v);
            //idx = searchList.get(itemPosition).getExhibition_idx();

            idx = searchListShowing.get(itemPosition).getExhibition_idx();
            flag = searchListShowing.get(itemPosition).getFlag();

            text.setText(searchListShowing.get(itemPosition).getExhibition_name());
//            Glide.with(ApplicationController.getInstance().getApplicationContext())
//                    .load(searchList.get(itemPosition).getExhibition_picture()).into(image);

            //search.setText(searchList.get(itemPosition).getExhibition_name());
            Glide.with(ApplicationController.getInstance().getApplicationContext())
                    .load(searchListShowing.get(itemPosition).getExhibition_picture()).into(image);

            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
            search.setCursorVisible(false);
            search.setText("");

        }
    };

    @OnClick(R.id.tab_search_search)
    public void clickSearch(){
        search.setCursorVisible(true);
        text.setVisibility(View.GONE);

    }

    @Subscribe
    public void onEventLoad(Integer code){
        switch (code){
            case EventCode.EVENT_CODE_SEARCH:
                initFragment();
                break;
            case EventCode.EVENT_CODE_END_DETAIL_SEARCH:
                getFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.tab_search_container, new Tab_End_Details())
                        .commit();
                break;
            case EventCode.EVENT_CODE_GOING_DETAIL_SEARCH:
                getFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.tab_search_container, new Tab_Going_Details())
                        .commit(); break;
            case EventCode.EVENT_CODE_COMING_DETAIL_SEARCH:
                getFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.tab_search_container, new Tab_Coming_Details())
                        .commit();break;
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getInstance().unregister(this);
    }

    @OnClick(R.id.search_result_image)
    public void clickImage(){
        if(flag.equals("done")){

            networkController.getDetailData(3, ApplicationController.getInstance().token, idx);
        }else if(flag.equals("doing")){

            networkController.getDetailData(4, ApplicationController.getInstance().token, idx);


        }else{

            networkController.getDetailData(5, ApplicationController.getInstance().token, idx);


        }
    }
}
