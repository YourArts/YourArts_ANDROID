package com.yg.yourexhibit.Retrofit.RetrofitPost;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by 2yg on 2017. 10. 29..
 */

@Data
@AllArgsConstructor
public class FindPwPostResponse {
    boolean status;
    String message;
    FindIdResult result;
}
