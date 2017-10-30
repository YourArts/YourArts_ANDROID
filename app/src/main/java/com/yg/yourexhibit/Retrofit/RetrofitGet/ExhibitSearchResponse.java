package com.yg.yourexhibit.Retrofit.RetrofitGet;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by 2yg on 2017. 10. 15..
 */

@Data
@AllArgsConstructor
public class ExhibitSearchResponse {
    boolean status;
    String message;
    ArrayList<ExhibitSearchResult> result;
}
