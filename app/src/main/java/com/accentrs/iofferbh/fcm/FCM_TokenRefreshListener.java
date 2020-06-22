package com.accentrs.iofferbh.fcm;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.accentrs.iofferbh.utils.SharedPreferencesData;
import com.google.firebase.messaging.FirebaseMessagingService;


public class FCM_TokenRefreshListener extends FirebaseMessagingService {
//public class FCM_TokenRefreshListener extends FirebaseInstanceIdService {

    private static final String TAG = FCM_TokenRefreshListener.class.getSimpleName();

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        FCM_SharedPreferenceData.getInstance(this).setFCM_Token(null);
        new SharedPreferencesData(getApplicationContext()).setRegistrationTokenStatus(false);
        Log.d("token refresh ","called");

        Intent intent = new Intent(this, FCM_RegistrationIntentService.class);
        startService(intent);
    }


}
