package com.mamode.anthony.mynews.newsmodels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The article model. We have kept only the necessary getters.
 * We custom some getters to get the right variable according
 * to which type of articles we have.
 */
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

        @SerializedName("snippet")
        @Expose
        private String snippet = null;

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
            if(section != null) return section;
            if(search_section != null) return search_section;
            else return "News Article";
        }

        public String getSubsection() {
            if(subsection != null) return subsection;
            if(search_subsection != null) return search_subsection;
            else return "";
        }

        public String getAbstract() {
            if(_abstract != null) return _abstract;
            if(snippet != null) return snippet;
            else return "";
        }

        public String getUrl() {
            if(url != null) return url;
            if(search_url != null) return search_url;
            else return null;
        }

        public String getPublishedDate() {
            if(publishedDate != null) return publishedDate;
            if(search_publishedDate != null) return search_publishedDate;
            else return "";
        }

        public List<Multimedia> getMultimedia() {
            return multimedia;
        }

        public List<Media> getMedia() {
            return media;
        }

    }
