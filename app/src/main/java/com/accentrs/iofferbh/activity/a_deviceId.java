package com.accentrs.iofferbh.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.utils.SharedPreferencesData;

public class a_deviceId extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_device_id);


        String ID = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);

//        new SharedPreferencesData(this).setLogin_stat(true);
//        new SharedPreferencesData(this).setUserID(ID);
//
//        Intent i = new Intent(a_deviceId.this,CouponCategoryActivity.class);
//        startActivity(i);
//
//        finish();


        new SharedPreferencesData(a_deviceId.this).setLogin_stat(true);

        new SharedPreferencesData(a_deviceId.this).setUserID(ID);



        if(new SharedPreferencesData(a_deviceId.this).getcart()){

            AlertDialog.Builder a_builder = new AlertDialog.Builder(a_deviceId.this);
            a_builder.setTitle("Payment Option").setCancelable(false).setMessage(new SharedPreferencesData(a_deviceId.this).getMSG())
                    .setPositiveButton("Debit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent in = new Intent(a_deviceId.this,paymentshare.class);
                            startActivity(in);
                            finish();
                        }
                    }).setNegativeButton("Credit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent in = new Intent(a_deviceId.this,paymentshare_c.class);
                    startActivity(in);
                    finish();
                }
            });

            AlertDialog alert = a_builder.create();
            alert.show();
//            Intent in = new Intent(this,paymentshare.class);
//            startActivity(in);
//            finish();
        }else{

            Intent i = new Intent(this,HomeScreenActivity.class);
            startActivity(i);
            finish();}
    }
}
