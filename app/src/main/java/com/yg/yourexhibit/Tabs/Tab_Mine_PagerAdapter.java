package com.yg.yourexhibit.Tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by 김민경 on 2017-10-15.
 */

public class Tab_Mine_PagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public Tab_Mine_PagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Tab_Mine_Watch_Tab watchTabFragment = new Tab_Mine_Watch_Tab();
                return watchTabFragment;
            case 1:
                Tab_Mine_Wish_Tab wishTabFragment = new Tab_Mine_Wish_Tab();
                return wishTabFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
