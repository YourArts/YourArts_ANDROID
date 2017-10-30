package com.yg.yourexhibit.Retrofit.RetrofitGet;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by 2yg on 2017. 10. 15..
 */

@Data
@AllArgsConstructor
public class ExhibitSearchResult {
    int exhibition_idx;
    String exhibition_name;
    String exhibition_start_date;
    String exhibition_end_date;
    String exhibition_picture;
    int start_date;
    int end_date;
    String flag;
}
