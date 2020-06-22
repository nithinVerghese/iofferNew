package com.accentrs.apilibrary.callback;

import android.content.Context;

import com.android.volley.Request;
import com.accentrs.apilibrary.interfaces.IFragmentBasicBehaviorProvider;
import com.accentrs.apilibrary.utils.Utils;
import com.accentrs.apilibrary.volley.VolleySingleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;


public class VolleyClass implements IFragmentBasicBehaviorProvider {

    private Context mContext;
    private VolleySingleton mVolleySingleton;
    private Map<IFragmentBasicBehaviorProvider, List<Request>> mRequestMap;

    public VolleyClass(Context mContext){
        this.mContext=mContext;
    }


    private void addVolleyRequest(Request request, String tag) {
        mVolleySingleton.addRequestToQueue(request, tag);
    }

    @Override
    public boolean addVolleyRequest(Request request, String tag, boolean requestAgain, IFragmentBasicBehaviorProvider IFragmentsBasicBehaviourProvider) {
        if (mVolleySingleton == null)
            mVolleySingleton = VolleySingleton.getInstance(mContext);

//        VolleyErrorWrapper errorWrapper = new VolleyErrorWrapper(mContext, request.getErrorListener());
//        request.setErrorListener(errorWrapper);

        if (Utils.isConnectedToInternet(mContext)) {
            addVolleyRequest(request, tag);
            return true;
        } else {
            request.setTag(tag);
            if (requestAgain) {
                if (mRequestMap == null) {
                    mRequestMap = new WeakHashMap<>();
                    mRequestMap = Collections.synchronizedMap(mRequestMap);
                }

                if (!mRequestMap.containsKey(IFragmentsBasicBehaviourProvider) || mRequestMap.get(IFragmentsBasicBehaviourProvider) == null) {
                    mRequestMap.put(IFragmentsBasicBehaviourProvider, new ArrayList<Request>());
                }

                mRequestMap.get(IFragmentsBasicBehaviourProvider).add(request);
            }

            return false;
        }
    }

    @Override
    public void cancelAll(String tag) {
        if (mVolleySingleton != null)
            mVolleySingleton.cancelAll(tag);
    }

}
