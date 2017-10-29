package com.yg.yourexhibit.Retrofit.RetrofitPost;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by 2yg on 2017. 10. 29..
 */
@Data
@AllArgsConstructor
public class CheckAuthResult {
    String user_id;
    String user_nickname;
    String user_email;
}
