package com.accentrs.iofferbh.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.accentrs.apilibrary.callback.Results;
import com.accentrs.apilibrary.interfaces.ResponseType;
import com.accentrs.apilibrary.utils.SessionSharedPreference;
import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.application.IOfferBhApplication;
import com.accentrs.iofferbh.fcm.FCM_RegistrationIntentService;
import com.accentrs.iofferbh.fcm.FCM_SharedPreferenceData;
import com.accentrs.iofferbh.model.bookmark.BookmarkModel;
import com.accentrs.iofferbh.model.bookmark.BookmarksItem;
import com.accentrs.iofferbh.model.fcm.UserDeviceModel;
import com.accentrs.iofferbh.model.home.OffersItem;
import com.accentrs.iofferbh.utils.Constants;
import com.accentrs.iofferbh.utils.SharedPreferencesData;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int PERMISSION_DENIED_REQUEST_CODE = 204;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 123;
    private static final String TAG = SplashScreenActivity.class.getSimpleName();
    private static final int PERMISSION_REQUEST_CODE = 200;
    private Handler mHandler = new Handler();
    private long delay = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (!checkPermission()) {
                requestPermission();

            } else {
                mHandler.postDelayed(mUpdateTimeTask, 1000);

            }
        } else {
            mHandler.postDelayed(mUpdateTimeTask, 1000);

        }
        initializeFCM();

    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE, READ_SMS}, PERMISSION_REQUEST_CODE);

    }

    private boolean checkPermission() {
        int result = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            result = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        }
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);


        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean readPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (readPermission && writePermission) {
                        mHandler.postDelayed(mUpdateTimeTask, 2000);
                    } else {

                        if (!readPermission && !writePermission) {

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)) {
                                    showMessageOKCancel(getString(R.string.txt_read_write_permission_txt),
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                        requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE},
                                                                PERMISSION_REQUEST_CODE);
                                                    }
                                                }
                                            }, getString(R.string.txt_ok));
                                    return;
                                } else {
                                    showMessageOKCancel(getString(R.string.txt_read_write_permission_txt),
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    openPermissionScreenForRuntimePermission();
                                                }
                                            }, getString(R.string.text_settings));
                                }
                            }

                        }

                    }
                }


                break;


        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener, String actionMessage) {
        new AlertDialog.Builder(SplashScreenActivity.this)
                .setMessage(message)
                .setPositiveButton(actionMessage, okListener)
                .setCancelable(false)
                .create()
                .show();
    }

    private void openPermissionScreenForRuntimePermission() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, PERMISSION_DENIED_REQUEST_CODE);
    }

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            fetchUserWishlistData();
        }
    };

    private void setSplash() {
        String action = getIntent().getAction();
        if ((action != null && action.equals(Intent.ACTION_VIEW))) {
            try {
                Uri data = getIntent().getData();


                String deeplinkingIntentData = data.toString();

                Toast.makeText(this, deeplinkingIntentData, Toast.LENGTH_LONG).show();

                if (deeplinkingIntentData.contains("share?id=")) {
                    String deepLinkArray[] = deeplinkingIntentData.split("\\?");
                    String contentFileId = deepLinkArray[1].split("=")[1];
                    Constants.IS_FROM_DEEPLINK = true;
                    fetchOfferDetail(contentFileId);
                } else {
                    gotoHomeScreen();
                }

            } catch (Exception exp) {
                gotoHomeScreen();
            }


        } else {
            checkNotification();
        }

    }

    private void fetchOfferDetail(String contentFileId) {
//        showHomeProgress();
        Results mResult = new Results(this);
        mResult.companyDetail(contentFileId);
        mResult.setOnResultsCallBack(new Results.ResultsCallBack() {
            @Override
            public void onSuccess(ResponseType response) {
//                hideHomeProgress();
                OffersItem offersItem = new Gson().fromJson(response.getStringResponse().toString(), OffersItem.class);
                gotoOfferDetail(offersItem);

            }

            @Override
            public void onError(String error) {
//                hideHomeProgress();
                gotoHomeScreen();
            }
        });

    }

    multiClickDissable dis = multiClickDissable.Singleton();

    private void gotoOfferDetail(OffersItem offersItem) {


        Boolean x =  dis.disab();

        if (!x){
            return;
        }


        Intent intent = new Intent(this, OfferDetailActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(com.accentrs.apilibrary.utils.Constants.OFFER_DATA_KEY, offersItem);
        intent.putExtras(mBundle);
        startActivity(intent);
        finish();
    }

    private void gotoHomeScreen() {
        Intent intent = new Intent(this, HomeScreenActivity.class);


        startActivity(intent);
        finish();
    }


    private void initializeFCM() {
//        if (checkPlayServices()) {
        initApp();
//        }
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }


    private void initApp() {
        startFCMRegistrationService();
    }

    private void startFCMRegistrationService() {

        boolean isTokenGenerated = FCM_SharedPreferenceData.getInstance(this.getApplicationContext()).getFCM_Token() != null;

        Intent intent = new Intent(this, FCM_RegistrationIntentService.class);
        startService(intent);
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreenActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        nbutton.setTextColor(Color.BLACK);

    }


    private void fetchUserWishlistData() {

        String userDeviceId = new SharedPreferencesData(this).getUserId();
        if (TextUtils.isEmpty(userDeviceId)) {
            addUserDevice();


        } else {
            hitUserBookmarkApi();

        }

    }


    private void hitUserBookmarkApi() {

        Results mResult = new Results(this);
        mResult.userBookmarkApi();
        mResult.setOnResultsCallBack(new Results.ResultsCallBack() {
            @Override
            public void onSuccess(ResponseType response) {

                BookmarkModel bookmarkModel = new Gson().fromJson(response.getStringResponse().toString(), BookmarkModel.class);
                if (bookmarkModel != null && bookmarkModel.getBookmarks().size() > 0) {

                    HashMap<String, String> bookmarksItemHashMap = new HashMap<>();

                    for (BookmarksItem bookmark : bookmarkModel.getBookmarks()) {
                        bookmarksItemHashMap.put(bookmark.getId(), bookmark.getNameEn());
                    }
                    ((IOfferBhApplication) getApplicationContext()).setUserBookmarkList(bookmarksItemHashMap);
                }

                setSplash();

            }

            @Override
            public void onError(String error) {
                setSplash();
            }
        });

    }


    private void addUserDevice() {
        Results mResult = new Results(this);
        mResult.addUserGCMToken(createGCMRegistrationTokenObject(FCM_SharedPreferenceData.getInstance(this.getApplicationContext()).getFCM_Token()));
        mResult.setOnResultsCallBack(new Results.ResultsCallBack() {
            @Override
            public void onSuccess(ResponseType response) {

                try {
                    UserDeviceModel userDeviceModel = new Gson().fromJson(response.getStringResponse().toString(), UserDeviceModel.class);
                    new SharedPreferencesData(getApplicationContext()).setRegistrationTokenStatus(true);
                    new SharedPreferencesData(getApplicationContext()).setUserId(userDeviceModel.getUserId());
                    new SessionSharedPreference(getApplicationContext()).setUserId(userDeviceModel.getUserId());
                    hitUserBookmarkApi();

                    Toast.makeText(SplashScreenActivity.this, userDeviceModel.toString(), Toast.LENGTH_SHORT).show();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }


            }

            @Override
            public void onError(String error) {

            }
        });

    }


    private JSONObject createGCMRegistrationTokenObject(String token) {


        JSONObject mGCMDeviceJsonObject = new JSONObject();
        try {

            mGCMDeviceJsonObject.put(Constants.GCM_ID_KEY, token);
            mGCMDeviceJsonObject.put(Constants.DEVICE_ID_KEY, uniqueDeviceId());

            //Log.e("user_id:  ",mGCMDeviceJsonObject.put(Constants.DEVICE_ID_KEY, uniqueDeviceId()).toString());
            Toast.makeText(this, mGCMDeviceJsonObject.put(Constants.DEVICE_ID_KEY, uniqueDeviceId()).toString(),
                    Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return mGCMDeviceJsonObject;

    }


    private String uniqueDeviceId() {
        return Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

    }


    private void checkNotification() {
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(Constants.PUSH_NOTIFICATION_FROM_KEY)) {
            String fromKey = getIntent().getExtras().getString(Constants.PUSH_NOTIFICATION_FROM_KEY);
            int offerId = getIntent().getExtras().getInt(Constants.OFFER_ID_KEY);

            if (offerId > 0) {
                if (fromKey != null) {
                    Constants.IS_FROM_DEEPLINK = true;
                    fetchOfferDetail(String.valueOf(offerId));
                }
            } else {
                gotoHomeScreen();
            }

        } else {
            gotoHomeScreen();
        }
    }


}
