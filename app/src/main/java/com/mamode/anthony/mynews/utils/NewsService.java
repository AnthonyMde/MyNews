package com.mamode.anthony.mynews.utils;

import com.mamode.anthony.mynews.models.NewsArticles;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsService {

    @GET("svc/topstories/v2/home.json")
    Call<NewsArticles> getTopStories(@Query("api-key") String apiKey);

    @GET("svc/mostpopular/v2/mostviewed/all-sections/1.json")
    Call<NewsArticles> getMostPopular(@Query("api-key") String apiKey);

    @GET("svc/topstories/v2/science.json")
    Call<NewsArticles> getTopStoriesScience(@Query("api-key") String apiKey);

    @GET("svc/topstories/v2/world.json")
    Call<NewsArticles> getTopStoriesWorld(@Query("api-key") String apiKey);

    @GET("svc/topstories/v2/health.json")
    Call<NewsArticles> getTopStoriesHealth(@Query("api-key") String apiKey);

    @GET("svc/topstories/v2/technology.json")
    Call<NewsArticles> getTopStoriesTechnology(@Query("api-key") String apiKey);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}


