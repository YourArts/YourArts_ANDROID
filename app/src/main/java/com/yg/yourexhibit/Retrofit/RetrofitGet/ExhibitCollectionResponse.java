package com.yg.yourexhibit.Retrofit.RetrofitGet;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by 2yg on 2017. 10. 16..
 */
@Data
@AllArgsConstructor
public class ExhibitCollectionResponse {
    boolean status;
    String message;
    ArrayList<ExhibitCollectionResult> result;
}
