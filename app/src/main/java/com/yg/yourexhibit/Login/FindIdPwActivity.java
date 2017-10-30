package com.yg.yourexhibit.Login;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Util.BaseActivity;


public class FindIdPwActivity extends BaseActivity {


    ImageButton idpwbackbutton;

    private SectionsPagerAdapter mSectionsPagerAdapter;


    private ViewPager mViewPager;
    private TabLayout tabLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id_pw);

        idpwbackbutton = (ImageButton) findViewById(R.id.backbtn_idpw);



        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
//        setupViewPager(mViewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("ID 찾기"));
        tabLayout.addTab(tabLayout.newTab().setText("PW 찾기"));


        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.getTabAt(1).select();

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                TextView txt = (TextView)(((LinearLayout)((LinearLayout)tabLayout.getChildAt(0)).getChildAt(tab.getPosition())).getChildAt(1));
                txt.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//                float scale = getResources().getDisplayMetrics().density;
//                txt.setTextSize(200.0f * scale);
                txt.setTextColor(Color.parseColor("#00FFC4"));
                txt.setTypeface(txt.getTypeface(), Typeface.BOLD);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                TextView txt = (TextView)(((LinearLayout)((LinearLayout)tabLayout.getChildAt(0)).getChildAt(tab.getPosition())).getChildAt(1));
                txt.setPaintFlags(Paint.HINTING_OFF);
                txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                txt.setTextColor(Color.parseColor("#FFFFFF"));
                txt.setTypeface(txt.getTypeface(), Typeface.DEFAULT.getStyle());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.getTabAt(0).select();


//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                mViewPager.setCurrentItem(tab.getPosition());
//                switch (tab.getPosition()) {
//                    case 0:
//                        //tabLayout.getTabAt(0).setCustomView(getLayoutInflater().inflate(R.layout.view_id_search, null));
//                        //tabLayout.getTabAt(1).setCustomView(getLayoutInflater().inflate(R.layout.view_pw_search_none, null));
//                        break;
//                    case 1:
//                        //tabLayout.getTabAt(0).setCustomView(getLayoutInflater().inflate(R.layout.view_id_search_none, null));
//                        //tabLayout.getTabAt(1).setCustomView(getLayoutInflater().inflate(R.layout.view_pw_search, null));
//                        //totleText.setText("검색");
//                        break;
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });




        idpwbackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(
                        getApplicationContext(), LoginActivity.class);
                startActivity(intent);

            }
        });

        //startActivity(new Intent(this, SplashActivity.class));
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.i("myTag", "restart");
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
        private int tabCount;

        IDfindFragment tabId;
        PWfindFragment tabPw;



        public SectionsPagerAdapter(FragmentManager fm, int count) {
            super(fm);

            tabId = new IDfindFragment();
            tabPw = new PWfindFragment();
            this.tabCount = count;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return tabId;
                case 1:
                    return tabPw;
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return tabCount;
        }


//        @Override
//        public CharSequence getPageTitle(int position) {
//            switch (position) {
//                case 0:
//                    return "ID 찾기";
//                case 1:
//                    return "PW 찾기";
//
//            }
//            return null;
//        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }



}

