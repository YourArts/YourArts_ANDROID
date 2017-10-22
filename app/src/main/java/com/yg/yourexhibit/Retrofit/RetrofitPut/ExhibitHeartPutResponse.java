package com.yg.yourexhibit.Retrofit.RetrofitPut;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by 2yg on 2017. 10. 22..
 */
@Data
@AllArgsConstructor

public class ExhibitHeartPutResponse {
    boolean status;
    String message;
    ArrayList<ExhibitHeartPutResult> result;
}
