package com.accentrs.iofferbh.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.accentrs.apilibrary.callback.Results;
import com.accentrs.apilibrary.interfaces.ResponseType;
import com.accentrs.apilibrary.utils.Constants;
import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.application.IOfferBhApplication;
import com.accentrs.iofferbh.helper.GlideApp;
import com.accentrs.iofferbh.model.spinninggame.SpinningWheelPlayResponse;
import com.accentrs.iofferbh.model.spinninggame.WheelItemsItem;
import com.accentrs.iofferbh.utils.SharedPreferencesData;
import com.accentrs.iofferbh.utils.Utils;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class GiftActivity extends HeaderActivity {

    private AppCompatImageView ivPrice;
    private AppCompatImageView ivGameGiftClose;
    private AppCompatEditText  etUserFullName;
    private AppCompatEditText  etUserMobileNumber;
    private TextView           tvSponsoredCompanyName;
    private AppCompatButton    btnSubmit;
    private WheelItemsItem     wheelItemsItem;
    private int                wheelId;
    private String             sponsoredCompanyName="";
    private String             FROM_SUBMIT="FROM_SUBMIT";
    private String             FROM_SUBMIT_BTN="FROM_SUBMIT_BTN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_voucher);
        fetchIntentData();
        initializeViews();
    }

    private void fetchIntentData(){
        if(getIntent() != null && getIntent().getExtras() != null){
            wheelItemsItem = (WheelItemsItem) getIntent().getExtras().getSerializable(Constants.SPINNING_WHEEL_ITEM_DATA_KEY);
            wheelId        =  getIntent().getExtras().getInt(Constants.WHEEL_ID_DATA_KEY);
            sponsoredCompanyName = wheelItemsItem.getCompanyName();
        }
    }

    private void initializeViews(){
        ivPrice                = findViewById(R.id.iv_gift);
        etUserFullName         = findViewById(R.id.et_user_full_name);
        etUserMobileNumber     = findViewById(R.id.et_user_mobile_no);
        ivGameGiftClose    = findViewById(R.id.iv_game_gift_close);
        tvSponsoredCompanyName = findViewById(R.id.tv_sponsered_company_name);
        tvSponsoredCompanyName.setText(sponsoredCompanyName);
        btnSubmit = findViewById(R.id.btn_submit);
        ivGameGiftClose.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        setUserDetail();
    }

    private void setUserDetail(){
        String fullName = new SharedPreferencesData(this).getUserName();
        String mobileNo = new SharedPreferencesData(this).getUserPhoneNumber();
        etUserFullName.setText(fullName);
        etUserMobileNumber.setText(mobileNo);
        etUserFullName.clearFocus();
        etUserMobileNumber.clearFocus();
        setSpinningWheelPrizeData();
        hitSpinWheelPlayApi(FROM_SUBMIT);
    }

    private void setSpinningWheelPrizeData(){
        if(wheelItemsItem != null && wheelItemsItem.getPrizeImage() != null){
            SimpleTarget target = new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    ivPrice.setImageBitmap(resource);
                }
            };
            GlideApp.with(this)
                    .asBitmap()
                    .load(Constants.BASE_URL + wheelItemsItem.getPrizeImage())
                    .into(target);
        }
    }

    private void hitSpinWheelPlayApi(final String from){
        showProgressDialog(getString(R.string.msg_loading));
        Results mResults = new Results(this);
        mResults.playSpinningWheel(createPlaySpinningWheelJsonObject());
        mResults.setOnResultsCallBack(new Results.ResultsCallBack() {
            @Override
            public void onSuccess(ResponseType response) {
                dismissProgressDialog();
                Log.d("data response",response.getStringResponse().toString());

                if(response.getStringResponse().toString() != null){

                    SpinningWheelPlayResponse wheelPlayResponse = new Gson().fromJson(response.getStringResponse().toString(),SpinningWheelPlayResponse.class);
                    if(wheelPlayResponse != null){
                        if(wheelPlayResponse.getMessage() != null){
                            if(from.equalsIgnoreCase(FROM_SUBMIT_BTN)){
                                showToast("Thankyou");
                                finish();
                            } else {
                                showToast(wheelPlayResponse.getMessage());
                            }
                        }
                    }
                }

            }

            @Override
            public void onError(String error) {
                dismissProgressDialog();
                showToast(error);
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()){
            case R.id.btn_submit:
                hideKeyboard(v);
                if(isUserDetailValid()){
                    if(Utils.isConnectedToInternet(this)){
                        new SharedPreferencesData(IOfferBhApplication.getIntsance()).setUserName(etUserFullName.getText().toString());
                        new SharedPreferencesData(IOfferBhApplication.getIntsance()).setUserPhoneNumber(etUserMobileNumber.getText().toString());
                        hitSpinWheelPlayApi(FROM_SUBMIT_BTN);
                    }else{
                        showToast(getString(R.string.error_no_internet_msg));
                    }
                }

                break;

            case R.id.iv_game_gift_close:
                finish();
                break;

        }

    }

    private JSONObject createPlaySpinningWheelJsonObject(){
        String fullname = new SharedPreferencesData(this).getUserName();
        String mobileNo = new SharedPreferencesData(this).getUserPhoneNumber();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants.WHEEL_ID_KEY,wheelId);
            jsonObject.put(Constants.WHEEL_ITEM_ID_KEY,wheelItemsItem.getId());
            jsonObject.put(Constants.USER_NAME_KEY,fullname);
            jsonObject.put(Constants.CONTACT_NO_KEY,mobileNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("game json object",jsonObject.toString());
       return jsonObject;
    }


    private boolean isUserDetailValid(){
        if(TextUtils.isEmpty(etUserFullName.getText().toString())){
            showToast("Please enter full name");
            return false;
        }else if(TextUtils.isEmpty(etUserMobileNumber.getText().toString())){
            showToast("Please enter mobile number");
            return false;
        }

        return true;

    }
}
