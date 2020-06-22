package com.accentrs.iofferbh.volley;

import android.content.Context;
import android.util.Log;


import com.accentrs.iofferbh.utils.Constants;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;

import java.io.File;


public final class VolleySingleton {

    private static final int POOL_SIZE = 3;
    private static final int CACHE_SIZE = 25 * 1024 * 1024;
    private static final String TAG = VolleySingleton.class.getSimpleName();
    private static final String APPLICATION_CACHE = Constants.APPLICATION_CACHCE_PACKAGE_NAME;
    private static final int CONNECTION_TIMEOUT = 25000;

    private static VolleySingleton mInstance;

//    private  Context mContext;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private DefaultRetryPolicy retryPolicy;

    private VolleySingleton(Context context) {
//        mContext = context.getApplicationContext();
        final BasicNetwork network = new BasicNetwork(new HurlStack());

        File cache = context.getExternalCacheDir();
        if (cache == null) {
            cache = context.getExternalCacheDir();
        }

        final File rootCache = new File(context.getExternalCacheDir(), APPLICATION_CACHE);
        final DiskBasedCache diskCache = new DiskBasedCache(rootCache, CACHE_SIZE);
        mRequestQueue = new RequestQueue(diskCache, network, POOL_SIZE);
        mRequestQueue.start();

        mImageLoader = new ImageLoader(mRequestQueue, new BitmapLruCache(context));

        retryPolicy = new DefaultRetryPolicy(CONNECTION_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    }

    public static VolleySingleton getInstance(Context context) {

        synchronized (VolleySingleton.class) {
            if (mInstance == null) {
                mInstance = new VolleySingleton(context);
            }
        }
        return mInstance;
    }

    public void addRequestToQueue(Request request, String tag) {
        Log.d(TAG, " Requested URL : " + request.getUrl());
        request.setTag(tag);
        request.setRetryPolicy(retryPolicy);
        mRequestQueue.add(request);
    }

    public void addCacheRequestToQueue(Request request, Object tag) {
        request.setTag(tag);
        request.setRetryPolicy(retryPolicy);
        request.shouldCache();
        mRequestQueue.add(request);
        Log.d(TAG, " Requested URL : " + request.getUrl());
    }

    public void addRequestToQueue(Request request) {
        addRequestToQueue(request, TAG);
    }

    public void cancelAll(String tag) {
        mRequestQueue.cancelAll(TAG);
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
