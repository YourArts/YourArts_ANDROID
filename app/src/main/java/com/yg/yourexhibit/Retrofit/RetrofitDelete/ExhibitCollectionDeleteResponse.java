package com.yg.yourexhibit.Retrofit.RetrofitDelete;

import com.yg.yourexhibit.Retrofit.RetrofitPost.ExhibitCollectionPostResult;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by 2yg on 2017. 10. 21..
 */

@AllArgsConstructor
@Data
public class ExhibitCollectionDeleteResponse {
    boolean status;
    String message;
    ExhibitCollectionPostResult result;
}
