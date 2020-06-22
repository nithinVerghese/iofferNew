package com.accentrs.iofferbh.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.accentrs.iofferbh.R;

public class ContactUsActivity extends BaseActivity {


    private TextView tvOfferbhEmail;
    private TextView tvOfferbhTermsAndCondition;
    private TextView tvOfferbhPrivacyPolicy;
    private LinearLayout llWhatsapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        initializeToolbar();
        initializeViews();
        setListener();
    }

    private void initializeToolbar() {
        setIofferBhLogo();
//        setTitleTextView("Contact us");
        setSearchIconVisibility(false);
        setLeftIconDrawableVisibility(true);
        setLeftIconDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back));

    }

    private void initializeViews() {
        llWhatsapp = findViewById(R.id.ll_iofferh_whatsapp);
        tvOfferbhEmail = findViewById(R.id.tv_offerbh_email);
        tvOfferbhTermsAndCondition = findViewById(R.id.tv_offerbh_terms_and_condition);
        tvOfferbhPrivacyPolicy = findViewById(R.id.tv_iofferbh_privacy_policy);
        tvOfferbhTermsAndCondition.setPaintFlags(tvOfferbhTermsAndCondition.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvOfferbhPrivacyPolicy.setPaintFlags(tvOfferbhTermsAndCondition.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    private void setListener() {
        llWhatsapp.setOnClickListener(this);
        tvOfferbhEmail.setOnClickListener(this);
        tvOfferbhPrivacyPolicy.setOnClickListener(this);
        tvOfferbhTermsAndCondition.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {

            case R.id.tv_offerbh_email:
                    openEmail();
                break;


            case R.id.tv_offerbh_terms_and_condition:
                setCompanyTermsAndConditionData("www.infolinebh.com/?page_id=2");
                break;


            case R.id.ll_iofferh_whatsapp:
                    setCompanyCallActionData("+97336332273");
                    break;

            case R.id.tv_iofferbh_privacy_policy:
                startPrivacyPolicyActivity();
                break;


        }


    }

    private void startPrivacyPolicyActivity() {
        startActivity(new Intent(this, PrivacyPolicyActivity.class));
    }

    private void openEmail(){

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:iofferbh@gmail.com"));

        try{
            startActivity(emailIntent);
        }catch (ActivityNotFoundException ex){

        }

    }


    public void setCompanyCallActionData(String contactNumber) {
        Uri call = Uri.parse("tel:" + contactNumber);
        Intent dialIntent = new Intent(Intent.ACTION_DIAL, call);
        startActivity(dialIntent);
    }

    public void setCompanyTermsAndConditionData(String termsAndConditionData) {

        if (!termsAndConditionData.startsWith("https://") && !termsAndConditionData.startsWith("http://")) {
            termsAndConditionData = "http://" + termsAndConditionData;
        }

        Intent companyUrlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(termsAndConditionData));
        startActivity(companyUrlIntent);
    }





}
