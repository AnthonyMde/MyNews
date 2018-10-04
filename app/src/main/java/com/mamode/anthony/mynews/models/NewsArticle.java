package com.mamode.anthony.mynews.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsArticle {
        @SerializedName("section")
        @Expose
        private String section = null;

        // From Search API.
        @SerializedName("section_name")
        @Expose
        private String search_section = null;

        @SerializedName("subsection")
        @Expose
        private String subsection = null;

        // From Search API.
        @SerializedName("subsection_name")
        @Expose
        private String search_subsection = null;

        @SerializedName("abstract")
        @Expose
        private String _abstract = null;

        @SerializedName("url")
        @Expose
        private String url = null;

        // From Search API.
        @SerializedName("web_url")
        @Expose
        private String search_url = null;

        @SerializedName("published_date")
        @Expose
        private String publishedDate = null;

        // From Search API.
        @SerializedName("pub_date")
        @Expose
        private String search_publishedDate = null;

        //From TopStories and Search API.
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

        public String getSearchSection() { return search_section;}

        public String getSubsection() {
            return subsection;
        }

        public String getSearchSubsection() { return search_subsection;}

        public String getAbstract() {
            return _abstract;
        }

        public String getUrl() {
            return url;
        }

        public String getSearchUrl(){return search_url;}

        public String getPublishedDate() {
            return publishedDate;
        }

        public String getSearchPublishedDate(){ return search_publishedDate;}

        public List<Multimedia> getMultimedia() {
            return multimedia;
        }

        public List<Media> getMedia() {
            return media;
        }

    }
