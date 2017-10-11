package com.yg.yourexhibit.Retrofit;

import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitComingResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitDetailResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitEndResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitGoingResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by 2yg on 2017. 10. 8..
 */

public interface NetworkService {

    //끝난 전시 불러오기
    @GET("arts/done")
    Call<ExhibitEndResponse> endResponse();

    //진행 중인 전시 불러오기
    @GET("arts/doing")
    Call<ExhibitGoingResponse> goingResponse();

    //다가올 전시 불러오기
    @GET("arts/todo")
    Call<ExhibitComingResponse> comingResponse();

    //해당 전시 상세정보 불러오기
    @GET("arts/{idx}")
    Call<ExhibitDetailResponse> getDetailResponse(@Path("idx") int idx);

}
