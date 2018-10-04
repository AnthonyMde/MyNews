package com.mamode.anthony.mynews.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

    public class NewsArticles {
        @SerializedName("results")
        @Expose
        private List<NewsArticle> articles = null;
        public List<NewsArticle> getArticles() {
            return articles;
        }

        @SerializedName("docs")
        @Expose
        private List<NewsArticle> searchArticles = null;
        public List<NewsArticle> getSearchArticles() {return searchArticles;}
    }
