package com.yg.yourexhibit.Retrofit.RetrofitGet;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by 2yg on 2017. 10. 9..
 */
@Data
@AllArgsConstructor
public class ExhibitGoingResult {
    int exhibition_idx;
    String exhibition_name;
    String exhibition_stard_date;
    String exhibition_end_date;
    int start_date;
    int end_date;
    String exhibition_picture;
    double avg;
    String flag;
}
