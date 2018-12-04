package com.mamode.anthony.mynews.NewsRepository;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Model from the TopStories and Search API.
 * Contains the images of articles.
 */
public class Multimedia {
    @SerializedName("url")
    @Expose
    private String url;
    public String getUrl() {
        return url;
    }
}
