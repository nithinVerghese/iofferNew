package com.accentrs.iofferbh.fcm;

import android.content.Context;
import android.content.SharedPreferences;


public class FCM_SharedPreferenceData {

    private SharedPreferences mPreferences;
    private static FCM_SharedPreferenceData mPrefrence;
    private static final String GCM_TOKEN = "gcm_token";


    public static synchronized FCM_SharedPreferenceData getInstance(Context context) {
        if (mPrefrence == null) {
            mPrefrence = new FCM_SharedPreferenceData(context);
        }
        return mPrefrence;
    }

    private FCM_SharedPreferenceData(Context context) {
        mPreferences = context.getSharedPreferences("gcmSharedPrefrence", Context.MODE_PRIVATE);
    }

    public String getFCM_Token() {
        return mPreferences.getString(GCM_TOKEN, null);
    }

    public void setFCM_Token(String token) {
        mPreferences.edit().putString(GCM_TOKEN, token).apply();
    }
}
