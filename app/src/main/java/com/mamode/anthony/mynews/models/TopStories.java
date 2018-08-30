package com.mamode.anthony.mynews.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

    public class TopStories {
        @SerializedName("results")
        @Expose
        private List<TopStoriesArticle> articles = null;

        public List<TopStoriesArticle> getArticles() {
            return articles;
        }

        public void setArticles(List<TopStoriesArticle> articles) {
            this.articles = articles;
        }

}
