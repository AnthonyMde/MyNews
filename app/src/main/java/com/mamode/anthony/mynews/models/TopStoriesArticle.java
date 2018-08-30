package com.mamode.anthony.mynews.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mamode.anthony.mynews.utils.Article;

import java.util.List;

public class TopStoriesArticle {
        @SerializedName("section")
        @Expose
        private String section;
        @SerializedName("subsection")
        @Expose
        private String subsection;
        @SerializedName("abstract")
        @Expose
        private String _abstract;
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("published_date")
        @Expose
        private String publishedDate;
        @SerializedName("multimedia")
        @Expose
        private List<Multimedium> multimedia = null;
        @SerializedName("short_url")
        @Expose
        private String shortUrl;

        public String getImage() {
            return multimedia.get(0).getUrl();
        }

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }

        public String getSubsection() {
            return subsection;
        }

        public void setSubsection(String subsection) {
            this.subsection = subsection;
        }

        public String getAbstract() {
            return _abstract;
        }

        public void setAbstract(String _abstract) {
            this._abstract = _abstract;
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

        public void setPublishedDate(String publishedDate) {
            this.publishedDate = publishedDate;
        }

        public List<Multimedium> getMultimedia() {
            return multimedia;
        }

        public void setMultimedia(List<Multimedium> multimedia) {
            this.multimedia = multimedia;
        }

        public String getShortUrl() {
            return shortUrl;
        }

        public void setShortUrl(String shortUrl) {
            this.shortUrl = shortUrl;
        }

    }
