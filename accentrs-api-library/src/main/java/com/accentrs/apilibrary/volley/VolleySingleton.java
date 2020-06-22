package com.accentrs.apilibrary.volley;

import android.content.Context;
import android.util.Log;

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
    private static final String VALENTINE_CACHE = "com.accentrs";
    private static final int CONNECTION_TIMEOUT = 25000;

    private static VolleySingleton mInstance;

    private static Context mContext;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private DefaultRetryPolicy retryPolicy;

    private VolleySingleton(Context context) {
        mContext = context.getApplicationContext();
        final BasicNetwork network = new BasicNetwork(new HurlStack());

        File cache = mContext.getExternalCacheDir();
        if (cache == null) {
            cache = mContext.getExternalCacheDir();
        }

        final File rootCache = new File(mContext.getExternalCacheDir(), VALENTINE_CACHE);
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
