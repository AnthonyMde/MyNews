package com.mamode.anthony.mynews.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MostPopulars {
    @SerializedName("results")
    @Expose
    private List<MostPopularArticle> articles = null;

    public List<MostPopularArticle> getArticles() {
        return articles;
    }

    public void setResults(List<MostPopularArticle> articles) {
        this.articles = articles;
    }

}
