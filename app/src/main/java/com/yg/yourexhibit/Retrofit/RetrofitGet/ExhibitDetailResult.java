package com.yg.yourexhibit.Retrofit.RetrofitGet;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by 2yg on 2017. 10. 11..
 */

@Data
@AllArgsConstructor
public class ExhibitDetailResult {
    int exhibition_idx;
    String exhibition_name;
    String exhibition_stard_date;
    String exhibition_end_date;
    String exhibition_start_time;
    String exhibition_end_time;
    String exhibition_location;
    String exhibition_description;
    String exhibition_picture;
    double avg;
    int like_count;
    int heart_used;
    ArrayList<ExhibitDetailImages> images;
}
