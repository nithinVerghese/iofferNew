package com.accentrs.apilibrary.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.NetworkResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


public class Utils {

    public static boolean isConnectedToInternet(Context context) {
        if(context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnectedOrConnecting();
        }

        return false;
    }

    public static String getHeaderfromAPIResponseVolley(String header_key,
                                                        Map<String, String> headers) {

        String data = headers.get(header_key);
        if (data != null) {
            return data;
        }
        return "";
    }


    public static String trimMessage(String json, String key) {
        String trimmedString = null;

        try {
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }


    public static String  setErrorMessage(NetworkResponse networkResponse){
        String msg = null;
        NetworkResponse response = networkResponse;
        if (response != null && response.data != null) {
            msg = new String(response.data);
//            msg = Utils.trimMessage(msg, "message");
            return msg;
        }
        return null;
    }

    public static boolean isNull(String value) {
        if (value.equals("") && value.length() < 1) {
            return true;
        } else {
            return false;
        }
    }

    public static String formateDate(String date) {
        SimpleDateFormat sdfg = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date d1 = null;
        try {
            d1 = sdfg.parse(date);
            return new SimpleDateFormat("dd MMM yyyy").format(d1).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getStringFromJsonObject(JSONObject data,String key){
        try {
            if (data.has(key)) {
                return data.getString(key);
            } else
                return "";
        }catch (JSONException e){
            e.printStackTrace();
            return "";
        }
    }
}
