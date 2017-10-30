package com.yg.yourexhibit.Login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.squareup.otto.Subscribe;
import com.yg.yourexhibit.Activity.HomeActivity;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Util.BaseActivity;
import com.yg.yourexhibit.Util.EventBus;
import com.yg.yourexhibit.Util.EventCode;
import com.yg.yourexhibit.Util.NetworkController;
import com.yg.yourexhibit.Util.SharedPrefrernceController;

public class SplashActivity extends BaseActivity {

    private NetworkController networkController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        EventBus.getInstance().register(this);

        Handler hd = new Handler();
        networkController = new NetworkController();
        if (SharedPrefrernceController.getSelected(SplashActivity.this)){
            Log.v("Splash", "1");
            if(!SharedPrefrernceController.getLoginId(SplashActivity.this).equals("")) {
                //자동 로그인 설정
                Log.v("Splash", "2");
                networkController.login(SharedPrefrernceController.getLoginId(SplashActivity.this),
                        SharedPrefrernceController.getPasswd(SplashActivity.this));
            }else if(!SharedPrefrernceController.getFacebookToken(SplashActivity.this).equals("")){
                Log.v("Splash", "3");
                networkController.facebookLogin(SharedPrefrernceController.getFacebookToken(SplashActivity.this));
            }else{
                SharedPrefrernceController.setSelected(SplashActivity.this, false);
                hd.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }

                }, 2000);
            }
        }else{
            //자동 로그인 설정X
            Log.v("Splash", "4");
            hd.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }

            }, 2000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getInstance().unregister(this);
    }

    @Subscribe
    public void onEventLoad(Integer code){
        switch (code){
            case EventCode.EVENT_CODE_FB_LOGIN:
            case EventCode.EVENT_CODE_LOGIN:
                Log.v("Splash", "5");
                Handler hd = new Handler();
                hd.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.v("로그인","로그인");
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        finish();
                    }
                }, 2000);
                break;
            case EventCode.EVENT_CODE_FB_LOGN_FAIL:
            case EventCode.EVENET_CODE_LOGIN_FAIL:
                Log.v("Splash", "6");
                SharedPrefrernceController.setSelected(SplashActivity.this, false);
                Handler hd2 = new Handler();
                hd2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                }, 2000);
                break;
        }
    }
}
