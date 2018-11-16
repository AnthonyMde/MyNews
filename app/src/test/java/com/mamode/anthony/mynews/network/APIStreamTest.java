package com.mamode.anthony.mynews.network;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mamode.anthony.mynews.NewsApi.ArticleCalls;
import com.mamode.anthony.mynews.NewsApi.DocsTypeAdapterFactory;
import com.mamode.anthony.mynews.NewsApi.FragmentNewsType;
import com.mamode.anthony.mynews.NewsApi.NewsService;
import com.mamode.anthony.mynews.NewsRepository.NewsArticles;
import com.mamode.anthony.mynews.fragments.SectionFragment;
import com.mamode.anthony.mynews.model.Constants;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mamode.anthony.mynews.model.Constants.API_KEY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class APIStreamTest{
    private int responseCall = 0;

    private void creatingTestCall(String url) {
        Request request =
                new Request
                        .Builder()
                        .url(url)
                        .build();
        OkHttpClient client =
                new OkHttpClient
                        .Builder()
                        .build();
        client
                .newCall(request)
                .enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) {
                responseCall = response.code();
            }
        });
    }
    @Test
    public void Fetch_TopStories_API() throws InterruptedException {
        String url = "https://api.nytimes.com/svc/topstories/v2/home.json?api-key=" + API_KEY;
        this.creatingTestCall(url);
        TimeUnit.SECONDS.sleep(5);
        assertEquals(200, responseCall);
    }

    @Test
    public void Fetch_MostPopular_API() throws InterruptedException {
        String url = "https://api.nytimes.com/svc/mostpopular/v2/mostviewed/all-sections/1.json?api-key=" + API_KEY;
        this.creatingTestCall(url);
        TimeUnit.SECONDS.sleep(5);
        assertEquals(200, responseCall);
    }
}
