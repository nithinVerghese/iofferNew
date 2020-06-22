package com.accentrs.iofferbh.activity;

import android.app.Application;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.application.IOfferBhApplication;
import com.accentrs.iofferbh.utils.Constants;
import com.accentrs.iofferbh.utils.SharedPreferencesData;

public class ChangeAppLanguage extends BaseActivity {


    //    private RadioGroup rgDownloadSortBy;
    private RadioButton rbHindi;
    private RadioButton rbEnglish;
    private TextView btnSave;
    private RelativeLayout rlParentView;

    private RadioGroup rgNotification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_app_language);
        initializeToolbar();
        initializeViews();
        setListeners();
        setSelectedLanguage();
    }


    private void initializeToolbar() {
        setIofferBhLogo();
//        setTitleTextView("Change Language");
        setSearchIconVisibility(false);
        setLeftIconDrawableVisibility(true);
        setLeftIconDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back));

    }



    private void setSelectedLanguage() {
        String lang = new SharedPreferencesData(this).getLanguage();
        if (!lang.isEmpty()) {
            if (lang.equals(Constants.ARABIC_LOCALE_LANGUAGE)) {
                rbHindi.setChecked(true);
            } else {
                rbEnglish.setChecked(true);
            }
        } else {
            rbEnglish.setChecked(true);
        }
    }


    private void setListeners() {
        btnSave.setOnClickListener(this);
    }

    private void initializeViews() {
        rbHindi = (RadioButton) findViewById(R.id.rb_hindi);
        rbEnglish = (RadioButton) findViewById(R.id.rb_english);
        btnSave = (TextView) findViewById(R.id.btn_save);
        rlParentView = (RelativeLayout) findViewById(R.id.rl_parent_view);
//        rbHindi.setVisibility(View.GONE);

    }



    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_save:
                changeLanguage();
                break;

        }
    }




    private void changeLanguage() {
        if (rbHindi.isChecked()) {
            new SharedPreferencesData(this).setLanguage(Constants.ARABIC_LOCALE_LANGUAGE);
        } else {
            new SharedPreferencesData(this).setLanguage(Constants.ENGLISH_LOCALE_LANGUAGE);
        }
        ((IOfferBhApplication) getApplication()).updateBaseContextLocale(this);
        refreshApp();
    }

    private void refreshApp() {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }



}
