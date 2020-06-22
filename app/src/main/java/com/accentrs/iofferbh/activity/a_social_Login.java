package com.accentrs.iofferbh.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.utils.SharedPreferencesData;

public class a_social_Login extends AppCompatActivity {
    ///Login activity


    private Button insta_loin, fb_login, google_login, guest_login;
    ImageView goback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_social__login);




            if((new SharedPreferencesData(a_social_Login.this).getLogin_stat())==true){

                Intent i = new Intent(this,signout.class);
                startActivity(i);
                finish();
            }


//        if((new SharedPreferencesData(a_social_Login.this).getcart())==true){
//
//            Intent i = new Intent(this,paymentshare.class);
//            startActivity(i);
//            finish();
//        }

        insta_loin = findViewById(R.id.insta_loin);
        fb_login = findViewById(R.id.fb_login);
        google_login = findViewById(R.id.google_login);
                guest_login = findViewById(R.id.guest_login) ;

                goback = findViewById(R.id.goback1);




        insta_loin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(a_social_Login.this,log__in.class);
                startActivity(i);
            }
        });

        fb_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(a_social_Login.this,login.class);
                startActivity(i);
            }
        });

        google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =new Intent(a_social_Login.this,g_login.class);
                startActivity(i);
                finish();
            }
        });

        guest_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =new Intent(a_social_Login.this,a_deviceId.class);
                startActivity(i);
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
