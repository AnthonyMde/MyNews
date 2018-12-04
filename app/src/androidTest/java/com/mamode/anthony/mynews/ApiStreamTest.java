package com.mamode.anthony.mynews;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.mamode.anthony.mynews.NewsApi.ApiClient;
import com.mamode.anthony.mynews.NewsApi.NewsService;
import com.mamode.anthony.mynews.NewsRepository.NewsArticles;
import com.mamode.anthony.mynews.controllers.MainActivity;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test if our API endpoints are up.
 */
public class ApiStreamTest {
    private static NewsService service = null;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    /**
     * We create our client retrofit for all our tests.
     */
    @BeforeClass
    public static void setUp() {
       service = ApiClient.getRetrofitInstance(InstrumentationRegistry.getContext())
                .create(NewsService.class);
    }

    /**
     * Test if the topStories API is up
     */
    @Test
    public void topStoriesAPITest() throws IOException {
        Call<NewsArticles> topStories = service.getTopStories();
        Response<NewsArticles> response = topStories.execute();

        assertTrue(response.isSuccessful() && response.code() == 200);
        assertNotNull(response.body());
        assertNotNull(response.body().getArticles());
    }

    /**
     * Test if the mostPopular API is up
     */
    @Test
    public void mostPopularAPITest() throws IOException {
        Call<NewsArticles> mostPopular = service.getMostPopular();
        Response<NewsArticles> response = mostPopular.execute();

        assertTrue(response.isSuccessful() && response.code() == 200);
        assertNotNull(response.body());
        assertNotNull(response.body().getArticles());
    }

    /**
     * Test if the topStories sections API are up
     */
    @Test
    public void topStoriesHealthAPITest() throws IOException {
        Call<NewsArticles> topStoriesHealth = service.getTopStoriesHealth();
        Response<NewsArticles> response = topStoriesHealth.execute();

        assertTrue(response.isSuccessful() && response.code() == 200);
        assertNotNull(response.body());
        assertNotNull(response.body().getArticles());
    }

    /**
     * Test if the search article API is up
     */
    @Test
    public void searchArticlesAPITest() throws IOException {
        HashMap<String, String> query = new HashMap<>();
        query.put("q", "Butterfly");
        query.put("fq", "news_desk(\"Science\")");
        Call<NewsArticles> searchArticles = service.getSearchArticles(query);
        Response<NewsArticles> response = searchArticles.execute();

        assertTrue(response.isSuccessful() && response.code() == 200);
        assertNotNull(response.body());
        assertNotNull(response.body().getSearchArticles());
    }
}
