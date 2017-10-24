package com.yg.yourexhibit.Tabs;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yg.yourexhibit.R;

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_mine, container, false);
        ButterKnife.bind(this, v);
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
                txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 80);
                txt.setTextColor(Color.parseColor("#00FFC4"));
                txt.setTypeface(txt.getTypeface(), Typeface.BOLD);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                TextView txt = (TextView)(((LinearLayout)((LinearLayout)tabLayout.getChildAt(0)).getChildAt(tab.getPosition())).getChildAt(1));
                txt.setPaintFlags(Paint.HINTING_OFF);
                txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 80);
                txt.setTextColor(Color.parseColor("#FFFFFF"));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

        tabLayout.getTabAt(0).select();

//        goSetting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getContext(), SettingActivity.class);
//                startActivity(i);
////                getFragmentManager()
////                        .beginTransaction()
////                        .add(R.id.topLayout, new Tab_Mine(), "base")
////                        .replace(R.id.tabSetting, new Tab_Setting(), "setting")
////                        .commit();
//            }
//        });

        return v;
    }

    @OnClick(R.id.btnGoSetting)
    public void goSetting(){
//        getFragmentManager()
//                .beginTransaction()
//                .addToBackStack(null)
//                .add(R.id.tabSetting, new Tab_Setting())
//                .commit();
        getFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.topLayout, new Tab_Setting())
                .commit();
    }



}
