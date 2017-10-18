package com.yg.yourexhibit.Retrofit.RetrofitGet;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by 2yg on 2017. 10. 17..
 */

@Data
@AllArgsConstructor
public class ExhibitWorkResult {
    int work_idx;
    String work_name;
    String work_size;
    String work_idea;
    String work_style;
    String work_owner;
    String work_image;
}
