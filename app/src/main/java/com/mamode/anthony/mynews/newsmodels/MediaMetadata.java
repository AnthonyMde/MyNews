package com.mamode.anthony.mynews.newsmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Child of Media media model class.
 * Contains the url of images.
 */
public class MediaMetadata {

    @SerializedName("url")
    @Expose
    private String url = null;
    public String getUrl() {
        return url;
    }
}
