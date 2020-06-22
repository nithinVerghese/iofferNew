package com.accentrs.iofferbh.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.utils.SharedPreferencesData;

public class signout extends AppCompatActivity {

    Button sign_ou;
    ImageView goback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signout);

//        if(new SharedPreferencesData(signout.this).getcart()==true){
//
//            finish();
//
//        }

        sign_ou = findViewById(R.id.sign_ou);

        goback = findViewById(R.id.goback);

        sign_ou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SharedPreferencesData(signout.this).setLogin_stat(false);
                new SharedPreferencesData(signout.this).putcart(false);

                Intent i = new Intent(signout.this,HomeScreenActivity.class);
                startActivity(i);
                finish();

            }
        });

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
