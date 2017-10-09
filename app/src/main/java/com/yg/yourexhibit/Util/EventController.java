package com.yg.yourexhibit.Util;

import com.squareup.otto.Subscribe;
import com.yg.yourexhibit.App.ApplicationController;

/**
 * Created by 2yg on 2017. 10. 9..
 */

public class EventController {

    @Subscribe
    public void onEventLoad(Integer eventCode){
        switch(eventCode){
            case EventCode.EVENT_CODE_NETWORK_FAIL:
                ApplicationController.getInstance().makeToast("checkNetworkState");
                break;
        }
    }
}
