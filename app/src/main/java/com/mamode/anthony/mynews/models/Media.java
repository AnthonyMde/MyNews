package com.mamode.anthony.mynews.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Media {
    @SerializedName("media-metadata")
    @Expose
    private List<MediaMetadata> mediaMetadata = null;

    public List<MediaMetadata> getMediaMetadata() {
        return mediaMetadata;
    }
}
