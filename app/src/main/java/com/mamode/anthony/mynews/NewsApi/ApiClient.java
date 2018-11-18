package com.mamode.anthony.mynews.NewsApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mamode.anthony.mynews.model.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .addInterceptor(new AuthInterceptor())
                .build();
    }

    private static Gson gsonConverter =
            new GsonBuilder()
                    .registerTypeAdapterFactory(new DocsTypeAdapterFactory())
                    .create();

    public static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gsonConverter))
                .client(okHttpClient())
                .build();
    }
}