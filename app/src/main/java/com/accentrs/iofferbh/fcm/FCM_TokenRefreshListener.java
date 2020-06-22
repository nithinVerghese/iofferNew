package com.accentrs.iofferbh.fcm;

import android.content.Intent;
import android.util.Log;

import com.accentrs.iofferbh.utils.SharedPreferencesData;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class FCM_TokenRefreshListener extends FirebaseInstanceIdService {

    private static final String TAG = FCM_TokenRefreshListener.class.getSimpleName();

    @Override
    public void onTokenRefresh() {

        FCM_SharedPreferenceData.getInstance(this).setFCM_Token(null);
        new SharedPreferencesData(getApplicationContext()).setRegistrationTokenStatus(false);
        Log.d("token refresh ","called");

        Intent intent = new Intent(this, FCM_RegistrationIntentService.class);
        startService(intent);
    }


}
