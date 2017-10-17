package com.yg.yourexhibit.Retrofit;

import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitCollectionDetailResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitCollectionResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitComingResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitDetailResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitEndResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitGoingResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitSearchResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitWorkResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
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
    @GET("arts/{art_idx}")
    Call<ExhibitDetailResponse> getDetailResponse(
            @Header("token") String token,
            @Path("art_idx") int art_idx);

    //검색 불러오기
    @GET("search/{search}")
    Call<ArrayList<ExhibitSearchResponse>> getSearchResponse(@Path("search") String search);

    //콜랙션 불러오기
    @GET("collections")
    Call<ExhibitCollectionResponse> getCollectionResponse(@Header("token") String token);

    //콜렉션 상세조회
    @GET("collections/{collection_idx}")
    Call<ExhibitCollectionDetailResponse> getCollectionDetailResponse(@Path("collection_idx") int collection_idx);

    //작품 정보 불러오기
    @GET("works/{work_idx}")
    Call<ExhibitWorkResponse> getWorkResponse(@Path("work_idx") int work_idx);

}
