package com.mamode.anthony.mynews.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mamode.anthony.mynews.models.NewsArticles;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleCalls {
    // Get a Retrofit instance and the related endpoints
    private static NewsService newsService = NewsService.retrofit.create(NewsService.class);
    private static Call<NewsArticles> call = null;

    // 1 - Creating a callback
    public interface ArticleCallsCallback {
        void onResponse(@Nullable NewsArticles articles);
        void onFailure();
    }

    public static void fetchSearchArticles(ArticleCallsCallback callback, HashMap<String, String> query){
        final WeakReference<ArticleCallsCallback> callbacksWeakReference = new WeakReference<>(callback);
        call = newsService.getSearchArticles(query);
    }
    // Public method to start fetching top stories api data
    public static void fetchNews(ArticleCallsCallback callback, String apikey, @FragmentNewsType.FragmentType int fragmentType) {
        // Create a weak reference to callback (avoid memory leaks)
        final WeakReference<ArticleCallsCallback> callbacksWeakReference = new WeakReference<>(callback);

        // Make the right API call according to the fragment name
        switch (fragmentType) {
            case FragmentNewsType.TOPSTORIES:
                call = newsService.getTopStories(apikey);
                break;
            case FragmentNewsType.MOSTPOPULAR:
                call = newsService.getMostPopular(apikey);
                break;
            case FragmentNewsType.SCIENCE:
                call = newsService.getTopStoriesScience(apikey);
                break;
            case FragmentNewsType.WORLD:
                call = newsService.getTopStoriesWorld(apikey);
                break;
            case FragmentNewsType.HEALTH:
                call = newsService.getTopStoriesHealth(apikey);
                break;
            case FragmentNewsType.TECHNOLOGY:
                call = newsService.getTopStoriesTechnology(apikey);
                break;
        }
        // Start the call
        if (call != null) {
            call.enqueue(new Callback<NewsArticles>() {
                @Override
                public void onResponse(@NonNull Call<NewsArticles> call,@NonNull Response<NewsArticles> response) {
                    // 2.5 - Call the proper callback used in controller
                    if (callbacksWeakReference.get() != null)
                        callbacksWeakReference.get().onResponse(response.body());
                }

                @Override
                public void onFailure(@NonNull Call<NewsArticles> call,@NonNull Throwable t) {
                    if (callbacksWeakReference.get() != null)
                        callbacksWeakReference.get().onFailure();
                }
            });
        }
    }
}

