package com.yg.yourexhibit.Retrofit.RetrofitGet;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by 2yg on 2017. 10. 11..
 */

@Data
@AllArgsConstructor
public class ExhibitDetailResponse {
    boolean status;
    String message;
    ExhibitDetailResult result;
}
