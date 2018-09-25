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
        //From TopStories API.
        @SerializedName("multimedia")
        @Expose
        private List<Multimedia> multimedia = null;
        //From MostPopular API.
        @SerializedName("media")
        @Expose
        private List<Media> media = null;

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

        public String getPublishedDate() {
            return publishedDate;
        }

        /* Specific to the TopStories API */
        public List<Multimedia> getMultimedia() {
            return multimedia;
        }

        /* Specific to the MostPopularArticle API */
        public List<Media> getMedia() {
            return media;
        }

    }
