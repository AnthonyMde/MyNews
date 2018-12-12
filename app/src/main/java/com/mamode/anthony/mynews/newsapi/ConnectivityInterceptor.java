package com.mamode.anthony.mynews.newsapi;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mamode.anthony.mynews.utils.NetworkUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ConnectivityInterceptor implements Interceptor {
    private Context mContext;

    /**
     * Default constructor.
     */
    ConnectivityInterceptor (Context context) {
        mContext = context;
    }

    /**
     * Interrupts the call and throws a custom exception (NoConnectivityException)
     * when no network is available.
     */
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        if (!(NetworkUtil.isOnline(mContext))) {
            throw new NoConnectivityException();
        }
        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }
}
