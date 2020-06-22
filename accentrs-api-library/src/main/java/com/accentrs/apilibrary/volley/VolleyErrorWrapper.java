package com.accentrs.apilibrary.volley;


import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.lang.ref.WeakReference;

public class VolleyErrorWrapper implements Response.ErrorListener {

    private final Response.ErrorListener mErrorListener;
    WeakReference reference;
    private String result;

    public VolleyErrorWrapper(Context activity, Response.ErrorListener errorListener) {
        reference = new WeakReference<>(activity);
        mErrorListener = errorListener;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Activity activity;

        if ((activity = (Activity) reference.get()) != null) {
//            Utils.createAlertDialog(activity," Login Again ","Login Again").show();
            int statusCode = -1;
            NetworkResponse networkResponse = null;

            if (error != null)
                networkResponse = error.networkResponse;
            if (networkResponse != null) {
                statusCode = networkResponse.statusCode;
                result = new String(networkResponse.data);
            }

            NetworkResponse response = error.networkResponse;
            if (response != null && response.data != null) {
               String json = new String(response.data);
//                json = Utils.trimMessage(json, "message");
                if (json != null) {
//                    if( ((BaseActivity) activity).getShowErrorFromWrapper()) {
//                        ((BaseActivity) activity).showSnackbar(((BaseActivity) activity).mainView, json);
//                    }
                }
            }
//            access token expiry
            Log.e(" ERROR From Server ", "statusCode : " + statusCode + " errorCode : " + statusCode + " result : " + result);

            if (statusCode == 477) {
//                if (!activity.isFinishing())
//                    Utils.signInAlertDialog(activity, false);
            }
        }
        mErrorListener.onErrorResponse(error);
    }

}
