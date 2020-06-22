package com.accentrs.apilibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class SessionSharedPreference {

    private final SharedPreferences sharedPrefs;
    private final String ACCESS_TOKEN = "session_access_token";
    private final String USER_ACCESS_TOKEN = "user_access_token";
    private static final String USER_ID = "user_id_data";




    public SessionSharedPreference(Context cntx) {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public String getSessionAccessToken() {
        return sharedPrefs.getString(ACCESS_TOKEN, "");
    }


    public void setSessionAccessToken(String access_token) {
        sharedPrefs.edit().putString(ACCESS_TOKEN, access_token).apply();
    }

    public void setUserAccessToken(String access_token) {
        sharedPrefs.edit().putString(USER_ACCESS_TOKEN, access_token).apply();
    }

    public String getUserAccessToken() {
        return sharedPrefs.getString(USER_ACCESS_TOKEN, "");
    }

    //User Id
    public String getUserId() {
        return sharedPrefs.getString(USER_ID, "");
    }

    public void setUserId(String userId) {
        sharedPrefs.edit().putString(USER_ID, userId).apply();
    }


}
