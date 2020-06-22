package com.accentrs.apilibrary.interfaces;


import com.android.volley.Request;

public interface IFragmentBasicBehaviorProvider {



    boolean addVolleyRequest(Request request, String tag, boolean requestAgain, IFragmentBasicBehaviorProvider IFragmentsBasicBehaviourProvider);

    void cancelAll(String tag);
}
