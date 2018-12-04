package com.mamode.anthony.mynews.NewsApi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mamode.anthony.mynews.NewsRepository.NewsArticles;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ArticleCalls {
    private static Call<NewsArticles> call = null;

    /**
     * Callback implemented by controllers which want
     * to do some work on API response (success or failure).
     */
    public interface onAPIResponseListener {
        void onResponse(@Nullable NewsArticles articles);
        void onFailure(@NonNull Throwable t);
    }

    /**
     * Launch the API calls and handle the response (success and failure).
     * @param context is needed by the retrofit instance.
     * @param callback the activity/fragment that will handle the API response.
     * @param fragmentType determined the type of API call.
     * @param query nullable param only needed for search API call.
     */
    public static void fetchNews(Context context, onAPIResponseListener callback, @FragmentNewsType.FragmentType int fragmentType, @Nullable HashMap<String, String> query) {
        // Create a weak reference to callback (avoid memory leaks)
        final WeakReference<onAPIResponseListener> callbacksWeakReference = new WeakReference<>(callback);
        // Get a Retrofit instance and the related endpoints
        final NewsService newsService = ApiClient.getRetrofitInstance(context).create(NewsService.class);

        // Make the right API call according to the fragment type
        switch (fragmentType) {
            case FragmentNewsType.TOPSTORIES:
                call = newsService.getTopStories();
                break;
            case FragmentNewsType.MOSTPOPULAR:
                call = newsService.getMostPopular();
                break;
            case FragmentNewsType.SCIENCE:
                call = newsService.getTopStoriesScience();
                break;
            case FragmentNewsType.WORLD:
                call = newsService.getTopStoriesWorld();
                break;
            case FragmentNewsType.HEALTH:
                call = newsService.getTopStoriesHealth();
                break;
            case FragmentNewsType.TECHNOLOGY:
                call = newsService.getTopStoriesTechnology();
                break;
            case FragmentNewsType.SEARCH:
                call = newsService.getSearchArticles(query);
        }
        // Start the call
        if (call != null) {
            call.enqueue(new Callback<NewsArticles>() {
                // Call the proper callback used in our controller
                @Override
                public void onResponse(@NonNull Call<NewsArticles> call,@NonNull Response<NewsArticles> response) {
                    if (response.isSuccessful()) {
                        if (callbacksWeakReference.get() != null)
                            callbacksWeakReference.get().onResponse(response.body());
                    } else if (response.errorBody() != null) {
                        Log.e("API_RESPONSE_ERROR", response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<NewsArticles> call,@NonNull Throwable t) {
                    if (callbacksWeakReference.get() != null)
                        callbacksWeakReference.get().onFailure(t);
                    Log.e("API_FAILURE", t.toString());
                }
            });
        }
    }
}

