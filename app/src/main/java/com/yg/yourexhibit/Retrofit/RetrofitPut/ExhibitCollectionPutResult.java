package com.yg.yourexhibit.Retrofit.RetrofitPut;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by 2yg on 2017. 10. 21..
 */

@Data
@AllArgsConstructor
public class ExhibitCollectionPutResult {
    int collection_idx;
    int user_idx;
    int exhibition_idx;
    String collection_content;
    String collection_image;
    String collection_created;
    String collection_updated;
    boolean collection_name;
}
