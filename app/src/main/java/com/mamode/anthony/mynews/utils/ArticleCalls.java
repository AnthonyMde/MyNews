package com.mamode.anthony.mynews.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mamode.anthony.mynews.models.NewsArticles;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mamode.anthony.mynews.utils.SectionType.*;

public class ArticleCalls {

    // 1 - Creating a callback
    public interface Callbacks {
        void onResponse(@Nullable NewsArticles articles);
        void onFailure();
    }

    // Public method to start fetching top stories api data
    public static void fetchNews(Callbacks callbacks, String apikey, SectionType fragmentType) {
        // Create a weak reference to callback (avoid memory leaks)
        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<>(callbacks);
        // Get a Retrofit instance and the related endpoints
        NewsService newsService = NewsService.retrofit.create(NewsService.class);
        // Create the call on NewsArticles API
        Call<NewsArticles> call = null;

        // Make the right API call according to the name fragment
        switch (fragmentType) {
            case TOPSTORIES:
                call = newsService.getTopStories(apikey);
                break;
            case MOSTPOPULAR:
                call = newsService.getMostPopular(apikey);
                break;
            case SCIENCE:
                call = newsService.getTopStoriesScience(apikey);
                break;
            case WORLD:
                call = newsService.getTopStoriesWorld(apikey);
                break;
            case HEALTH:
                call = newsService.getTopStoriesHealth(apikey);
                break;
            case TECHNOLOGY:
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

