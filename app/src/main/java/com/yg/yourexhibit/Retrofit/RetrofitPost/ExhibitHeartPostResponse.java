package com.yg.yourexhibit.Retrofit.RetrofitPost;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by 2yg on 2017. 10. 22..
 */
@Data
@AllArgsConstructor
public class ExhibitHeartPostResponse {
    boolean status;
    String message;
    ArrayList<ExhibitLikePostResult> result;
}
