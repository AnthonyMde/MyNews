package com.mamode.anthony.mynews.NewsRepository;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Child of Media media model class.
 * Contains the url of images.
 */
public class MediaMetadata {

    @SerializedName("url")
    @Expose
    private String url;
    public String getUrl() {
        return url;
    }
}
