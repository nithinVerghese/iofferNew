package com.accentrs.iofferbh.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.provider.Settings;
import android.util.Log;

import com.accentrs.apilibrary.callback.Results;
import com.accentrs.apilibrary.interfaces.ResponseType;
import com.accentrs.apilibrary.utils.SessionSharedPreference;
import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.fcm.FCM_SharedPreferenceData;
import com.accentrs.iofferbh.model.bookmark.BookmarkStatusModel;
import com.accentrs.iofferbh.model.fcm.UserDeviceModel;
import com.accentrs.iofferbh.utils.Constants;
import com.accentrs.iofferbh.utils.SharedPreferencesData;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class WishlistService extends IntentService {

    public static final int WISHLIST_RESULT =100;
    public static final int WISHLIST_REMOVE_RESULT =101;

    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_BOOKMARK = "com.accentrs.iofferbh.service.action.ACTION_BOOKMARK";
    public static final String ACTION_DEVICE = "com.accentrs.iofferbh.service.action.ACTION_DEVICE";
    public static final String ACTION_REMOVE_BOOKMARK = "com.accentrs.iofferbh.service.action.ACTION_REMOVE_BOOKMARK";


    public WishlistService() {
        super("WishlistService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBookmark(Context context,String offerId, ResultReceiver resultReceiver) {
        Intent intent = new Intent(context, WishlistService.class);
        intent.setAction(ACTION_BOOKMARK);
        intent.putExtra(Constants.BOOKMARK_OFFER_ID,offerId);
        intent.putExtra(Constants.RECEIVER_KEY,resultReceiver);
        context.startService(intent);
    }

    public static void startActionRemoveBookmark(Context context,String offerId, ResultReceiver resultReceiver) {
        Intent intent = new Intent(context, WishlistService.class);
        intent.setAction(ACTION_REMOVE_BOOKMARK);
        intent.putExtra(Constants.BOOKMARK_OFFER_ID,offerId);
        intent.putExtra(Constants.RECEIVER_KEY,resultReceiver);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionDevice(Context context,String offerId,ResultReceiver resultReceiver) {
        Intent intent = new Intent(context, WishlistService.class);
        intent.setAction(ACTION_DEVICE);
        intent.putExtra(Constants.BOOKMARK_OFFER_ID,offerId);
        intent.putExtra(Constants.RECEIVER_KEY,resultReceiver);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            ResultReceiver receiver = (ResultReceiver) intent.getParcelableExtra(com.accentrs.iofferbh.utils.Constants.RECEIVER_KEY);
            final String offerId = intent.getStringExtra(Constants.BOOKMARK_OFFER_ID);

            if (ACTION_DEVICE.equals(action)) {
                addUserDevice(receiver,offerId);
            } else if (ACTION_BOOKMARK.equals(action)) {
                hitUserBookmarkApi(receiver,offerId);
            } else if(ACTION_REMOVE_BOOKMARK.equals(action)){
                removeUserBookmark(receiver,offerId);
            }
        }
    }



    private void hitUserBookmarkApi(final ResultReceiver receiver, final String offerId){

        Results mResult = new Results(this);
        mResult.addUserBookmarkApi(createBookmarkObject(offerId));
        mResult.setOnResultsCallBack(new Results.ResultsCallBack() {
            @Override
            public void onSuccess(ResponseType response) {

                BookmarkStatusModel bookmarkModel = new Gson().fromJson(response.getStringResponse().toString(),BookmarkStatusModel.class);

                Bundle mBundle = new Bundle();
                mBundle.putBoolean(Constants.BOOKMARK_STATUS_KEY,bookmarkModel.isStatus());
                mBundle.putString(Constants.BOOKMARK_MESSAGE_KEY,bookmarkModel.getMessage());
                mBundle.putString(Constants.OFFER_ID_KEY,offerId);
                receiver.send(WISHLIST_RESULT,mBundle);

            }

            @Override
            public void onError(String error) {
                Bundle mBundle = new Bundle();
                mBundle.putBoolean(Constants.BOOKMARK_STATUS_KEY,false);
                mBundle.putString(Constants.OFFER_ID_KEY,offerId);
                mBundle.putString(Constants.BOOKMARK_MESSAGE_KEY,getString(R.string.something_went_wrong_text));
                receiver.send(WISHLIST_RESULT,mBundle);
            }
        });

    }


    private void addUserDevice(final ResultReceiver receiver, final String offerId){
        Results mResult = new Results(this);
        mResult.addUserGCMToken(createGCMRegistrationTokenObject(FCM_SharedPreferenceData.getInstance(this.getApplicationContext()).getFCM_Token()));
        mResult.setOnResultsCallBack(new Results.ResultsCallBack() {
            @Override
            public void onSuccess(ResponseType response) {
                Log.d("GCM Token response", response.getStringResponse().toString());

                try {
                    UserDeviceModel userDeviceModel = new Gson().fromJson(response.getStringResponse().toString(),UserDeviceModel.class);
                    new SharedPreferencesData(getApplicationContext()).setRegistrationTokenStatus(true);
                    new SharedPreferencesData(getApplicationContext()).setUserId(userDeviceModel.getUserId());
                    new SessionSharedPreference(getApplicationContext()).setUserId(userDeviceModel.getUserId());
                    hitUserBookmarkApi(receiver,offerId);

                }catch (Exception ex){
                    ex.printStackTrace();
                }



            }

            @Override
            public void onError(String error) {
                Bundle mBundle = new Bundle();
                mBundle.putBoolean(Constants.BOOKMARK_STATUS_KEY,false);
                mBundle.putString(Constants.OFFER_ID_KEY,offerId);
                mBundle.putString(Constants.BOOKMARK_MESSAGE_KEY,getString(R.string.something_went_wrong_text));
                receiver.send(WISHLIST_RESULT,mBundle);
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


    private String uniqueDeviceId() {
        return Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }


    private JSONObject createBookmarkObject(String offerId) {


        JSONObject bookmarkJsonObject = new JSONObject();
        try {

            bookmarkJsonObject.put(Constants.OFFER_ID_KEY, offerId);


        } catch (Exception e) {
            e.printStackTrace();
        }


        return bookmarkJsonObject;

    }




    private void removeUserBookmark(final ResultReceiver receiver, final String offerId){

        Results mResults = new Results(this);
        mResults.removeUserBookmarkApi(offerId);
        mResults.setOnResultsCallBack(new Results.ResultsCallBack() {
            @Override
            public void onSuccess(ResponseType response) {

                BookmarkStatusModel bookmarkModel = new Gson().fromJson(response.getStringResponse().toString(),BookmarkStatusModel.class);


                Bundle mBundle = new Bundle();
                mBundle.putBoolean(Constants.BOOKMARK_STATUS_KEY,bookmarkModel.isStatus());
                mBundle.putString(Constants.BOOKMARK_MESSAGE_KEY,bookmarkModel.getMessage());
                mBundle.putString(Constants.OFFER_ID_KEY,offerId);
                receiver.send(WISHLIST_REMOVE_RESULT,mBundle);

            }

            @Override
            public void onError(String error) {
                Bundle mBundle = new Bundle();
                mBundle.putBoolean(Constants.BOOKMARK_STATUS_KEY,false);
                mBundle.putString(Constants.OFFER_ID_KEY,offerId);
                mBundle.putString(Constants.BOOKMARK_MESSAGE_KEY,getString(R.string.something_went_wrong_text));
                receiver.send(WISHLIST_REMOVE_RESULT,mBundle);
            }
        });



    }










}
