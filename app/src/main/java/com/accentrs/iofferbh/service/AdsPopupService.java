package com.accentrs.iofferbh.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.Nullable;

import com.accentrs.apilibrary.callback.Results;
import com.accentrs.apilibrary.interfaces.ResponseType;
import com.accentrs.iofferbh.model.spinninggame.SpinningWheelPlayResponse;
import com.accentrs.iofferbh.utils.Constants;
import com.accentrs.iofferbh.utils.SharedPreferencesData;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;


public class AdsPopupService extends IntentService {


    public AdsPopupService() {
        super("AdsPopupService");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public AdsPopupService(String name) {
        super(name);
    }

    public static void startAdsPopupService(Context context, int adsPopupId) {
        Intent intent = new Intent(context, AdsPopupService.class);
        intent.putExtra(Constants.ADS_POPUP_ID_KEY, adsPopupId);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (intent != null) {
            final int adsPopupId = intent.getIntExtra(Constants.ADS_POPUP_ID_KEY, 0);
            if (adsPopupId > 0) {
                hitAdsPopupViewApi(createAdsPopupJsonObject(adsPopupId));

            }
        }
    }

    private JSONObject createAdsPopupJsonObject(int adsPopupId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants.ADS_POPUP_ID_KEY, adsPopupId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void hitAdsPopupViewApi(JSONObject adsPopupJsonObject) {
        Results mResults = new Results(this);
        mResults.viewAdsPopup(adsPopupJsonObject);
        mResults.setOnResultsCallBack(new Results.ResultsCallBack() {
            @Override
            public void onSuccess(ResponseType response) {

                if (response.getStringResponse().toString() != null) {

                    SpinningWheelPlayResponse wheelPlayResponse = new Gson().fromJson(response.getStringResponse().toString(), SpinningWheelPlayResponse.class);
                    if (wheelPlayResponse != null) {
                        if (wheelPlayResponse.getMessage() != null) {
                            new SharedPreferencesData(AdsPopupService.this).setAdsPopupStatus(false);
                        }
                    }
                }

            }

            @Override
            public void onError(String error) {
            }
        });
    }


}
