package com.yg.yourexhibit.Retrofit.RetrofitPost;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by 2yg on 2017. 10. 22..
 */
@Data
@AllArgsConstructor
public class ExhibitLikePostResponse {
    boolean status;
    String message;
    ExhibitLikePostResult result;
}
