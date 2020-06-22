package com.accentrs.iofferbh.fcm;


import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.activity.SplashScreenActivity;
import com.accentrs.iofferbh.helper.GlideApp;
import com.accentrs.iofferbh.utils.Constants;
import com.accentrs.iofferbh.utils.SharedPreferencesData;
import com.bumptech.glide.request.FutureTarget;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;


public class FCM_PushNotificationListener extends FirebaseMessagingService {
    public static final String TAG = FCM_PushNotificationListener.class.getSimpleName();

    public static final int MESSAGE_NOTIFICATION_ID = 435345;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (checkPushNotificationStatus()) {
            createDefaultNotification(remoteMessage);
        }


    }


    private void createDefaultNotification(RemoteMessage remoteMessage) {
        String image = "";
        String header = "";
        String body = "";
        int offerId=0;
        Bundle notificationBundle = new Bundle();
        Intent intent = new Intent(this, SplashScreenActivity.class);
        if (remoteMessage.getData().size() > 0) {
            JSONObject notificationJsonObject = new JSONObject(remoteMessage.getData());
            try {
                notificationBundle.putString(Constants.NOTIFICATION_DATA, notificationJsonObject.toString());
                image = notificationJsonObject.getString(Constants.IMAGE_KEY);
                header = notificationJsonObject.getString(Constants.HEADER_KEY);
                body = notificationJsonObject.getString(Constants.DESCRIPTION_KEY);
                offerId = notificationJsonObject.getInt(Constants.OFFER_ID_KEY);
            } catch (Exception e) {
                e.printStackTrace();
            }


            notificationBundle.putString(Constants.PUSH_NOTIFICATION_FROM_KEY, Constants.PUSH_NOTIFICATION_FROM_VALUE);
            notificationBundle.putInt(Constants.OFFER_ID_KEY,offerId);


            final int notificationId = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
            intent.putExtras(notificationBundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);


            PendingIntent pendingIntent = PendingIntent.getActivity(this, notificationId /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);


            final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_HIGH);

                // Configure the notification channel.
                notificationChannel.setDescription(body);
                notificationChannel.enableLights(true);
                notificationChannel.setVibrationPattern(new long[]{0});
                notificationChannel.enableVibration(false);
                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(notificationChannel);
                }
            }


            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                notificationBuilder.setSmallIcon(R.mipmap.ic_iofferbh_logo_white);
                notificationBuilder.setColor(getResources().getColor(R.color.colorAccent));
            } else {
                notificationBuilder.setSmallIcon(R.mipmap.ic_iofferbh_logo);
            }
            notificationBuilder.setAutoCancel(true)
                    .setWhen(System.currentTimeMillis())
                    .setTicker(getString(R.string.app_name))
                    .setAutoCancel(true)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(body)
                    .setVibrate(new long[]{0})
                    .setContentIntent(pendingIntent);
            notificationBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            assert notificationManager != null;
            if(image != null && !image.isEmpty() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){

                FutureTarget<Bitmap> futureTarget =
                        GlideApp.with(this).asBitmap()
                                .load(com.accentrs.apilibrary.utils.Constants.BASE_URL.concat(image))
                                .centerInside()
                                .submit(700, 850);

                try {
                    Bitmap newBitmap = futureTarget.get();
                    notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(newBitmap));
                    notificationManager.notify(/*notification id*/notificationId, notificationBuilder.build());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

//                Log.d(TAG, "createDefaultNotification: in if");
//                SimpleTarget<Bitmap> target = new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                        notificationBuilder.setLargeIcon(resource);
//                        notificationManager.notify(/*notification id*/notificationId, notificationBuilder.build());
//
//                        Log.d(TAG, "createDefaultNotification: in callback");
//                    }
//                };
//
//                Glide.with(this)
//                        .asBitmap()
//                        .load(com.accentrs.apilibrary.utils.Constants.BASE_URL.concat(image))
//                        .into(target);

            }else{
                notificationManager.notify(/*notification id*/notificationId, notificationBuilder.build());
//                Log.d(TAG, "createDefaultNotification: in callback");
            }

//            NotificationCompat.Builder mBuilder =
//                    new NotificationCompat.Builder(this)
//                            .setSmallIcon(R.mipmap.ic_launcher)
//                            .setContentTitle(getString(R.string.app_name))
//                            .setContentText(body)
//                            .setTicker(getString(R.string.app_name))
//                            .setAutoCancel(true)
////                            .setStyle(new NotificationCompat.BigPictureStyle()
////                                    .bigPicture(bitmap).setSummaryText(getString(R.string.app_name)))
//                            .setContentIntent(pendingIntent);


//            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            assert notificationManager != null;
//            notificationManager.notify(notificationId, mBuilder.build());
        }
    }


    private boolean checkPushNotificationStatus() {

        boolean notificationStatus = new SharedPreferencesData(getApplicationContext()).getShowNotificationStatus();

        if (notificationStatus) {
            if(checkNotificationCurrentDate()){
                return true;
            }else{
                return false;
            }

        } else {
            return false;
        }


    }


    private boolean checkNotificationCurrentDate() {
        String notificationCurrentDate = new SharedPreferencesData(getApplicationContext()).getPushNotificationCurrentDate();
        if (TextUtils.isEmpty(notificationCurrentDate)) {
            startNewPushNotificationSessionCounting();
            return true;
        } else if (getCurrentDate().equals(notificationCurrentDate)) {
            int liveCount = new SharedPreferencesData(getApplicationContext()).getUserNotificationLiveCount();
            boolean pushNotificationCountSelectedStatus =  new SharedPreferencesData(this).getNotificationCountSelectedStatus();

            if(pushNotificationCountSelectedStatus){
                if (checkPushNotificationCountStatus(liveCount)) {
                    new SharedPreferencesData(getApplicationContext()).setUserNotificationLiveCount(liveCount + 1);
                    return true;
                }

                return false;
            }else{
                new SharedPreferencesData(getApplicationContext()).setUserNotificationLiveCount(liveCount + 1);
                return true;
            }


        } else {
            destroyPreviousUserNotificationSesstionCounting();
            startNewPushNotificationSessionCounting();
            return true;
        }


    }

    private void startNewPushNotificationSessionCounting() {
        new SharedPreferencesData(getApplicationContext()).setPushNotificationCurrentDate(getCurrentDate());
        int liveCount = new SharedPreferencesData(getApplicationContext()).getUserNotificationLiveCount();
        new SharedPreferencesData(getApplicationContext()).setUserNotificationLiveCount(liveCount + 1);
    }

    private boolean checkPushNotificationCountStatus(int liveNotificationCount) {

        int totalPushNotificationCountLimit = new SharedPreferencesData(getApplicationContext()).getNotificationCountPerDay();

        if (liveNotificationCount <= totalPushNotificationCountLimit) {
            return true;
        } else {
            return false;
        }

    }

    private void destroyPreviousUserNotificationSesstionCounting() {
        new SharedPreferencesData(getApplicationContext()).setPushNotificationCurrentDate("");
        new SharedPreferencesData(getApplicationContext()).setUserNotificationLiveCount(0);

    }


    private String getCurrentDate() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        System.out.println(formatter.format(date));
        Log.d("current date ", formatter.format(date) + "");
        Log.d("shared current date ",  new SharedPreferencesData(getApplicationContext()).getPushNotificationCurrentDate() + "");
        return formatter.format(date);
    }


}
