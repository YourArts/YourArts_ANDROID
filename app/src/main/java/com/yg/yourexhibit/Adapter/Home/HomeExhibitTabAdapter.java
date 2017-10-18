package com.yg.yourexhibit.Adapter.Home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.yg.yourexhibit.Tabs.Tab_Coming;
import com.yg.yourexhibit.Tabs.Tab_End;
import com.yg.yourexhibit.Tabs.Tab_Going;

/**
 * Created by 2yg on 2017. 10. 9..
 */

public class HomeExhibitTabAdapter extends FragmentStatePagerAdapter {

    private int tabCount;
    Tab_End tabEnd;
    Tab_Going tabGoing;
    Tab_Coming tabComing;


    public HomeExhibitTabAdapter(FragmentManager fm, int TabCount){
        super(fm);
        tabEnd = new Tab_End();
        tabGoing = new Tab_Going();
        tabComing = new Tab_Coming();

        this.tabCount = TabCount;
    }


    public HomeExhibitTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return tabEnd;
            case 1:
                return tabGoing;
            case 2:
                return tabComing;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Don't Touch!!
    }
}
