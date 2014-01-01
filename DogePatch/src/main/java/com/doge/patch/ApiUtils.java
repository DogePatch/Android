package com.doge.patch;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;

/**
 * Helper class to aid api calls to DogePatch
 */
public class ApiUtils implements Response.ErrorListener {
    private static final String TAG = ApiUtils.class.getSimpleName();
    private static final boolean DBG = true;
    private static final String API_SCHEME = "http";
    private static final String API_AUTHORITY = "dogepatch.apiary.io";
    private static final String API_BASE_PATH = "api/v1";
    private static final String DISHES_PATH = "dishes";

    private RequestQueue mRequestQueue;

    public ApiUtils(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public void getDishes(Response.Listener listener) {
        getDishes(new HashMap<String, String>(), listener);
    }

    public void getDishes(HashMap<String, String> params, Response.Listener listener) {
        String url = buildUrl(DISHES_PATH, params);
        GsonRequest gsonRequest = new GsonRequest(url, Request.Method.GET, DishList.class, this, listener);
        mRequestQueue.add(gsonRequest);
    }

    private String buildUrl(String path, HashMap<String, String> params) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(API_SCHEME).authority(API_AUTHORITY).appendEncodedPath(API_BASE_PATH).appendEncodedPath(path);
        for (String key : params.keySet()) {
            builder.appendQueryParameter(key, params.get(key));
        }
        String url = builder.build().toString();
        if (DBG) {
            Log.d(TAG, "Build api uri: " + url);
        }
        return url;
    }

    @Override
    public void onErrorResponse(VolleyError e) {
        Log.e(TAG, "API ERROR", e);
    }
}
