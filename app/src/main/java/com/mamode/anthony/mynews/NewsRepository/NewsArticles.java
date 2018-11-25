package com.mamode.anthony.mynews.NewsRepository;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

    public class NewsArticles {
        @SerializedName("results")
        @Expose
        private List<NewsArticle> articles = null;
        public List<NewsArticle> getArticles() {
            if (articles != null && articles.size() > 20)
                return articles.subList(0, 20);
            return articles;
        }

        @SerializedName("docs")
        @Expose
        private List<NewsArticle> searchArticles = null;
        public List<NewsArticle> getSearchArticles() {
            if (searchArticles != null && searchArticles.size() > 20)
                return searchArticles.subList(0, 20);
            return searchArticles;
        }
    }
