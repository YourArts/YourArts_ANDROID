package com.yg.yourexhibit.Util;

import android.util.Log;

import com.yg.yourexhibit.App.ApplicationController;
import com.yg.yourexhibit.Retrofit.NetworkService;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitComingResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitEndResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitGoingResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 2yg on 2017. 10. 9..
 */

public class NetworkController {
    private NetworkService networkService;
    private static final String TAG = "LOG::NetworkController";

    public NetworkController(){
        this.networkService = ApplicationController.getInstance().getNetworkService();
    }

    public void getEndData(){
        networkService = ApplicationController.getInstance().getNetworkService();
        Call<ExhibitEndResponse> exhibitEndResponse = networkService.endResponse();
        exhibitEndResponse.enqueue(new Callback<ExhibitEndResponse>() {
            @Override
            public void onResponse(Call<ExhibitEndResponse> call, Response<ExhibitEndResponse> response) {
                if(response.body().isStatus()){
                    ApplicationController.getInstance().setExhibitEndResult(response.body().getResult());
                    //EventBus.getInstance().post(response.body().getResult());
                    EventBus.getInstance().post(EventCode.EVENT_CODE_END_SUCESS);
                    Log.v(TAG, "getEndDataSuccess");
                }else{
                    EventBus.getInstance().post(EventCode.EVENT_CODE_NETWORK_FAIL);
                    Log.v(TAG, "getEndDataFail");
                }
            }

            @Override
            public void onFailure(Call<ExhibitEndResponse> call, Throwable t) {
                    Log.v(TAG, "checkNetwork");
            }
        });
    }

    public void getGoingData(){
        Call<ExhibitGoingResponse> exhibitGoingResponse = networkService.goingResponse();
        exhibitGoingResponse.enqueue(new Callback<ExhibitGoingResponse>() {
            @Override
            public void onResponse(Call<ExhibitGoingResponse> call, Response<ExhibitGoingResponse> response) {
                if(response.body().isStatus()){
                    ApplicationController.getInstance().setExhibitGoingResult(response.body().getResult());
                    EventBus.getInstance().post(EventCode.EVENT_CODE_GOING_SUCESS);
                    Log.v(TAG, "getGoingDataSuccess");
                }else{
                    EventBus.getInstance().post(EventCode.EVENT_CODE_NETWORK_FAIL);
                    Log.v(TAG, "getGoingDataFail");
                }
            }

            @Override
            public void onFailure(Call<ExhibitGoingResponse> call, Throwable t) {
                Log.v(TAG, "checkNetwork");
            }
        });
    }

    public void getComingData(){
        Call<ExhibitComingResponse> exhibitGoingResponse = networkService.comingResponse();
        exhibitGoingResponse.enqueue(new Callback<ExhibitComingResponse>() {
            @Override
            public void onResponse(Call<ExhibitComingResponse> call, Response<ExhibitComingResponse> response) {
                if(response.body().isStatus()){
                    ApplicationController.getInstance().setExhibitComingResult(response.body().getResult());
                    //EventBus.getInstance().post(response.body().getResult());
                    EventBus.getInstance().post(EventCode.EVENT_CODE_COMING_SUCESS);
                    Log.v(TAG, "getComingDataSuccess");
                }else{
                    EventBus.getInstance().post(EventCode.EVENT_CODE_NETWORK_FAIL);
                    Log.v(TAG, "getComingDataFail");
                }
            }

            @Override
            public void onFailure(Call<ExhibitComingResponse> call, Throwable t) {
                Log.v(TAG, "checkNetwork");
            }
        });
    }

}
