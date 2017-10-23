package com.yg.yourexhibit.Retrofit.RetrofitPost;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by 2yg on 2017. 10. 22..
 */
@Data
@AllArgsConstructor
public class ExhibitLikePostResult {
    int user_idx;
    int exhibition_idx;
    int like_count;
}
