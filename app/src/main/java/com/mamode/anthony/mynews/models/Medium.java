package com.mamode.anthony.mynews.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Medium {
    @SerializedName("media-metadata")
    @Expose
    private List<MediumMetadatum> mediumMetadata = null;

    public List<MediumMetadatum> getMediaMetadatum() {
        return mediumMetadata;
    }

    public void setMediaMetadata(List<MediumMetadatum> mediumMetadata) {
        this.mediumMetadata = mediumMetadata;
    }

}
