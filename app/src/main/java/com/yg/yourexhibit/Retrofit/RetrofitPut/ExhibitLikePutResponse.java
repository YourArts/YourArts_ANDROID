package com.yg.yourexhibit.Retrofit.RetrofitPut;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by 2yg on 2017. 10. 22..
 */
@Data
@AllArgsConstructor
public class ExhibitLikePutResponse {
    boolean status;
    String message;
    ExhibitLikePutResult result;
}
