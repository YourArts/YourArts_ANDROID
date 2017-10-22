package com.yg.yourexhibit.Retrofit.RetrofitPut;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by 2yg on 2017. 10. 22..
 */
@Data
@AllArgsConstructor
public class ExhibitLikePutResult {
    int user_idx;
    int exhibition_idx;
    int like_count;
}
