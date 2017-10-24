package com.yg.yourexhibit.Login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.squareup.otto.Subscribe;
import com.yg.yourexhibit.Activity.HomeActivity;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Util.EventBus;
import com.yg.yourexhibit.Util.EventCode;
import com.yg.yourexhibit.Util.NetworkController;
import com.yg.yourexhibit.Util.SharedPrefrernceController;

public class SplashActivity extends AppCompatActivity {

    private NetworkController networkController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        EventBus.getInstance().register(this);

        Handler hd = new Handler();
        networkController = new NetworkController();
        if (SharedPrefrernceController.getSelected(SplashActivity.this)){
            //자동 로그인 설정
            networkController.login(SharedPrefrernceController.getLoginId(SplashActivity.this),
                    SharedPrefrernceController.getPasswd(SplashActivity.this));
        }else{
            //자동 로그인 설정X
            hd.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }

            }, 2000);
        }
    }

    @Subscribe
    public void onEventLoad(Integer code){
        switch (code){
            case EventCode.EVENT_CODE_LOGIN:
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
        }
    }
}
