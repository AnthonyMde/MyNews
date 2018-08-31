package com.mamode.anthony.mynews.utils;

import android.support.annotation.Nullable;

import com.mamode.anthony.mynews.models.NewsArticles;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleCalls {

    // 1 - Creating a callback
    public interface Callbacks {
        void onResponse(@Nullable NewsArticles articles);
        void onFailure();
    }

    // Public method to start fetching top stories api data
    public static void fetchTopStories(Callbacks callbacks, String apikey){
        // Create a weak reference to callback (avoid memory leaks)
        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<Callbacks>(callbacks);
        // Get a Retrofit instance and the related endpoints
        NewsService newsService = NewsService.retrofit.create(NewsService.class);
        // Create the call on NewsArticles API
        Call<NewsArticles> call = newsService.getTopStories(apikey);
        // Start the call
        call.enqueue(new Callback<NewsArticles>() {

            @Override
            public void onResponse(Call<NewsArticles> call, Response<NewsArticles> response) {
                // 2.5 - Call the proper callback used in controller
                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onResponse(response.body());
            }

            @Override
            public void onFailure(Call<NewsArticles> call, Throwable t) {
                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onFailure();
            }
        });
    }

    // Public method to start fetching top stories api data
    public static void fetchMostPopular(Callbacks callbacks, String apikey){
        // Create a weak reference to callback (avoid memory leaks)
        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<Callbacks>(callbacks);
        // Get a Retrofit instance and the related endpoints
        NewsService newsService = NewsService.retrofit.create(NewsService.class);
        // Create the call on NewsArticles API
        Call<NewsArticles> call = newsService.getMostPopular(apikey);
        // Start the call
        call.enqueue(new Callback<NewsArticles>() {

            @Override
            public void onResponse(Call<NewsArticles> call, Response<NewsArticles> response) {
                // 2.5 - Call the proper callback used in controller
                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onResponse(response.body());
            }

            @Override
            public void onFailure(Call<NewsArticles> call, Throwable t) {
                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onFailure();
            }
        });
    }
}
