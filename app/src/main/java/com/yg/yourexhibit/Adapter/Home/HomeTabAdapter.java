package com.yg.yourexhibit.Adapter.Home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.yg.yourexhibit.Tabs.Tab_Collection;
import com.yg.yourexhibit.Tabs.Tab_Home;
import com.yg.yourexhibit.Tabs.Tab_Mine;
import com.yg.yourexhibit.Tabs.Tab_Search;

/**
 * Created by 2yg on 2017. 10. 8..
 */

public class HomeTabAdapter extends FragmentStatePagerAdapter{

    private int tabCount;
    Tab_Home tabHome;
    Tab_Collection tabCollection;
    Tab_Mine tabMine;
    Tab_Search tabSearch;


    public HomeTabAdapter(FragmentManager fm, int TabCount){
        super(fm);
          tabHome = new Tab_Home();
          tabCollection = new Tab_Collection();
          tabMine = new Tab_Mine();
          tabSearch = new Tab_Search();

         this.tabCount =TabCount;
    }

    public HomeTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return tabHome;
            case 1:
                return tabSearch;
            case 2:
                return tabCollection;
            case 3:
                return tabMine;
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
