package com.mamode.anthony.mynews.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MediaMetadata {

    @SerializedName("url")
    @Expose
    private String url;
    public String getUrl() {
        return url;
    }
}