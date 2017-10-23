package com.yg.yourexhibit.Retrofit.RetrofitDelete;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by 2yg on 2017. 10. 21..
 */

@AllArgsConstructor
@Data
public class ExhibitCollectionDeleteResult {
    int fieldCount;
    int affectedRows;
    int insertId;
    int serverStatus;
    int warningCount;
    String message;
    boolean protocol41;
    int changedRows;
}
