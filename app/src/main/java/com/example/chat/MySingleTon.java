package com.example.chat;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by ahmed on 10/10/17.
 */

public class MySingleTon {
    public static final String TAG ="mutee";
    private static MySingleTon mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private MySingleTon(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();

    }

    public static synchronized MySingleTon getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MySingleTon(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
