package com.accentrs.iofferbh.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.accentrs.iofferbh.R;

public class AboutUsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        initializeToolbar();
    }

    private void initializeToolbar() {
        setIofferBhLogo();
        setSearchIconVisibility(false);
        setLeftIconDrawableVisibility(true);
        setLeftIconDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back));

    }


}
