package com.mamode.anthony.mynews.NewsApi;

import android.support.annotation.NonNull;

import com.mamode.anthony.mynews.model.Constants;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    /**
     * Default constructor.
     */
    AuthInterceptor() {
    }

    /**
     * We set the API key here, during the request instead
     * of setting it statically in our calls.
     */
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url().newBuilder()
                .addQueryParameter("api-key", Constants.API_KEY)
                .build();
        request = request.newBuilder().url(url).build();
        return chain.proceed(request);
    }
}
