package com.yg.yourexhibit.Retrofit.RetrofitGet;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by 2yg on 2017. 10. 17..
 */

@Data
@AllArgsConstructor
public class ExhibitCollectionDetailResult {
    int collection_idx;
    int user_idx;
    String exhibition_name;
    String collection_content;
    String collection_image;
}
