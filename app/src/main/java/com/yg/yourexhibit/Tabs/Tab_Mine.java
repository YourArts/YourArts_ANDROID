package com.yg.yourexhibit.Tabs;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Util.EventBus;
import com.yg.yourexhibit.Util.SharedPrefrernceController;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 2yg on 2017. 10. 8..
 */

public class Tab_Mine extends Fragment{

    int beforePosition = 0;


    @BindView(R.id.tab_mine_pager)
    TabLayout tabLayout;

    @BindView(R.id.tab_mine)
    ViewPager viewPager;

    @BindView(R.id.btnGoSetting)
    ImageButton goSetting;

    @BindView(R.id.topbarName)
    TextView topName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_mine, container, false);
        ButterKnife.bind(this, v);
        EventBus.getInstance().register(this);

        Log.d("tabmine","oncreateView!!");
        topName.setText(SharedPrefrernceController.getUserNickname(getContext()));

        // Initializing the TabLayout
        tabLayout.addTab(tabLayout.newTab().setText("WATCH"));
        tabLayout.addTab(tabLayout.newTab().setText("WISH"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.getTabAt(1).select();

        // Creating TabPagerAdapter adapter
        Tab_Mine_PagerAdapter pagerAdapter = new Tab_Mine_PagerAdapter(getActivity().getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // Set TabSelectedListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                beforePosition = tab.getPosition();
                viewPager.setCurrentItem(tab.getPosition());
                TextView txt = (TextView)(((LinearLayout)((LinearLayout)tabLayout.getChildAt(0)).getChildAt(tab.getPosition())).getChildAt(1));
                txt.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                txt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                txt.setTextColor(Color.parseColor("#00FFC4"));
                txt.setTypeface(txt.getTypeface(), Typeface.BOLD);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                TextView txt = (TextView)(((LinearLayout)((LinearLayout)tabLayout.getChildAt(0)).getChildAt(tab.getPosition())).getChildAt(1));
                txt.setPaintFlags(Paint.HINTING_OFF);
                txt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                txt.setTextColor(Color.parseColor("#FFFFFF"));
                txt.setTypeface(txt.getTypeface(), Typeface.DEFAULT.getStyle());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

        tabLayout.getTabAt(0).select();

        return v;
    }

    @OnClick(R.id.btnGoSetting)
    public void goSetting(){
        getFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.topLayout, new Tab_Setting())
                .commit();
    }

}
