package com.mamode.anthony.mynews.NewsRepository;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Model from MostPopular API.
 * Contains the image of articles.
 */
public class Media {
    @SerializedName("media-metadata")
    @Expose
    private List<MediaMetadata> mediaMetadata = null;

    public List<MediaMetadata> getMediaMetadata() {
        return mediaMetadata;
    }
}
