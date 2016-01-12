package com.appstud.testappstud.network;


import android.content.Context;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import com.appstud.testappstud.Constant;

/**
 *
 */
public class NetworkCore {


    public static final long CONNECT_MS_TIMEOUT = 5000;
    public static final long READ_MS_TIMEOUT = 5000;
    public static final long WRITE_MS_TIMEOUT = 5000;

    public static final String CACHE_DIRECTORY = "network_cache";
    public static final long CACHE_MAX_BYTE_SIZE = 1024 * 1024 * 10; // 10 MB

    private RestAdapter mRestAdapter;
    private Context mContext;

    /**
     * Init Network Core
     * @param context Context
     */
    private void init(Context context) {
        if (mRestAdapter == null) {
            mContext = context;

            // Setup timeout and cache for the HttpClient used for all network requests
            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setConnectTimeout(CONNECT_MS_TIMEOUT, TimeUnit.MILLISECONDS);
            okHttpClient.setReadTimeout(READ_MS_TIMEOUT, TimeUnit.MILLISECONDS);
            okHttpClient.setWriteTimeout(WRITE_MS_TIMEOUT, TimeUnit.MILLISECONDS);

            File httpCacheDirectory = new File(context.getCacheDir(), CACHE_DIRECTORY);
            Cache cache = new Cache(httpCacheDirectory, CACHE_MAX_BYTE_SIZE);
            okHttpClient.setCache(cache);

            // Create and configure our Rest Adapter
            mRestAdapter = new RestAdapter.Builder()
                    .setClient(new OkClient(okHttpClient))
                    .setEndpoint(Constant.URL_ADDRESS)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();
        }
    }

    public RestAdapter getRestAdapter() {
        return mRestAdapter;
    }

    // region Error handler

    // endregion

    // region Singleton

    private NetworkCore(Context context) {
        init(context);
    }

    private static NetworkCore instance = null;

    public static NetworkCore getInstance(Context context) {
        if (instance == null) {
            instance = new NetworkCore(context);
        }
        return instance;
    }

    // #endregion

}
