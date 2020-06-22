package com.accentrs.iofferbh.activity;

import androidx.appcompat.app.AppCompatActivity;

public class app {

    public static void finish_activity(BaseActivity BaseActivity){
        BaseActivity.finish();
    }
    public static void refresh_activity(AppCompatActivity appCompatActivity){
        appCompatActivity.recreate();
    }

}
