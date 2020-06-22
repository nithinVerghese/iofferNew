package com.accentrs.iofferbh.activity;

import android.os.SystemClock;
import android.util.Log;

public class multiClickDissable {

    private static multiClickDissable single_instance=null;

    // variable of type String
    private static long mLastClickTime = 0;
    public Boolean disab(){

        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return false;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        return true;
    }

    // private constructor restricted to this class itself
//       private multiClickDissable()
//    {
//
//    }

    // static method to create instance of Singleton class
    public static multiClickDissable Singleton()
    {
        // To ensure only one instance is created
        if (single_instance == null)
        {
            single_instance = new multiClickDissable();
        }
        return single_instance;
    }
}
