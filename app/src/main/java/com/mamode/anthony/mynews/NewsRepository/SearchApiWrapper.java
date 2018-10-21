package com.mamode.anthony.mynews.NewsRepository;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchApiWrapper {
    @SerializedName("docs")
    @Expose
    private List<NewsArticle> searchArticles = null;
    public List<NewsArticle> getSearchArticles() {return searchArticles;}
}
