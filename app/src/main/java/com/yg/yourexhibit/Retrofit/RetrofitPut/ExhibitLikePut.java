package com.yg.yourexhibit.Retrofit.RetrofitPut;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by 2yg on 2017. 10. 22..
 */
@AllArgsConstructor
@Data
public class ExhibitLikePut {
    int exhibition_idx;
    int like_before_count;
    int like_after_count;
}
