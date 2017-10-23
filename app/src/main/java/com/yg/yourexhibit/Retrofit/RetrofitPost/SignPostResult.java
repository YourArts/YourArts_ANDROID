package com.yg.yourexhibit.Retrofit.RetrofitPost;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by 2yg on 2017. 10. 23..
 */

@Data
@AllArgsConstructor
public class SignPostResult {
    int user_idx;
    String user_id;
    String user_nickname;
    String user_email;
    String user_created;
}
