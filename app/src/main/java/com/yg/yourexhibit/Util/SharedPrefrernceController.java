package com.yg.yourexhibit.Util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 2yg on 2017. 10. 18..
 */

public class SharedPrefrernceController {
    private static final String USER = "user";
    private static final String LOGIN_TYPE = "loginType";
    private static final String FIREBASE_TOKEN = "firebaseToken";
    private static final String NICKNAME = "nickname";
    private static final String EMAIL = "email";
    private static final String PASSWD = "passwd";
    private static final String IMAGE = "image";
    private static final String SELETED = "seleted";



    public static void setLoginType(Context context, String loginType){
        SharedPreferences pref = context.getSharedPreferences(USER,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(LOGIN_TYPE, loginType);
        editor.commit();
    }
    public static String getLoginType(Context context){
        SharedPreferences pref = context.getSharedPreferences(USER, context.MODE_PRIVATE);
        String loginType = pref.getString(LOGIN_TYPE,"");
        return loginType;
    }
    public static void setFirebaseToken(Context context,String token){
        SharedPreferences pref = context.getSharedPreferences(USER,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(FIREBASE_TOKEN,token);
        editor.commit();
    }

    public static String getFirebaseToken(Context context){
        SharedPreferences pref = context.getSharedPreferences(USER, Context.MODE_PRIVATE);
        return pref.getString(FIREBASE_TOKEN, "");
    }

    public static void setUserEmail(Context context,String email){
        SharedPreferences pref = context.getSharedPreferences(USER,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(EMAIL,email);
        editor.commit();
    }
    public static String getUserEmail(Context context){
        SharedPreferences pref = context.getSharedPreferences(USER, context.MODE_PRIVATE);
        return pref.getString(EMAIL,"");
    }

    public static void setPasswd(Context context, String passwd){
        SharedPreferences pref = context.getSharedPreferences(USER,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PASSWD,passwd);
        editor.commit();
    }

    public static String getPasswd(Context context){
        SharedPreferences pref = context.getSharedPreferences(USER, context.MODE_PRIVATE);
        return pref.getString(PASSWD,"");
    }


    public static void setUserNickname(Context context, String nickname){
        SharedPreferences pref = context.getSharedPreferences(USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(NICKNAME, nickname);
        editor.commit();
    }

    public static String getUserNickname(Context context){
        SharedPreferences pref = context.getSharedPreferences(USER, Context.MODE_PRIVATE);
        return pref.getString(NICKNAME, "");
    }

    public static void setUserImage(Context context, String image){
        SharedPreferences pref = context.getSharedPreferences(USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(IMAGE, image);
        editor.commit();
    }

    public static String getUserImage(Context context){
        SharedPreferences pref = context.getSharedPreferences(USER, Context.MODE_PRIVATE);
        return pref.getString(IMAGE, "");
    }

    public static void setSelected(Context context, boolean selected){
        SharedPreferences pref = context.getSharedPreferences(USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(SELETED, selected);
        editor.commit();
    }

    public static boolean getSelected(Context context){
        SharedPreferences pref = context.getSharedPreferences(USER, Context.MODE_PRIVATE);
        return pref.getBoolean(SELETED, false);
    }
}
