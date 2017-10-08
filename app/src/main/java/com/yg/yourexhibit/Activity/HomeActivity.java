package com.yg.yourexhibit.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.yg.yourexhibit.Adapter.HomeTabAdapter;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Util.BaseActivity;
import com.yg.yourexhibit.Util.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {


    private final static String TAG = "LOG::HomeActivity";
    @BindView(R.id.homeTabs)
    TabLayout tabLayout;
    @BindView(R.id.homeContainer)
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        EventBus.getInstance().register(this);
        ButterKnife.bind(this);

        //tabLayout.addTab(tabLayout.newTab().setText("Aaaa"));
        tabLayout.addTab(tabLayout.newTab().setCustomView(getLayoutInflater().inflate(R.layout.view_home,null)));

        tabLayout.addTab(tabLayout.newTab().setCustomView(getLayoutInflater().inflate(R.layout.view_search,null)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(getLayoutInflater().inflate(R.layout.view_collection,null)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(getLayoutInflater().inflate(R.layout.view_mine,null)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        HomeTabAdapter pagerAdapter = new HomeTabAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
//        viewPager.setAdapter(pagerAdapter);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //viewPager.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()){
                    case 0:

                        Log.v(TAG, "homeTab");
                        break;
                    case 1:

                        Log.v(TAG, "searchTab");
                        break;
                    case 2:

                        Log.v(TAG, "CollectionTab");
                        break;
                    case 3:

                        Log.v(TAG, "mineTab");
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
    }
}
