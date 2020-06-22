package com.accentrs.apilibrary.volley;

import android.content.Context;
import android.util.ArrayMap;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.Map;

public class JsonArrayRequestVolley_ArrayMap extends JsonArrayRequest {

    ArrayMap<String, String> headersHashMap;
    private Context context;

//    public JsonArrayRequestVolley(int method, String url, JSONArray jsonRequest, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
//        super(method, url, jsonRequest, listener, errorListener);
//    }

    public JsonArrayRequestVolley_ArrayMap(String url, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    public void putHeaders(ArrayMap<String, String> headers, Context mContext) {
        this.context = mContext;
        headersHashMap = headers;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headersHashMap;

    }

    @Override
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse networkResponse) {
        Map<String, String> responseHeaders = networkResponse.headers;
//        String accessToken = Utils.getHeaderfromAPIResponseVolley(Constants.HEADER_ACCESS_TOKEN, responseHeaders);
//        if (!TextUtils.isEmpty(accessToken)) {
//            new SharedPreferencesData(context).setAccessToken(accessToken);
//            new SharedPreferencesData(context).getAccessToken();
//        }
        return super.parseNetworkResponse(networkResponse);
    }

}

