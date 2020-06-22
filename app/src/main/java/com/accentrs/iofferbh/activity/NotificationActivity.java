package com.accentrs.iofferbh.activity;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.utils.SharedPreferencesData;

public class NotificationActivity extends BaseActivity {

    private Switch svPushNotification;
    private LinearLayout llNotificationSetting;
    private RadioGroup rgNotification;
    private LinearLayout llCustomNotification;
    private EditText edNotificationCount;

    private RadioButton rbtenNotification;
    private RadioButton rbTwentyNotification;
    private RadioButton rbFiftyNotification;
    private RadioButton rbOtherNotification;


    private LinearLayout llSubmitUserCountNotification;


    private LinearLayout llNotificationSubmitV1;

    private int notificationCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        initializeToolbar();
        initializeViews();
        setListener();
        setPushNotificationData();
    }

    private void setPushNotificationData() {

        boolean notificationstatus = new SharedPreferencesData(this).getShowNotificationStatus();
        svPushNotification.setChecked(notificationstatus);
        if (notificationstatus) {

            showNotificationSettingView();
            setUserNotificationCount();
        } else {
            hideNotificationSettingView();
        }


    }

    private void setUserNotificationCount() {
        int count = new SharedPreferencesData(this).getNotificationCountPerDay();
        boolean isOtherSelected = new SharedPreferencesData(this).getNotificationOtherSelectedStatus();

        if (isOtherSelected) {
            rbOtherNotification.setChecked(true);
            edNotificationCount.setText(String.valueOf(count));
            edNotificationCount.setSelection(String.valueOf(count).length());
        } else {
            if (count == 10) {
                rbtenNotification.setChecked(true);
                showNotificationV1();
            } else if (count == 20) {
                rbTwentyNotification.setChecked(true);
                showNotificationV1();
            } else if (count == 50) {
                rbFiftyNotification.setChecked(true);
                showNotificationV1();
            } else {
                if(count!=0){
                    rbOtherNotification.setChecked(true);
                    edNotificationCount.setText(String.valueOf(count));
                }else{
                    rbOtherNotification.setChecked(true);
                }

            }
        }


    }


    private void initializeToolbar() {
        setIofferBhLogo();
//        setTitleTextView("Notification");
        setSearchIconVisibility(false);
        setLeftIconDrawableVisibility(true);
        setLeftIconDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back));

    }

    private void initializeViews() {
        svPushNotification = findViewById(R.id.switch_push_notification);
        llNotificationSetting = findViewById(R.id.ll_notification_container);
        rgNotification = findViewById(R.id.rg_notification);
        llCustomNotification = findViewById(R.id.ll_enter_custom_notification);
        edNotificationCount = findViewById(R.id.et_notification_count);


        rbTwentyNotification = findViewById(R.id.rb_twenty_notification);
        rbtenNotification = findViewById(R.id.rb_ten_notification);
        rbFiftyNotification = findViewById(R.id.rb_fifty_notification);
        rbOtherNotification = findViewById(R.id.rb_other_notification);


        llSubmitUserCountNotification = findViewById(R.id.ll_notification_submit);
        llNotificationSubmitV1 = findViewById(R.id.ll_notification_submit_v1);

    }


    private void setListener() {

        rbtenNotification.setOnClickListener(this);
        rbTwentyNotification.setOnClickListener(this);
        rbFiftyNotification.setOnClickListener(this);
        rbOtherNotification.setOnClickListener(this);

        llSubmitUserCountNotification.setOnClickListener(this);
        llNotificationSubmitV1.setOnClickListener(this);


        edNotificationCount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    performSubmit();
                    return true;
                }
                return false;
            }
        });


        svPushNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked) {
                    showNotificationSettingView();
                } else {
                    hideNotificationSettingView();
                }

                new SharedPreferencesData(NotificationActivity.this).setShowNotificationStatus(isChecked);
            }
        });


        rgNotification.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_ten_notification:
                        hideCustomNotification();
                        notificationCount = 10;
//                        setNotificationCount(10);
                        new SharedPreferencesData(NotificationActivity.this).setNotificationOtherSelectedStatus(false);
                        showNotificationV1();
                        hideCustomNotification();
                        break;
                    case R.id.rb_twenty_notification:
                        notificationCount = 20;
                        hideCustomNotification();
//                        setNotificationCount(20);
                        new SharedPreferencesData(NotificationActivity.this).setNotificationOtherSelectedStatus(false);
                        hideCustomNotification();
                        showNotificationV1();

                        break;

                    case R.id.rb_fifty_notification:
                        notificationCount = 50;
                        hideCustomNotification();
//                        setNotificationCount(50);
                        new SharedPreferencesData(NotificationActivity.this).setNotificationOtherSelectedStatus(false);
                        hideCustomNotification();
                        showNotificationV1();
                        break;

                    case R.id.rb_other_notification:


                        showCustomNotification();
                        hideNotificationV1();

                        break;


                }

            }
        });


    }

    private void notificationSuccess() {

        showToast("Notification count set successfully");
        finish();

    }


    private void performSubmit() {
        if (isValid()) {
            int notificationCount = Integer.parseInt(edNotificationCount.getText().toString());
            if (notificationCount == 0) {
                showToast("Please enter notification count greater than 0");
            } else {
                new SharedPreferencesData(this).setNotificationOtherSelectedStatus(true);
                new SharedPreferencesData(this).setNotificationCountSelectedStatus(true);
                setNotificationCount(notificationCount);
                notificationSuccess();
            }

        } else {
            showToast("Please enter notification count ");
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ll_notification_submit:

                performSubmit();


                break;


            case R.id.ll_notification_submit_v1:

                setNotificationCount();
                new SharedPreferencesData(this).setNotificationCountSelectedStatus(true);
                notificationSuccess();

                break;

            case R.id.ll_custom_ab_left:
                finish();
                break;


        }
    }


    private boolean isValid() {

        if (TextUtils.isEmpty(edNotificationCount.getText().toString())) {
            return false;
        }

        return true;

    }




    private void showNotificationSettingView() {
        llNotificationSetting.setVisibility(View.VISIBLE);
    }


    private void hideNotificationSettingView() {
        llNotificationSetting.setVisibility(View.INVISIBLE);
    }


    private void showCustomNotification() {
        llCustomNotification.setVisibility(View.VISIBLE);
        edNotificationCount.requestFocus();
    }

    private void hideCustomNotification() {
        llCustomNotification.setVisibility(View.GONE);
    }

    private void setNotificationCount() {
        new SharedPreferencesData(this).setNotificationCountPerDay(notificationCount);
    }

    private void setNotificationCount(int count) {
        new SharedPreferencesData(this).setNotificationCountPerDay(count);
    }


    private void showNotificationV1() {
        llNotificationSubmitV1.setVisibility(View.VISIBLE);
    }

    private void hideNotificationV1() {
        llNotificationSubmitV1.setVisibility(View.GONE);
    }


}
