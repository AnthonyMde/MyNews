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
    public static void fetchNews(Callbacks callbacks, String apikey, String fragmentName) {
        // Create a weak reference to callback (avoid memory leaks)
        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<Callbacks>(callbacks);
        // Get a Retrofit instance and the related endpoints
        NewsService newsService = NewsService.retrofit.create(NewsService.class);
        // Create the call on NewsArticles API

        Call<NewsArticles> call = null;
        switch (fragmentName) {
            case "TopStories":
                call = newsService.getTopStories(apikey);
                break;
            case "MostPopular":
                call = newsService.getMostPopular(apikey);
                break;
            case "Science":
                call = newsService.getTopStoriesScience(apikey);
                break;
        }
        // Start the call
        if (call != null) {
            call.enqueue(new Callback<NewsArticles>() {

                @Override
                public void onResponse(Call<NewsArticles> call, Response<NewsArticles> response) {
                    // 2.5 - Call the proper callback used in controller
                    if (callbacksWeakReference.get() != null)
                        callbacksWeakReference.get().onResponse(response.body());
                }

                @Override
                public void onFailure(Call<NewsArticles> call, Throwable t) {
                    if (callbacksWeakReference.get() != null)
                        callbacksWeakReference.get().onFailure();
                }
            });
        }
    }
}

