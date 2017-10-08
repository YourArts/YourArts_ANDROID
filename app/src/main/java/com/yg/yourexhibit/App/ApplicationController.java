package com.yg.yourexhibit.App;

import android.app.Application;
import android.content.Context;

import com.tsengvn.typekit.Typekit;
import com.yg.yourexhibit.Retrofit.NetworkService;

/**
 * Created by 2yg on 2017. 10. 8..
 */

public class ApplicationController extends Application{
    public final static String baseUrl = "52.78.97.61:3000/api";
    public static Context appContext = null;
    private static ApplicationController instance;
    private NetworkService networkService;


    @Override public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();

        instance = this;
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this,"NanumGothic.otf"))
                .addBold(Typekit.createFromAsset(this,"NanumGothicBold.otf"))
                .addCustom1(Typekit.createFromAsset(this, "NanumGothicExtraBold.otf"));

        //FacebookSdk.sdkInitialize(getApplicationContext());
        //buildNetwork();
    }


//    public void buildNetwork() {
//        Retrofit.Builder builder = new Retrofit.Builder();
//        Retrofit retrofit = builder
//                .baseUrl(baseUrl)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        networkService = retrofit.create(NetworkService.class);
//
//    }

    public static ApplicationController getInstance() {
        return instance;
    }
    public NetworkService getNetworkService() {
        return networkService;
    }


}
