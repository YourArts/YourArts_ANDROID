package com.yg.yourexhibit.Tabs;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yg.yourexhibit.Adapter.HomeExhibitTabAdapter;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Util.EventBus;
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
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        HomeExhibitTabAdapter pagerAdapter = new HomeExhibitTabAdapter(getActivity().getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                EventBus.getInstance().post(tab.getPosition());
                switch (tab.getPosition()){
                    case 0:
                        //EventBus.getInstance().post(EventCode.EVENT_CODE_END_TAB);
                        Log.v(TAG, "endTab");
                        break;
                    case 1:

                        Log.v(TAG, "goingTab");
                        break;
                    case 2:

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

    @Override
    public void onDestroy() {
        EventBus.getInstance().unregister(this);
        super.onDestroy();
    }
}
