package com.accentrs.iofferbh.volley;

import android.content.Context;
import android.text.TextUtils;

import com.accentrs.iofferbh.utils.Constants;
import com.accentrs.iofferbh.utils.SharedPreferencesData;
import com.accentrs.iofferbh.utils.Utils;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class JsonObjRequestVolley extends JsonObjectRequest {

    HashMap<String, String> headersHashMap;
    private Context context;

    public JsonObjRequestVolley(int method, String url, JSONObject jsonRequest,
                                Listener<JSONObject> listener, ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);

    }



    public void putHeaders(HashMap<String, String> headers, Context mContext) {
        this.context = mContext;
        headersHashMap = headers;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headersHashMap;

    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse networkResponse) {
        Map<String, String> responseHeaders = networkResponse.headers;
        String accessToken = Utils.getHeaderfromAPIResponseVolley(Constants.HEADER_ACCESS_TOKEN, responseHeaders);
        if (!TextUtils.isEmpty(accessToken)) {
            new SharedPreferencesData(context).setAccessToken(accessToken);
        }
        return super.parseNetworkResponse(networkResponse);
    }

}
