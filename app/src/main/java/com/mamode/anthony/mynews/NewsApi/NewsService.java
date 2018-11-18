package com.mamode.anthony.mynews.NewsApi;

import com.mamode.anthony.mynews.NewsRepository.NewsArticles;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface NewsService {

    @GET("svc/topstories/v2/home.json")
    Call<NewsArticles> getTopStories();

    @GET("svc/mostpopular/v2/mostviewed/all-sections/1.json")
    Call<NewsArticles> getMostPopular();

    @GET("svc/topstories/v2/science.json")
    Call<NewsArticles> getTopStoriesScience();

    @GET("svc/topstories/v2/world.json")
    Call<NewsArticles> getTopStoriesWorld();

    @GET("svc/topstories/v2/health.json")
    Call<NewsArticles> getTopStoriesHealth();

    @GET("svc/topstories/v2/technology.json")
    Call<NewsArticles> getTopStoriesTechnology();

    @GET("svc/search/v2/articlesearch.json")
    Call<NewsArticles> getSearchArticles(@QueryMap Map<String, String> query);
}


