package com.yg.yourexhibit.Util;

import com.squareup.otto.Bus;

/**
 * Created by 2yg on 2017. 10. 8..
 */

public class EventBus {
    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }
}
