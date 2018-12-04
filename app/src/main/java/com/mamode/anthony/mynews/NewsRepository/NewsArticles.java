package com.mamode.anthony.mynews.NewsRepository;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The list of articles received from the NYT API.
 * We limit the number of articles received to 30.
 */
public class NewsArticles {
    /**
     * From the TopStories and MostPopular API
     */
    @SerializedName("results")
        @Expose
        private List<NewsArticle> articles = null;
        public List<NewsArticle> getArticles() {
            if (articles != null && articles.size() > 30)
                return articles.subList(0, 30);
            return articles;
        }

    /**
     * From the SearchArticle API
     */
    @SerializedName("docs")
    @Expose
    private List<NewsArticle> searchArticles = null;
    public List<NewsArticle> getSearchArticles() {
        if (searchArticles != null && searchArticles.size() > 30)
            return searchArticles.subList(0, 30);
        return searchArticles;
    }
}
