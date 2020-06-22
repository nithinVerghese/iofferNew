package com.accentrs.iofferbh.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.helper.GlideApp;
import com.github.chrisbanes.photoview.PhotoView;

public class coupon_view extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_view);

        PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);

        ImageView im_bc = findViewById(R.id.goback1);

        Bundle extras = getIntent().getExtras();
        String value1 = extras.getString("img_url");

        GlideApp.with(this)
                .load(value1).centerCrop()

                .fitCenter()
                .into(photoView);

        im_bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }
}
