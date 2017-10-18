package com.yg.yourexhibit.Retrofit.RetrofitGet;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by 2yg on 2017. 10. 16..
 */
@Data
@AllArgsConstructor
public class ExhibitCollectionResult {
    int collection_idx;
    int user_idx;
    int exhibition_idx;
    String collection_content;
    String collection_image;
}
