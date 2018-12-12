package com.mamode.anthony.mynews.model;

import com.mamode.anthony.mynews.GsonParser;
import com.mamode.anthony.mynews.newsmodels.Multimedia;
import com.mamode.anthony.mynews.newsmodels.NewsArticle;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Test our getters on the SearchArticles API articles
 */
public class SearchArticleTest {
    private static NewsArticle article;

    /**
     * This method is called only one time before all the unit tests.
     */
    @BeforeClass
    public static void setUp() {
        article = GsonParser.parseJson("search_article.json", NewsArticle.class);
    }

    @Test
    public void Given_DataApi_When_CallSearchArticles_Then_GetSectionDataPlaceholder() {
        assertEquals("News Article", article.getSection());
    }

    @Test
    public void Given_DataApi_When_CallSearchArticles_GetSubSectionData() {
        assertEquals("", article.getSubsection());
    }

    @Test
    public void Given_DataApi_When_CallSearchArticles_Then_GetAbstract() {
        assertEquals("New York’s governor and other state authorities can ask right now for a criminal investigation of the president’s taxes.", article.getAbstract());
    }

    @Test
    public void Given_DataApi_When_CallSearchArticles_Then_GetPublishedDate() {
        assertEquals("2018-07-04T23:00:04+0000", article.getPublishedDate());
    }

    @Test
    public void Given_DataApi_When_CallSearchArticles_Then_GetMultimedia() {
        List<Multimedia> multimedia = article.getMultimedia();
        assert(!multimedia.isEmpty());
    }

    @Test
    public void Given_DataApi_When_CallSearchArticles_Then_GetMultimediaUrl() {
        assertEquals("images/2018/07/04/opinion/sunday/04johnston/merlin_139573884_570a430b-b978-4d5b-803b-16eaf23d34d2-articleLarge.jpg", article.getMultimedia().get(0).getUrl());
        assertNotEquals("images/2018/07/04/opinion/sunday/04johnston/merlin_139573884_570a430b-b978-4d5b-803b-16eaf23d34d2-articleLarge.jpg", article.getMultimedia().get(1).getUrl());
    }
}
