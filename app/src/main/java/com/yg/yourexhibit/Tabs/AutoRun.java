package com.yg.yourexhibit.Tabs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import lombok.core.Main;

/**
 * Created by 김민경 on 2017-10-26.
 */

public class AutoRun extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equals("android.intent.action.BOOT_COMPLETED")){
            Intent i = new Intent(context, Main.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}
