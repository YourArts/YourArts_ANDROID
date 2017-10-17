package com.yg.yourexhibit.Util;

import android.util.Log;

import com.yg.yourexhibit.App.ApplicationController;
import com.yg.yourexhibit.Retrofit.NetworkService;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitCollectionDetailResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitCollectionResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitCollectionResult;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitComingResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitDetailResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitEndResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitGoingResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitSearchResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitWorkResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 2yg on 2017. 10. 9..
 */

public class NetworkController {
    private NetworkService networkService;
    private static final String TAG = "LOG::NetworkController";
    private ArrayList<ExhibitCollectionResult> firstResult;
    private ArrayList<ExhibitCollectionResult> secondResult;
    private ArrayList<ExhibitCollectionResult> thirdResult;

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

    public void getDetailData(final int status, String token, int idx){
        Call<ExhibitDetailResponse> exhibitDetailResponse = networkService.getDetailResponse(token, idx);
        exhibitDetailResponse.enqueue(new Callback<ExhibitDetailResponse>() {
            @Override
            public void onResponse(Call<ExhibitDetailResponse> call, Response<ExhibitDetailResponse> response) {
                if(response.body().isStatus()){
                    ApplicationController.getInstance().setExhibitDetailResult(response.body().getResult());
                    Log.v(TAG, "getDetailSuccess");
                    switch (status){
                        case 0:
                            EventBus.getInstance().post(EventCode.EVENT_CODE_END_DETAIL);
                            Log.v(TAG, "getDetailSuccess/END");
                            break;
                        case 1:
                            EventBus.getInstance().post(EventCode.EVENT_CODE_GOING_DETAIL);
                            Log.v(TAG, "getDetailSuccess/GOING");
                            break;
                        case 2:
                            EventBus.getInstance().post(EventCode.EVENT_CODE_COMING_DETAIL);
                            Log.v(TAG, "getDetailSuccess/COMING");
                            break;
                        default:
                            break;
                    }
                }else{
                    Log.v(TAG, "getDetailFail");
                }
            }
            @Override
            public void onFailure(Call<ExhibitDetailResponse> call, Throwable t) {
                Log.v(TAG, "checkNetwork");
            }
        });
    }

    public void getSearchData(String search){
        Call<ArrayList<ExhibitSearchResponse>> exhibitSearchResponse = networkService.getSearchResponse(search);
        exhibitSearchResponse.enqueue(new Callback<ArrayList<ExhibitSearchResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<ExhibitSearchResponse>> call, Response<ArrayList<ExhibitSearchResponse>> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null) {
                        ApplicationController.getInstance().setExhibitSearchResult(response.body());
                        EventBus.getInstance().post(EventCode.EVENT_CODE_SEARCH);
                        Log.v(TAG, "getSearchSuccess");
                    }else{
                        Log.v(TAG, "getSearchSuccessButNull");
                    }
                }else{
                    EventBus.getInstance().post(EventCode.EVENT_CODE_NETWORK_FAIL);
                    Log.v(TAG,"getSearchFail");
                }
            }
            @Override
            public void onFailure(Call<ArrayList<ExhibitSearchResponse>> call, Throwable t) {
                Log.v(TAG,"checkNetwork");
            }
        });
    }

    public void getCollectionData(String token){
        firstResult = new ArrayList<ExhibitCollectionResult>();
        secondResult = new ArrayList<ExhibitCollectionResult>();
        thirdResult = new ArrayList<ExhibitCollectionResult>();

        Call<ExhibitCollectionResponse> exhibitCollectionResponse = networkService.getCollectionResponse(token);
        //final ArrayList<ExhibitCollectionResult> finalFirstResult = firstResult;
        exhibitCollectionResponse.enqueue(new Callback<ExhibitCollectionResponse>() {
            @Override
            public void onResponse(Call<ExhibitCollectionResponse> call, Response<ExhibitCollectionResponse> response) {

                if(response.body().isStatus()){
                    ApplicationController.getInstance().setCollectionSize(response.body().getResult().size());
                    Log.v(TAG, "getCollectionSuccess");
                    for(int i = 0; i<response.body().getResult().size(); i++){
                        switch(i%3){
                            case 0:
                                firstResult.add(response.body().getResult().get(i));
                                break;
                            case 1:
                                secondResult.add(response.body().getResult().get(i));
                                break;
                            case 2:
                                thirdResult.add(response.body().getResult().get(i));
                                break;
                            default:
                                break;
                        }
                    }
                    ApplicationController.getInstance().setExhibitCollectionResultFirst(firstResult);
                    ApplicationController.getInstance().setExhibitCollectionResultSecond(secondResult);
                    ApplicationController.getInstance().setExhibitCollectionResultThird(thirdResult);

                    EventBus.getInstance().post(EventCode.EVENT_CODE_COLLECTION_GET);
                }else{
                    Log.v(TAG,"getCollectionFail");
                }
            }

            @Override
            public void onFailure(Call<ExhibitCollectionResponse> call, Throwable t) {
                Log.v(TAG,"checkNetwork");
            }
        });
    }

    public void getWorkData(int idx){
        Call<ExhibitWorkResponse> exhibitWorkResponse = networkService.getWorkResponse(idx);
        exhibitWorkResponse.enqueue(new Callback<ExhibitWorkResponse>() {
            @Override
            public void onResponse(Call<ExhibitWorkResponse> call, Response<ExhibitWorkResponse> response) {
                if(response.body().isStatus()){
                    ApplicationController.getInstance().setExhibitWorkResult(response.body().getResult());
                    EventBus.getInstance().post(EventCode.EVENT_CODE_GOING_WORK);
                    Log.v(TAG, "getWorkSuccess");
                }else{
                    Log.v(TAG,"getCollectionFail");
                }
            }

            @Override
            public void onFailure(Call<ExhibitWorkResponse> call, Throwable t) {
                Log.v(TAG,"checkNetwork");
            }
        });
    }

    public void getCollectionDetailData(int idx){
        Call<ExhibitCollectionDetailResponse> detailResponse = networkService.getCollectionDetailResponse(idx);
        detailResponse.enqueue(new Callback<ExhibitCollectionDetailResponse>() {
            @Override
            public void onResponse(Call<ExhibitCollectionDetailResponse> call, Response<ExhibitCollectionDetailResponse> response) {
                if(response.body().isStatus()){
                    ApplicationController.getInstance().setExhibitCollectionDetailResult(response.body().getResult());
                    EventBus.getInstance().post(EventCode.EVENT_CODE_COLLECTION_DETAIL);
                    Log.v(TAG, "getCollectionDetailSuccess");
                }else{
                    Log.v(TAG, "getCollectionDetailFail");
                }
            }
            @Override
            public void onFailure(Call<ExhibitCollectionDetailResponse> call, Throwable t) {
                Log.v(TAG,"checkNetwork");
            }
        });
    }
}
