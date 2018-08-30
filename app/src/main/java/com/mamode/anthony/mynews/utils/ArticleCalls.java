package com.mamode.anthony.mynews.utils;

import android.support.annotation.Nullable;

import com.mamode.anthony.mynews.models.TopStories;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleCalls {

    // 1 - Creating a callback
    public interface Callbacks {
        void onResponse(@Nullable TopStories articles);
        void onFailure();
    }

    // 2 - Public method to start fetching users following by Jake Wharton
    public static void fetchTopStories(Callbacks callbacks, String apikey){

        // 2.1 - Create a weak reference to callback (avoid memory leaks)
        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<Callbacks>(callbacks);

        // 2.2 - Get a Retrofit instance and the related endpoints
        NewsService newsService = NewsService.retrofit.create(NewsService.class);

        // 2.3 - Create the call on TopStories API
        Call<TopStories> call = newsService.getTopStories(apikey);
        // 2.4 - Start the call
        call.enqueue(new Callback<TopStories>() {

            @Override
            public void onResponse(Call<TopStories> call, Response<TopStories> response) {
                // 2.5 - Call the proper callback used in controller
                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onResponse(response.body());
            }

            @Override
            public void onFailure(Call<TopStories> call, Throwable t) {
                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onFailure();
            }
        });
    }
}
