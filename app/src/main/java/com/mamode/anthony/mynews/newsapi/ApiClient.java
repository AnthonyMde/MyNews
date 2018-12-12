package com.mamode.anthony.mynews.newsapi;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mamode.anthony.mynews.newsmodels.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The retrofit client responsible for making the NewYorkTimes API call.
 */
public class ApiClient {
    /**
     * Retrofit is based on okHttp client.
     * We had here our custom interceptor : loggingInterceptor for debugging,
     * quthInterceptor to add the API key and the ConnectivityInterceptor which
     * will send the request only if a connection is available.
     * @param context the context is needed by the ConnectivityInterceptor to
     *                check the internet connection.
     * @return our okHttpClient with the added interceptor.
     */
    private static OkHttpClient okHttpClient(Context context) {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .addInterceptor(new AuthInterceptor())
                .addInterceptor(new ConnectivityInterceptor(context))
                .build();
    }

    /**
     * Converter needed for the search API which is wrapped in an
     * undesirable object.
     */
    private static Gson gsonConverter =
            new GsonBuilder()
                    .registerTypeAdapterFactory(new DocsTypeAdapterFactory())
                    .create();

    /**
     * Retrofit instance to make the API call.
     * @param context is needed for our ConnectivityInterceptor.
     * @return retrofit instance with the base URL, the gson converter
     * and the okHttpClient.
     */
    public static Retrofit getRetrofitInstance(Context context) {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gsonConverter))
                .client(okHttpClient(context))
                .build();
    }
}