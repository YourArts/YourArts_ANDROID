package com.yg.yourexhibit.Retrofit.RetrofitPost;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by 2yg on 2017. 10. 23..
 */

@Data
@AllArgsConstructor
public class SignPost {
    String id;
    String pw1;
    String pw2;
    String nickname;
}
