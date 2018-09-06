package com.mamode.anthony.mynews.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsArticle {
        @SerializedName("section")
        @Expose
        private String section = null;
        @SerializedName("subsection")
        @Expose
        private String subsection = null;
        @SerializedName("abstract")
        @Expose
        private String _abstract = null;
        @SerializedName("url")
        @Expose
        private String url = null;
        @SerializedName("published_date")
        @Expose
        private String publishedDate = null;
        @SerializedName("multimedia")
        @Expose
        private List<Multimedium> multimedia = null;
        //from MostPopular API
        @SerializedName("media")
        @Expose
        private List<Medium> media = null;

        public String getSection() {
            return section;
        }

        public String getSubsection() {
            return subsection;
        }

        public String getAbstract() {
            return _abstract;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPublishedDate() {
            return publishedDate;
        }

        public List<Multimedium> getMultimedia() {
            return multimedia;
        }

        public List<Medium> getMedia() {
            return media;
        }

    }
