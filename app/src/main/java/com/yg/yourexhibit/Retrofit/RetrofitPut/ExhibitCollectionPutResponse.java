package com.yg.yourexhibit.Retrofit.RetrofitPut;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by 2yg on 2017. 10. 21..
 */
@Data
@AllArgsConstructor
public class ExhibitCollectionPutResponse {
    boolean status;
    String message;
    ArrayList<ExhibitCollectionPutResult> result;
}
