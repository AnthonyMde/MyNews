package com.mamode.anthony.mynews.network;

import com.mamode.anthony.mynews.NewsApi.ApiClient;
import com.mamode.anthony.mynews.NewsApi.NewsService;
import com.mamode.anthony.mynews.NewsRepository.NewsArticles;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ApiStreamTest {
    @Test
    public void topStoriesAPITest() throws IOException {
        Call<NewsArticles> topStories = ApiClient
                .getRetrofitInstance()
                .create(NewsService.class)
                .getTopStories();
        Response<NewsArticles> response = topStories.execute();

        assertEquals(response.code(), 200);
        assertTrue(response.isSuccessful());
    }

    @Test
    public void mostPopularAPITest() throws IOException {
        Call<NewsArticles> mostPopular = ApiClient
                .getRetrofitInstance()
                .create(NewsService.class)
                .getMostPopular();
        Response<NewsArticles> response = mostPopular.execute();

        assertEquals(response.code(), 200);
        assertTrue(response.isSuccessful());
    }

    @Test
    public void topStoriesHealthAPITest() throws IOException {
        Call<NewsArticles> topStoriesHealth = ApiClient
                .getRetrofitInstance()
                .create(NewsService.class)
                .getTopStoriesHealth();
        Response<NewsArticles> response = topStoriesHealth.execute();

        assertEquals(response.code(), 200);
        assertTrue(response.isSuccessful());
    }

    @Test
    public void searchArticlesAPITest() throws IOException {
        HashMap<String, String> query = new HashMap<>();
        query.put("q", "Butterfly");
        query.put("fq", "news_desk(\"Science\")");
        Call<NewsArticles> searchArticles = ApiClient
                .getRetrofitInstance()
                .create(NewsService.class)
                .getSearchArticles(query);
        Response<NewsArticles> response = searchArticles.execute();

        assertEquals(response.code(), 200);
        assertTrue(response.isSuccessful());
    }
}
