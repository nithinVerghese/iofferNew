package com.accentrs.iofferbh.fcm;

import android.app.IntentService;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import com.accentrs.apilibrary.callback.Results;
import com.accentrs.apilibrary.interfaces.ResponseType;
import com.accentrs.apilibrary.utils.SessionSharedPreference;
import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.interfaces.ExponentialBackoffRunnable;
import com.accentrs.iofferbh.model.fcm.UserDeviceModel;
import com.accentrs.iofferbh.utils.Constants;
import com.accentrs.iofferbh.utils.SharedPreferencesData;
import com.accentrs.iofferbh.utils.Utils;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import org.json.JSONObject;


public class FCM_RegistrationIntentService extends IntentService {

    ExponentialBackoffRunnable mGetTokenRunnable;


    public FCM_RegistrationIntentService() {
        super(FCM_RegistrationIntentService.class.getSimpleName());
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mGetTokenRunnable = new GetTokenRunnable();
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        final String token = FCM_SharedPreferenceData.getInstance(this.getApplicationContext()).getFCM_Token();
        Log.d("FCM token frm pref", token + "  asfkpoasdkas");
//        if (TextUtils.isEmpty(token) | (!new SharedPreferencesData(getApplicationContext()).getRegistrationTokenStatus())) {
        Utils.exponentialBackOffOperation(mGetTokenRunnable, 1000, 5);
//        }

    }

    private class GetTokenRunnable implements ExponentialBackoffRunnable {

        boolean mTokenGeneratedSuccessfully;

        @Override
        public boolean isTaskSuccessful() {
            return mTokenGeneratedSuccessfully;
        }

        @Override
        public void run() {

            FirebaseInstanceId instanceID = FirebaseInstanceId.getInstance();
            String senderId = getResources().getString(R.string.gcm_defaultSenderId);
            try {
                // request token that will be used by the server to send push notifications
                String token = instanceID.getToken();
                Log.d("FCM token", "FCM Registration Token: " + token);
                Log.d("Sender Id ", senderId + "");

//                if (!TextUtils.isEmpty(token) | (!new SharedPreferencesData(getApplicationContext()).getRegistrationTokenStatus())) {
                FCM_SharedPreferenceData.getInstance(FCM_RegistrationIntentService.this.getApplicationContext()).setFCM_Token(token);
                mTokenGeneratedSuccessfully = true;
                sendRegistrationToServer(token);

//                }


//                sendRegistrationToServer(token);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        private void sendRegistrationToServer(String token) {

            Results results = new Results(getApplicationContext());
            results.addUserGCMToken(createGCMRegistrationTokenObject(token));
            results.setOnResultsCallBack(new Results.ResultsCallBack() {
                @Override
                public void onSuccess(ResponseType response) {

                    Log.d("GCM Token response", response.getStringResponse().toString());

                    UserDeviceModel userDeviceModel = new Gson().fromJson(response.getStringResponse().toString(),UserDeviceModel.class);

                    new SharedPreferencesData(getApplicationContext()).setRegistrationTokenStatus(true);
                    new SharedPreferencesData(getApplicationContext()).setUserId(userDeviceModel.getUserId());
                    new SessionSharedPreference(getApplicationContext()).setUserId(userDeviceModel.getUserId());

                }

                @Override
                public void onError(String error) {
                    new SharedPreferencesData(getApplicationContext()).setRegistrationTokenStatus(false);
                }
            });
        }

        private JSONObject createGCMRegistrationTokenObject(String token) {
            JSONObject mGCMDeviceJsonObject = new JSONObject();
            try {
                mGCMDeviceJsonObject.put(Constants.GCM_ID_KEY, token);
                mGCMDeviceJsonObject.put(Constants.DEVICE_ID_KEY, uniqueDeviceId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mGCMDeviceJsonObject;
        }
    }

    private String uniqueDeviceId() {

//        int randomDeviceId = new SharedPreferencesData(this).getUserRandomDeviceId();
//        return String.valueOf(randomDeviceId);
        return Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }


}
