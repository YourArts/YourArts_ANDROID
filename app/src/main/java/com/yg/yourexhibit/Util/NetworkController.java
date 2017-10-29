package com.yg.yourexhibit.Util;

import android.util.Log;

import com.yg.yourexhibit.App.ApplicationController;
import com.yg.yourexhibit.Retrofit.NetworkService;
import com.yg.yourexhibit.Retrofit.RetrofitDelete.ExhibitCollectionDeleteResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitCollectionDetailResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitCollectionResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitCollectionResult;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitComingResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitDetailResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitEndResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitGoingResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitSearchResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitWorkResponse;
import com.yg.yourexhibit.Retrofit.RetrofitPost.CheckAuthPost;
import com.yg.yourexhibit.Retrofit.RetrofitPost.CheckAuthResponse;
import com.yg.yourexhibit.Retrofit.RetrofitPost.ExhibitCollectionPostResponse;
import com.yg.yourexhibit.Retrofit.RetrofitPost.ExhibitFacebookLoginPost;
import com.yg.yourexhibit.Retrofit.RetrofitPost.ExhibitFacebookSignPost;
import com.yg.yourexhibit.Retrofit.RetrofitPost.ExhibitHeartPost;
import com.yg.yourexhibit.Retrofit.RetrofitPost.ExhibitHeartPostResponse;
import com.yg.yourexhibit.Retrofit.RetrofitPost.ExhibitLikePost;
import com.yg.yourexhibit.Retrofit.RetrofitPost.ExhibitLikePostResponse;
import com.yg.yourexhibit.Retrofit.RetrofitPost.FindIdPost;
import com.yg.yourexhibit.Retrofit.RetrofitPost.FindIdResponse;
import com.yg.yourexhibit.Retrofit.RetrofitPost.LoginPost;
import com.yg.yourexhibit.Retrofit.RetrofitPost.LoginPostResponse;
import com.yg.yourexhibit.Retrofit.RetrofitPost.SignPost;
import com.yg.yourexhibit.Retrofit.RetrofitPost.SignPostResponse;
import com.yg.yourexhibit.Retrofit.RetrofitPut.CollectionPutBody;
import com.yg.yourexhibit.Retrofit.RetrofitPut.ExhibitCollectionPutResponse;
import com.yg.yourexhibit.Retrofit.RetrofitPut.ExhibitHeartPut;
import com.yg.yourexhibit.Retrofit.RetrofitPut.ExhibitHeartPutResponse;
import com.yg.yourexhibit.Retrofit.RetrofitPut.ExhibitLikePut;
import com.yg.yourexhibit.Retrofit.RetrofitPut.ExhibitLikePutResponse;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    public void getCollectionDetailData(String token, int idx){
        Call<ExhibitCollectionDetailResponse> detailResponse = networkService.getCollectionDetailResponse(token, idx);
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

    public void postCollection(String token, RequestBody idx, RequestBody content, MultipartBody.Part image){
        Call<ExhibitCollectionPostResponse> postCollectionResponse = networkService.postCollectionResponse(token, idx, content, image);
        postCollectionResponse.enqueue(new Callback<ExhibitCollectionPostResponse>() {
            @Override
            public void onResponse(Call<ExhibitCollectionPostResponse> call, Response<ExhibitCollectionPostResponse> response) {
                if(response.isSuccessful()){
                    Log.v(TAG, "getCollectionDetailSuccess");
                    if(ApplicationController.getInstance().isFromDetail()){
                        //디테일로부터 옴
                    }else{
                        //그냥 생으로 작성
                        EventBus.getInstance().post(EventCode.EVENT_CODE_COLLECTION_POST);
                    }
                }else{
                    Log.v(TAG, "getCollectionDetailFail");

                }
            }

            @Override
            public void onFailure(Call<ExhibitCollectionPostResponse> call, Throwable t) {
                Log.v(TAG,"checkNetwork");

            }
        });
    }

    public void getCollectionSearchData(String search){
        Call<ArrayList<ExhibitSearchResponse>> exhibitSearchResponse = networkService.getSearchResponse(search);
        exhibitSearchResponse.enqueue(new Callback<ArrayList<ExhibitSearchResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<ExhibitSearchResponse>> call, Response<ArrayList<ExhibitSearchResponse>> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null) {
                        ApplicationController.getInstance().setExhibitSearchResult(response.body());
                        EventBus.getInstance().post(EventCode.EVENT_CODE_COLLECTION_SEARCH);
                        Log.v(TAG, "getCollectionSearchSuccess");
                    }else{
                        Log.v(TAG, "getCollectionSearchSuccessButNull");
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

    public void putCollection(String token, String content, int idx){
        Call<ExhibitCollectionPutResponse> putCollectionResponse = networkService.putCollectionResponse(token, new CollectionPutBody(content), idx);
        putCollectionResponse.enqueue(new Callback<ExhibitCollectionPutResponse>() {
            @Override
            public void onResponse(Call<ExhibitCollectionPutResponse> call, Response<ExhibitCollectionPutResponse> response) {
                if(response.isSuccessful()){
                    ApplicationController.getInstance().makeToast("수정 완료.");
                    EventBus.getInstance().post(EventCode.EVENT_CODE_COLLECTION_PUT);
                }else{
                    Log.v(TAG,"putCollectionFail");
                }
            }

            @Override
            public void onFailure(Call<ExhibitCollectionPutResponse> call, Throwable t) {
                Log.v(TAG,"checkNetwork");
            }
        });
    }

    public void deleteCollection(String token, int idx){
        Call<ExhibitCollectionDeleteResponse> deleteCollectionResponse = networkService.deleteCollectionResponse(token, idx);
        deleteCollectionResponse.enqueue(new Callback<ExhibitCollectionDeleteResponse>() {
            @Override
            public void onResponse(Call<ExhibitCollectionDeleteResponse> call, Response<ExhibitCollectionDeleteResponse> response) {
                if(response.isSuccessful()){
                    ApplicationController.getInstance().makeToast("삭제 완료.");
                    EventBus.getInstance().post(EventCode.EVENT_CODE_COLLECTION_DELETE);
                    Log.v(TAG,"deletetCollectionSuccess");
                }else{
                    Log.v(TAG,"deletetCollectionFail");
                }
            }

            @Override
            public void onFailure(Call<ExhibitCollectionDeleteResponse> call, Throwable t) {
                Log.v(TAG,"checkNetwork");
            }
        });
    }

    public void postLike(String token, int idx, int count){
        Call<ExhibitLikePostResponse> postLikeResponse = networkService.postLike(token, new ExhibitLikePost(idx, count));
        postLikeResponse.enqueue(new Callback<ExhibitLikePostResponse>() {
            @Override
            public void onResponse(Call<ExhibitLikePostResponse> call, Response<ExhibitLikePostResponse> response) {
                if(response.isSuccessful()){
                    Log.v(TAG,"postLikeSuccess");
                }else{
                    Log.v(TAG,"postLikeFail");
                }
            }

            @Override
            public void onFailure(Call<ExhibitLikePostResponse> call, Throwable t) {
                Log.v(TAG,"checkNetwork");

            }
        });
    }

    public void putLike(String token, int idx, int beforeCount, int afterCount){
        Call<ExhibitLikePutResponse> putLikeResponse = networkService.putLike(token, new ExhibitLikePut(idx, beforeCount, afterCount));
        putLikeResponse.enqueue(new Callback<ExhibitLikePutResponse>() {
            @Override
            public void onResponse(Call<ExhibitLikePutResponse> call, Response<ExhibitLikePutResponse> response) {
                if(response.isSuccessful()){
                    Log.v(TAG,"putLikeSuccess");
                }else{
                    Log.v(TAG,"putLikeFail");
                }
            }

            @Override
            public void onFailure(Call<ExhibitLikePutResponse> call, Throwable t) {
                Log.v(TAG,"checkNetwork");
            }
        });
    }

    public void postHeart(String token, int idx){
        Call<ExhibitHeartPostResponse> postHeartResponse = networkService.postHeart(token, new ExhibitHeartPost(idx));
        postHeartResponse.enqueue(new Callback<ExhibitHeartPostResponse>() {
            @Override
            public void onResponse(Call<ExhibitHeartPostResponse> call, Response<ExhibitHeartPostResponse> response) {
                if(response.isSuccessful()){
                    Log.v(TAG,"postHeartSuccess");

                }else{
                    Log.v(TAG,"postHeartFail");

                }
            }

            @Override
            public void onFailure(Call<ExhibitHeartPostResponse> call, Throwable t) {
                Log.v(TAG,"checkNetwork");
            }
        });
    }

    public void putHeart(String token, int idx){
        Call<ExhibitHeartPutResponse> putHeartResponse = networkService.putHeart(token, new ExhibitHeartPut(idx));
        putHeartResponse.enqueue(new Callback<ExhibitHeartPutResponse>() {
            @Override
            public void onResponse(Call<ExhibitHeartPutResponse> call, Response<ExhibitHeartPutResponse> response) {
                if(response.isSuccessful()){
                    Log.v(TAG,"putHeartSuccess");
                }else{
                    Log.v(TAG,"putHeartFail");
                }
            }

            @Override
            public void onFailure(Call<ExhibitHeartPutResponse> call, Throwable t) {
                Log.v(TAG,"checkNetwork");
            }
        });
    }

    public void login(String id, String pw){
        Call<LoginPostResponse> postLoginResponse = networkService.postLogin(new LoginPost(id, pw));
        postLoginResponse.enqueue(new Callback<LoginPostResponse>() {
            @Override
            public void onResponse(Call<LoginPostResponse> call, Response<LoginPostResponse> response) {
                if (response.isSuccessful()){
                    ApplicationController.getInstance().token = response.body().getResult().getToken();
                    EventBus.getInstance().post(EventCode.EVENT_CODE_LOGIN);
                    Log.v(TAG,"postLoginSuccess");
                }else{
                    EventBus.getInstance().post(EventCode.EVENET_CODE_LOGIN_FAIL);
                    Log.v(TAG,"postLoginFail");
                }
            }

            @Override
            public void onFailure(Call<LoginPostResponse> call, Throwable t) {
                Log.v(TAG,"checkNetwork");
            }
        });
    }

    public void sign(final String id, final String pw1, String pw2, final String email, final String nickname){
        Call<SignPostResponse> postSignResponse = networkService.postSign(new SignPost(id, pw1, pw2, email, nickname));
        postSignResponse.enqueue(new Callback<SignPostResponse>() {
            @Override
            public void onResponse(Call<SignPostResponse> call, Response<SignPostResponse> response) {
                if(response.isSuccessful()){
                    //response.body().getResult().
                    //login(id, pw1);
                    Log.v(TAG,"postSignSuccess");
                    SharedPrefrernceController.setLoginId(ApplicationController.getInstance().getApplicationContext(), id);

                    SharedPrefrernceController.setPasswd(ApplicationController.getInstance().getApplicationContext(), pw1);

                    SharedPrefrernceController.setUserEmail(ApplicationController.getInstance().getApplicationContext(), email);

                    SharedPrefrernceController.setUserNickname(ApplicationController.getInstance().getApplicationContext(), nickname);

                    EventBus.getInstance().post(EventCode.EVENT_CODE_SIGN);
                }else{
                    EventBus.getInstance().post(EventCode.EVENT_CODE_SIGN_FAIL);
                    Log.v(TAG,"postSignFail");
                }
            }

            @Override
            public void onFailure(Call<SignPostResponse> call, Throwable t) {
                Log.v(TAG,"checkNetwork");
            }
        });
    }

    public void facebookSign(String token){
        Call<SignPostResponse> fbSignResponse = networkService.facebookSign(new ExhibitFacebookSignPost(token));
        fbSignResponse.enqueue(new Callback<SignPostResponse>() {
            @Override
            public void onResponse(Call<SignPostResponse> call, Response<SignPostResponse> response) {
                if(response.isSuccessful()){
                    EventBus.getInstance().post(EventCode.EVENT_CODE_FB_SIGN);
                    Log.v(TAG,"facebookSign");
                }else{
                    EventBus.getInstance().post(EventCode.EVENT_CDOE_FB_SIGN_FAIL);
                    Log.v(TAG,"facebookSignFail");
                }
            }
            @Override
            public void onFailure(Call<SignPostResponse> call, Throwable t) {
                Log.v(TAG,"checkNetwork");
            }
        });
    }

    public void facebookLogin(String token){
        Call<LoginPostResponse> fbLoginResponse = networkService.facebookLogin(new ExhibitFacebookLoginPost(token));
        fbLoginResponse.enqueue(new Callback<LoginPostResponse>() {
            @Override
            public void onResponse(Call<LoginPostResponse> call, Response<LoginPostResponse> response) {
                if (response.isSuccessful()){
                    ApplicationController.getInstance().token = response.body().getResult().getToken();
                    EventBus.getInstance().post(EventCode.EVENT_CODE_FB_LOGIN);
                    Log.v(TAG,"facebookLogin");
                }else{
                    EventBus.getInstance().post(EventCode.EVENT_CODE_FB_LOGN_FAIL);
                    Log.v(TAG,"facebookLoginFail");
                }
            }
            @Override
            public void onFailure(Call<LoginPostResponse> call, Throwable t) {
                Log.v(TAG,"checkNetwork");
            }
        });
    }

    public void postFindId(String name, String email){
        Call<FindIdResponse> findIdResponse = networkService.findIdResponse(new FindIdPost(email, name));
        findIdResponse.enqueue(new Callback<FindIdResponse>() {
            @Override
            public void onResponse(Call<FindIdResponse> call, Response<FindIdResponse> response) {
                if(response.isSuccessful()){
                    EventBus.getInstance().post(response.body().getResult().getUser_id());
                }else{
                    EventBus.getInstance().post("");
                    Log.v(TAG,"fidnIdFail");
                }
            }

            @Override
            public void onFailure(Call<FindIdResponse> call, Throwable t) {
                Log.v(TAG,"checkNetwork");
            }
        });
    }

    public void postAuth(String code){
        Call<CheckAuthResponse> checkAuthResponse = networkService.checkAuthResponse(new CheckAuthPost(code));
        checkAuthResponse.enqueue(new Callback<CheckAuthResponse>() {
            @Override
            public void onResponse(Call<CheckAuthResponse> call, Response<CheckAuthResponse> response) {
                if(response.isSuccessful()){
                    EventBus.getInstance().post(true);
                    Log.v(TAG,"authSuccess");
                }else{
                    EventBus.getInstance().post(false);
                    Log.v(TAG,"authFail");
                }
            }
            @Override
            public void onFailure(Call<CheckAuthResponse> call, Throwable t) {
                Log.v(TAG,"checkNetwork");
            }
        });
    }
}
