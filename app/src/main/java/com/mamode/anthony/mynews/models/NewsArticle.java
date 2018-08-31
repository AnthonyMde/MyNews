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

        public List<Medium> getMedia() {
            return media;
        }

        public void setMedia(List<Medium> media) {
            this.media = media;
        }

    }
