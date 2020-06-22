package com.accentrs.iofferbh.application;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.accentrs.iofferbh.utils.Constants;
import com.accentrs.iofferbh.utils.SharedPreferencesData;
import com.crashlytics.android.Crashlytics;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Locale;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Ravi on 11-09-2017.
 */

public class IOfferBhApplication extends Application {


    private HashMap<String, String> bookmarksItemHashMap;
    private Configuration config;
    public static String localeCountry;
    public static String localeLanguage;
    private static Context context;
    String ACTION_SEND_LOG                                 = "com.technapse.iofferbh.SEND_LOG";


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        localeLanguage = Locale.getDefault().getLanguage();
        localeCountry = Locale.getDefault().getCountry();

        final Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(true)           // Enables Crashlytics debugger
                .build();
        Fabric.with(fabric);

//        Thread.setDefaultUncaughtExceptionHandler(this::handleUncaughtException);

    }

    public static Context getInstance() {
        return IOfferBhApplication.context;
    }


    public void handleUncaughtException(Thread thread, Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);

        Intent intent = new Intent();
        intent.setAction(ACTION_SEND_LOG);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("exception", sw.toString());
        startActivity(intent);
        System.exit(1);
    }


    @Override
    protected void attachBaseContext(Context base) {
        MultiDex.install(this);
        super.attachBaseContext(updateBaseContextLocale(base));
    }

    public void setUserBookmarkList(HashMap<String, String> bookmarksItemHashMap) {
        this.bookmarksItemHashMap = bookmarksItemHashMap;
    }

    public HashMap<String, String> getBookmarksItemHashMap() {
        if (bookmarksItemHashMap == null)
            return new HashMap<>();
        else
            return bookmarksItemHashMap;
    }

    public static Context getIntsance() {
        return context;
    }

//    public void setAppLocale() {
//        String lang = new SharedPreferencesData(this).getLanguage();
//        Locale locale;
//        Log.d("RRR LANG",lang+"0");
//        if (lang.isEmpty()) {
//            locale = new Locale(Constants.DEFAULT_LOCALE_LANGUAGE, Constants.DEFAULT_LOCALE_COUNTRY);
//        } else {
//            locale = new Locale(lang,"BH");
//        }
//
//        setLocale(locale);
//    }

    public Context updateBaseContextLocale(Context context) {
        String language = new SharedPreferencesData(context).getLanguage();
        Locale locale = new Locale(language,"BH");
        Locale.setDefault(locale);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResourcesLocale(context, locale);
        }

        return updateResourcesLocaleLegacy(context, locale);
    }

    @TargetApi(Build.VERSION_CODES.N)
    private Context updateResourcesLocale(Context context, Locale locale) {
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration);
    }

    @SuppressWarnings("deprecation")
    private Context updateResourcesLocaleLegacy(Context context, Locale locale) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return context;
    }
}
