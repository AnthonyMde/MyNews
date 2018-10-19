package com.mamode.anthony.mynews.NewsRepository;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Multimedia {
    @SerializedName("url")
    @Expose
    private String url;
    public String getUrl() {
        return url;
    }
}
