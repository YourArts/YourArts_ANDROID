package com.yg.yourexhibit.Retrofit;

import com.yg.yourexhibit.Retrofit.RetrofitDelete.ExhibitCollectionDeleteResponse;
import com.yg.yourexhibit.Retrofit.RetrofitDelete.TabSettingDeleteUserResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitCollectionDetailResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitCollectionResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitComingResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitDetailResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitEndResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitGoingResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitSearchResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitWorkResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.TabWatchResult;
import com.yg.yourexhibit.Retrofit.RetrofitGet.TabWishResult;
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
import com.yg.yourexhibit.Retrofit.RetrofitPost.FindPwPost;
import com.yg.yourexhibit.Retrofit.RetrofitPost.FindPwPostResponse;
import com.yg.yourexhibit.Retrofit.RetrofitPost.LoginPost;
import com.yg.yourexhibit.Retrofit.RetrofitPost.LoginPostResponse;
import com.yg.yourexhibit.Retrofit.RetrofitPost.SignPost;
import com.yg.yourexhibit.Retrofit.RetrofitPost.SignPostResponse;
import com.yg.yourexhibit.Retrofit.RetrofitPost.TabSettingPWPost;
import com.yg.yourexhibit.Retrofit.RetrofitPost.TabSettingPWResponse;
import com.yg.yourexhibit.Retrofit.RetrofitPut.CollectionPutBody;
import com.yg.yourexhibit.Retrofit.RetrofitPut.ExhibitCollectionPutResponse;
import com.yg.yourexhibit.Retrofit.RetrofitPut.ExhibitHeartPut;
import com.yg.yourexhibit.Retrofit.RetrofitPut.ExhibitHeartPutResponse;
import com.yg.yourexhibit.Retrofit.RetrofitPut.ExhibitLikePut;
import com.yg.yourexhibit.Retrofit.RetrofitPut.ExhibitLikePutResponse;
import com.yg.yourexhibit.Retrofit.RetrofitPut.TabSettingNamePut;
import com.yg.yourexhibit.Retrofit.RetrofitPut.TabSettingNameResponse;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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
    Call<ExhibitCollectionDetailResponse> getCollectionDetailResponse(
            @Header("token") String token,
            @Path("collection_idx") int collection_idx);

    //콜렉션 작성
    @Multipart
    @POST("collections")
    Call<ExhibitCollectionPostResponse> postCollectionResponse(@Header("token") String token,
                                                               @Part("exhibition_idx") RequestBody exhibition_idx,
                                                               @Part("collection_content") RequestBody collection_content,
                                                               @Part MultipartBody.Part profile_pic);

    //작품 정보 불러오기
    @GET("works/{work_idx}")
    Call<ExhibitWorkResponse> getWorkResponse(@Path("work_idx") int work_idx);

    //콜렉션 삭제
    @DELETE("collections/{collection_idx}")
    Call<ExhibitCollectionDeleteResponse> deleteCollectionResponse(@Header("token") String token,
                                                                   @Path("collection_idx") int collection_idx);

    //콜렉션 수정
    @PUT("collections/{collection_idx}")
    Call<ExhibitCollectionPutResponse> putCollectionResponse(@Header("token") String token,
                                                             @Body CollectionPutBody putBody,
                                                             @Path("collection_idx") int collection_idx
                                                             );

    //좋아요 작성
    @POST("like")
    Call<ExhibitLikePostResponse> postLike(@Header("token") String token,
                                           @Body ExhibitLikePost exhibitLikePost);
    //좋아요 수정
    @PUT("like")
    Call<ExhibitLikePutResponse> putLike(@Header("token") String token,
                                         @Body ExhibitLikePut exhibitLikePut);

    //하트 작성
    @POST("heart")
    Call<ExhibitHeartPostResponse> postHeart(@Header("token") String token,
                                             @Body ExhibitHeartPost exhibitHeartPost);

    @PUT("heart")
    Call<ExhibitHeartPutResponse> putHeart(@Header("token") String token,
                                           @Body ExhibitHeartPut exhibitHeartPut);

    @POST("users/login")
    Call<LoginPostResponse> postLogin(@Body LoginPost loginPost);

    @POST("users/register")
    Call<SignPostResponse> postSign(@Body SignPost signPost);


    // Watch Tab 조회
    @GET("watch")
    Call<TabWatchResult> getWatch(@Header("token") String token);

    // Wish Tab 조회
    @GET("wish")
    Call<TabWishResult> getWish(@Header("token") String token);

    // 닉네임 수정
    @PUT("users")
    Call<TabSettingNameResponse> putName(@Header("token") String token,
                                          @Body TabSettingNamePut tabSettingNamePut);

    // 비번 수정
    @POST("users/edit/pw")
    Call<TabSettingPWResponse> postPW(@Body TabSettingPWPost pwPost);

    // 회원 탈퇴
    @DELETE("users")
    Call<TabSettingDeleteUserResponse> deleteUser(@Header("token") String token);

    @POST("users/fb/register")
    Call<SignPostResponse> facebookSign(@Body ExhibitFacebookSignPost exhibitFacebookSignPost);

    @POST("users/fb/login")
    Call<LoginPostResponse> facebookLogin(@Body ExhibitFacebookLoginPost exhibitFacebookLoginPost);

    @POST("users/find/pw")
    Call<FindPwPostResponse> findPwResponse(@Body FindPwPost findPwPost);

    @POST("users/find/id")
    Call<FindIdResponse> findIdResponse(@Body FindIdPost findIdPost);

    @POST("users/confirm/pw")
    Call<CheckAuthResponse> checkAuthResponse(@Body CheckAuthPost checkAuthPost);
}
