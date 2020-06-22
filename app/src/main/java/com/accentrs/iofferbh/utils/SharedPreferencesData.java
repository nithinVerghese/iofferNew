package com.accentrs.iofferbh.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Random;


public class SharedPreferencesData {

    private final SharedPreferences sharedPrefs;
    private final String ACCESS_TOKEN = "access_token";
    private final String USER_ACCESS_TOKEN = "user_access_token";
    private final String LOGIN_STATUS = "login_status";
    private final String FIRSTNAME_KEY = "firstname";
    private final String LASTNAME_KEY = "lastname";
    private final String EMAIL_KEY = "email";
    private final String PHONE_NUMBER = "phone_number";
    private final String REGISTRATION_TOKEN_STATUS = "registration_token_status";
    private static final String LANGUAGE_SELECTED = "language";
    private static final String USER_ID = "user_id";

    private final String SHOW_NOTIFICATION = "show_notification";
    private final String NOTIFICATION_COUNT = "notification_count";
    private final String NOTIFICATION_OTHER_SELECTED_COUNT = "notification_other_selected_count";

    private final String NOTIFICATION_CURRENT_DATE = "notification_current_date";
    private final String USER_NOTIFICATION_LIVE_COUNT = "notification_user_live_count";

    private final String USER_NOTIFICATION_COUNT_SELECTED_STATUS = "user_notification_count_selected_status";

    private final String USER_GAME_PLAYED_STATUS = "user_game_played_status";

    private final String USER_RANDOM_DEVEICE_ID = "user_random_device_id";

    private final String ADS_POPUP_DATA = "ads_popup_data";

    private final String SPINNING_WHEEL_DATA = "spinning_wheel_data";

    private final String ADS_POPUP_VIEWED_STATUS = "ads_popup_viewed_status";

    private final String LOGIN_STAT  = "LOGIN_STAT";

    private final String USER_ID_LOGIN  = "USER_ID_LOGIN";

    private final String CART = "cart";

    private final String AMOUNT = "AMOUNT";

    private final String DEC = "dec";

    private final String S_ID = "s_id";

    private final String C_NAM = "c_nam";

    private final String QTY = "qty";

    private final String S_NM = "s_nm";

    private final String D_ID = "did";

    private final String MSG = "msg";

    private final String D_PAY = "d_pay";

    private final String C_PAY = "c_pay";

    private final String FLAG_coupon = "FLAG_coupon";



    public SharedPreferencesData(Context cntx) {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setAccessToken(String access_token) {
        sharedPrefs.edit().putString(ACCESS_TOKEN, access_token).apply();
    }

    public void setUserName(String firstName) {
        sharedPrefs.edit().putString(FIRSTNAME_KEY, firstName).apply();
    }

    public String getUserName() {
        return sharedPrefs.getString(FIRSTNAME_KEY, "");
    }

    public void setUserPhoneNumber(String phoneNumber) {
        sharedPrefs.edit().putString(PHONE_NUMBER, phoneNumber).apply();
    }

    public String getUserPhoneNumber() {
        return sharedPrefs.getString(PHONE_NUMBER, "");
    }


    public boolean getRegistrationTokenStatus() {
        return sharedPrefs.getBoolean(REGISTRATION_TOKEN_STATUS, false);
    }

    public void setRegistrationTokenStatus(boolean status) {
        sharedPrefs.edit().putBoolean(REGISTRATION_TOKEN_STATUS, status).apply();
    }

    public String getLanguage() {
        return sharedPrefs.getString(LANGUAGE_SELECTED, "");
    }

    public void setLanguage(String language) {
        sharedPrefs.edit().putString(LANGUAGE_SELECTED, language).apply();
    }


    //User Id
    public String getUserId() {
        return sharedPrefs.getString(USER_ID, "");
    }

    public void setUserId(String userId) {
        sharedPrefs.edit().putString(USER_ID, userId).apply();
    }


    //Notification status

    public void setShowNotificationStatus(boolean notificationStatus) {
        sharedPrefs.edit().putBoolean(SHOW_NOTIFICATION, notificationStatus).apply();
    }

    public boolean getShowNotificationStatus() {
        return sharedPrefs.getBoolean(SHOW_NOTIFICATION, true);
    }


    public void setNotificationCountPerDay(int count) {
        sharedPrefs.edit().putInt(NOTIFICATION_COUNT, count).apply();
    }


    public int getNotificationCountPerDay() {
        return sharedPrefs.getInt(NOTIFICATION_COUNT, 0);
    }

    public void setNotificationOtherSelectedStatus(boolean status) {
        sharedPrefs.edit().putBoolean(NOTIFICATION_OTHER_SELECTED_COUNT, status).apply();
    }

    public boolean getNotificationOtherSelectedStatus() {
        return sharedPrefs.getBoolean(NOTIFICATION_OTHER_SELECTED_COUNT, false);
    }


    public void setPushNotificationCurrentDate(String date) {
        sharedPrefs.edit().putString(NOTIFICATION_CURRENT_DATE, date).apply();
    }


    public String getPushNotificationCurrentDate() {
        return sharedPrefs.getString(NOTIFICATION_CURRENT_DATE, "");
    }


    //Set live count of notification

    public void setUserNotificationLiveCount(int count) {
        sharedPrefs.edit().putInt(USER_NOTIFICATION_LIVE_COUNT, count).apply();
    }

    public int getUserNotificationLiveCount() {
        return sharedPrefs.getInt(USER_NOTIFICATION_LIVE_COUNT, 0);
    }

    public void setNotificationCountSelectedStatus(boolean status) {
        sharedPrefs.edit().putBoolean(USER_NOTIFICATION_COUNT_SELECTED_STATUS, status).apply();
    }

    public boolean getNotificationCountSelectedStatus() {
        return sharedPrefs.getBoolean(USER_NOTIFICATION_COUNT_SELECTED_STATUS, false);
    }


    public void setUserGamePlayedStatus(boolean status) {
        sharedPrefs.edit().putBoolean(USER_GAME_PLAYED_STATUS, status).apply();
    }

    public boolean getUserGamePlayedStatus() {
        return sharedPrefs.getBoolean(USER_GAME_PLAYED_STATUS, false);
    }

    public int getUserRandomDeviceId() {
        return sharedPrefs.getInt(USER_RANDOM_DEVEICE_ID, new Random().nextInt(99999999));
    }

    public String getAdsPopupData() {
        return sharedPrefs.getString(ADS_POPUP_DATA, "");
    }

    public void setAdsPopupData(String adsPopupData) {
        sharedPrefs.edit().putString(ADS_POPUP_DATA, adsPopupData).apply();
    }

    public String getSpinningWheelData() {
        return sharedPrefs.getString(SPINNING_WHEEL_DATA, "");
    }

    public void setSpinningWheelData(String spinningWheelData) {
        sharedPrefs.edit().putString(SPINNING_WHEEL_DATA, spinningWheelData).apply();
    }

    public boolean getAdsPopupStatus() {
        return sharedPrefs.getBoolean(ADS_POPUP_VIEWED_STATUS, false);
    }

    public void setAdsPopupStatus(boolean status) {
        sharedPrefs.edit().putBoolean(ADS_POPUP_VIEWED_STATUS, status).apply();
    }

    public void setLogin_stat(boolean userId) {
        sharedPrefs.edit().putBoolean(LOGIN_STAT, userId).apply();
        }


    public boolean getLogin_stat() {

        return  sharedPrefs.getBoolean(LOGIN_STAT, false);

    }

    public void setUserID(String userID){
        sharedPrefs.edit().putString(USER_ID_LOGIN, userID).apply();
    }

    public String getUserID1() {
        String getLogin_stat;
        getLogin_stat = sharedPrefs.getString(USER_ID_LOGIN, "12345");
        return getLogin_stat;
    }

    public void putcart(boolean userId) {
        sharedPrefs.edit().putBoolean(CART, userId).apply();
    }

    public boolean getcart() {

        return  sharedPrefs.getBoolean(CART, false);

    }

    public void setAmount(String userID){
        sharedPrefs.edit().putString(AMOUNT, userID).apply();
    }

    public String getAmount() {
        String getLogin_stat;
        getLogin_stat = sharedPrefs.getString(USER_ID_LOGIN, "WRONG");
        return getLogin_stat;
    }

    public void setDec(String userID){
        sharedPrefs.edit().putString(USER_ID_LOGIN, userID).apply();
    }

    public String getDec() {
        String getLogin_stat;
        getLogin_stat = sharedPrefs.getString(USER_ID_LOGIN, "WRONG");
        return getLogin_stat;
    }

    public void setS_ID(String userID){
        sharedPrefs.edit().putString(USER_ID_LOGIN, userID).apply();
    }

    public String get_SID() {
        String getLogin_stat;
        getLogin_stat = sharedPrefs.getString(USER_ID_LOGIN, "WRONG");
        return getLogin_stat;
    }

    public void set_CNAM(String userID){
        sharedPrefs.edit().putString(C_NAM, userID).apply();
    }

    public String get_C_NAM() {
        String getLogin_stat;
        getLogin_stat = sharedPrefs.getString(C_NAM, "WRONG");
        return getLogin_stat;
    }

    public void set_QTY(String userID){
        sharedPrefs.edit().putString(QTY, userID).apply();
    }

    public String get_QTY() {
        String getLogin_stat;
        getLogin_stat = sharedPrefs.getString(QTY, "WRONG");
        return getLogin_stat;
    }

    public void set_S_NM(String userID){
        sharedPrefs.edit().putString(S_NM, userID).apply();
    }

    public String get_S_NM() {
        String getLogin_stat;
        getLogin_stat = sharedPrefs.getString(S_NM, "WRONG");
        return getLogin_stat;
    }

    public void set_D_ID(String userID){
        sharedPrefs.edit().putString(D_ID, userID).apply();
    }

    public String get_D_ID() {
        String getLogin_stat;
        getLogin_stat = sharedPrefs.getString(D_ID, "WRONG");
        return getLogin_stat;
    }

    public void set_MSG(String userID){
        sharedPrefs.edit().putString(MSG, userID).apply();
    }

    public String getMSG() {
        String getLogin_stat;
        getLogin_stat = sharedPrefs.getString(MSG, "WRONG");
        return getLogin_stat;
    }


    public void set_C_PAY(String userID){
        sharedPrefs.edit().putString(C_PAY, userID).apply();
    }

    public String get_C_PAY() {
        String getLogin_stat;
        getLogin_stat = sharedPrefs.getString(C_PAY, "WRONG");
        return getLogin_stat;
    }

    public void set_D_PAY(String userID){
        sharedPrefs.edit().putString(D_PAY, userID).apply();
    }

    public String get_D_PAY() {
        String getLogin_stat;
        getLogin_stat = sharedPrefs.getString(D_PAY, "WRONG");
        return getLogin_stat;
    }


    public void set_FLAG_coupon(int coup_flag){ sharedPrefs.edit().putInt(FLAG_coupon, coup_flag).apply(); }



    public int get_FLAG_coupon() {
        int getLogin_stat;
        getLogin_stat = sharedPrefs.getInt(FLAG_coupon, 0);
        return getLogin_stat;
    }
}
