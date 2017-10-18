package com.yg.yourexhibit.Retrofit.RetrofitGet;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by 2yg on 2017. 10. 17..
 */

@Data
@AllArgsConstructor
public class ExhibitCollectionDetailResponse {
    boolean status;
    String message;
    ExhibitCollectionDetailResult result;

}
