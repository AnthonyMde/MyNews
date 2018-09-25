package com.mamode.anthony.mynews.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mamode.anthony.mynews.GsonParser;

import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import okio.Utf8;

import static org.junit.Assert.*;

public class TopStoriesArticleTest {

    private static NewsArticle article;

    @BeforeClass
    public static void setUp() {
        /*Gson gson = new GsonBuilder().create();
        try {
            article = gson.fromJson(FileUtils.readFileToString(new File("../app/src/test/assets/news_article.json"), "UTF-8"), NewsArticle.class);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        article = GsonParser.parseJson("news_article.json", NewsArticle.class);
    }

    @Test
    public void Given_DataApi_When_CallTopStories_Then_GetSectionData() {
        assertEquals("Science", article.getSection());
    }

    @Test
    public void Given_DataApi_When_CallTopStories_GetSubSectionData() {
        assertEquals("", article.getSubsection());
    }

    @Test
    public void Given_DataApi_When_CallTopStories_Then_GetAbstract() {
        assertEquals("Rising sea levels are bringing more nest-flooding tides that threaten to push the birds that breed in coastal marshes along the Atlantic Coast to extinction.", article.getAbstract());
    }

    @Test
    public void Given_DataApi_When_CallTopStories_Then_GetPublishedDate() {
        assertEquals("2018-09-17T13:18:27-04:00", article.getPublishedDate());
    }

    /* From TopStories */
    @Test
    public void Given_DataApi_When_CallTopStories_Then_GetMultimedia() {
        List<Multimedia> multimedia = article.getMultimedia();
        assert(!multimedia.isEmpty());
    }

    /* From TopStories API */
    @Test
    public void Given_DataApi_When_CallTopStories_Then_GetMultimediaUrl() {
        assertEquals("https://static01.nyt.com/images/2018/09/18/science/18SCI-SPARROWS1/merlin_141234075_fafe4100-5370-49a3-9fe0-97330cf98cd9-thumbStandard.jpg", article.getMultimedia().get(0).getUrl());
        assertNotEquals("https://static01.nyt.com/images/2018/09/18/science/18SCI-SPARROWS1/merlin_141234075_fafe4100-5370-49a3-9fe0-97330cf98cd9-thumbLarge.jpg", article.getMultimedia().get(1).getUrl());
    }

    /* From MostPopular API */
    @Test
    public void Given_DataApi_When_CallApi_Then_GetMedia() {

    }
}