package com.yg.yourexhibit.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 2yg on 2017. 5. 15..
 */

public class ResourcesUtil {
    public static boolean checkEmail(String email) {

        String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        boolean isNormal = m.matches();
        return isNormal;

    }
}
