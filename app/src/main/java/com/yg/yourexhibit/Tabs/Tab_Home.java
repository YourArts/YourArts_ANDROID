package com.yg.yourexhibit.Tabs;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.yg.yourexhibit.Adapter.Home.HomeExhibitTabAdapter;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Util.EventBus;
import com.yg.yourexhibit.Util.EventCode;
import com.yg.yourexhibit.Util.NetworkController;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 2yg on 2017. 10. 8..
 */

public class Tab_Home extends Fragment {

    private final static String TAG = "LOG::HomeTab";


    @BindView(R.id.tab_home_exhibit_tabs)
    TabLayout tabLayout;

    @BindView(R.id.tab_home_exhibit_pager)
    ViewPager viewPager;

    @BindView(R.id.tab_home_frame)
    RelativeLayout frame;

    @BindView(R.id.tab_home_title)
    TextView title;

    private NetworkController networkController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_home, container, false);
        EventBus.getInstance().register(this);
        ButterKnife.bind(this, v);
//        networkController = new NetworkController();
//        networkController.getEndData();
//        networkController.getGoingData();


        tabLayout.addTab(tabLayout.newTab().setCustomView(getActivity().getLayoutInflater().inflate(R.layout.view_end_exhibit,null)));

        tabLayout.addTab(tabLayout.newTab().setCustomView(getActivity().getLayoutInflater().inflate(R.layout.view_on_exhibit,null)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(getActivity().getLayoutInflater().inflate(R.layout.view_coming_exhibit,null)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

       // tabLayout.getChildAt(0).findViewById(R.)

        HomeExhibitTabAdapter pagerAdapter = new HomeExhibitTabAdapter(getActivity().getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setCurrentItem(1);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

                EventBus.getInstance().post(tab.getPosition());
                switch (tab.getPosition()){
                    case 0:
                        //EventBus.getInstance().post(EventCode.EVENT_CODE_END_TAB);
                        title.setText("종료 전시");
                        Log.v(TAG, "endTab");
                        break;
                    case 1:
                        title.setText("진행 전시");
                        Log.v(TAG, "goingTab");
                        break;
                    case 2:
                        title.setText("예정 전시");
                        Log.v(TAG, "comingTab");
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return v;
    }

    @Subscribe
    public void onEventLoad(Integer code){
        switch (code){
            case EventCode.EVENT_CODE_END_DETAIL:
                toEndDetail();
                break;
            case EventCode.EVENT_CODE_GOING_DETAIL:
                toGoingDetail();
                break;
            case EventCode.EVENT_CODE_COMING_DETAIL:
                toComingDetail();
                break;

        }
    }

    public void toEndDetail(){
        Log.v(TAG, "toEndDetail");
        if(NetworkController.getIsFrom().equals("watch") || NetworkController.getIsFrom().equals("wish")){
            getFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.topLayout, new Tab_End_Details())
                    .commit();
        }else{
            getFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.tab_home_container, new Tab_End_Details())
                    .commit();
        }
    }

    public void toGoingDetail(){
        Log.v(TAG, "toGoingDetail");
        if(NetworkController.getIsFrom().equals("watch") || NetworkController.getIsFrom().equals("wish")){
            getFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.topLayout, new Tab_Going_Details())
                    .commit();
        }else{
            getFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.tab_home_container, new Tab_Going_Details())
                    .commit();
        }
    }

    public void toComingDetail(){
        Log.v(TAG, "toComingDetail");
        if(NetworkController.getIsFrom().equals("watch") || NetworkController.getIsFrom().equals("wish")){
            getFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.topLayout, new Tab_Coming_Details())
                    .commit();
        }else{
            getFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.tab_home_container, new Tab_Coming_Details())
                    .commit();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getInstance().unregister(this);
    }
}
