package com.yg.yourexhibit.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.WindowManager;

import com.squareup.otto.Subscribe;
import com.yg.yourexhibit.Adapter.Home.HomeTabAdapter;
import com.yg.yourexhibit.App.ApplicationController;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Util.BaseActivity;
import com.yg.yourexhibit.Util.NetworkController;
import com.yg.yourexhibit.Util.SwipeViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {


    private final static String TAG = "LOG::HomeActivity";
    @BindView(R.id.homeTabs)
    TabLayout tabLayout;
    @BindView(R.id.homeContainer)
    SwipeViewPager viewPager;
//    @BindView(R.id.home_title_text)
//    TextView totleText;

    private NetworkController networkController;
//    private OnKeyBackPressedListener mOnKeyBackPressedListener;

    //풀 하고 이 문구가 잘 보이면 됨
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //networkController = new NetworkController();
        //getData();
        //EventBus.getInstance().register(this);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        //tabLayout.addTab(tabLayout.newTab().setText("Aaaa"));
        tabLayout.addTab(tabLayout.newTab().setCustomView(getLayoutInflater().inflate(R.layout.view_home, null)));

        tabLayout.addTab(tabLayout.newTab().setCustomView(getLayoutInflater().inflate(R.layout.view_search, null)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(getLayoutInflater().inflate(R.layout.view_collection, null)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(getLayoutInflater().inflate(R.layout.view_mine, null)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        HomeTabAdapter pagerAdapter = new HomeTabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        //viewPager.removeOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        if(!ApplicationController.getInstance().isReFresh())
            viewPager.setCurrentItem(0);
        else
            viewPager.setCurrentItem(ApplicationController.getInstance().getTabNum());


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //viewPager.clearAnimation();
                Log.v(TAG, "tab1");
                viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:

                        Log.v(TAG, "homeTab");
                        break;
                    case 1:
                        //totleText.setText("검색");
                        Log.v(TAG, "searchTab");
                        break;
                    case 2:
                        //totleText.setText("콜렉션");
                        Log.v(TAG, "CollectionTab");
                        break;
                    case 3:
                        ApplicationController.getInstance().setReFresh(true);
                        ApplicationController.getInstance().setTabNum(3);
                        finish();
                        startActivity(getIntent());
                        //totleText.setText("내 전시");
                        Log.v(TAG, "mineTab");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.v(TAG, "tab2");

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.v(TAG, "tab3");

            }
        });
        ApplicationController.getInstance().setReFresh(false);
        ApplicationController.getInstance().setTabNum(0);

    }

    @Subscribe
    public void onEventLoad(Integer code) {
        switch (code){
//            case EventCode.EVENT_CODE_TAB_END:
//              //  totleText.setText("종료 전시");
//                break;
//            case EventCode.EVENT_CODE_TAB_GOING:
//                //totleText.setText("진행 전시");
//                break;
//            case EventCode.EVENT_CODE_TAB_COMING:
//               // totleText.setText("예정 전시");
//                break;
        }

    }

    @Override
    public void onBackPressed() {
        if(ApplicationController.getInstance().isDetailFromEdit()){
            ApplicationController.getInstance().setDetailFromEdit(false);
            ApplicationController.getInstance().setReFresh(true);
            ApplicationController.getInstance().setTabNum(2);
            finish();
            startActivity(getIntent());
        }else
            super.onBackPressed();
    }

    public void getData(){
        networkController.getEndData();
    }


}
