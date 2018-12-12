package com.mamode.anthony.mynews.model;

import com.mamode.anthony.mynews.GsonParser;
import com.mamode.anthony.mynews.newsmodels.Media;
import com.mamode.anthony.mynews.newsmodels.NewsArticle;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Test our getters on the MostPopular API articles
 */
public class MostPopularArticleTest {
    private static NewsArticle article;

    /**
     * This method is called only one time before all the unit tests.
     */
    @BeforeClass
    public static void setUp() {
        article = GsonParser.parseJson("most_popular_article.json", NewsArticle.class);
    }

    @Test
    public void Given_DataApi_When_CallMostPopular_Then_GetSectionData() {
        assertEquals("Science", article.getSection());
    }

    @Test
    public void Given_DataApi_When_CallMostPopular_GetSubSectionData() {
        assertEquals("", article.getSubsection());
    }

    @Test
    public void Given_DataApi_When_CallMostPopular_Then_GetAbstract() {
        assertEquals("Fifteen murder and sexual assault cases have been solved since April with a single genealogy website. This is how GEDmatch went from a casual side project to a revolutionary tool.", article.getAbstract());
    }

    @Test
    public void Given_DataApi_When_CallMostPopular_Then_GetPublishedDate() {
        assertEquals("2018-10-15", article.getPublishedDate());
    }

    @Test
    public void Given_DataApi_When_CallMostPopular_Then_GetMedia() {
        List<Media> media = article.getMedia();
        assert(!media.isEmpty());
    }

    @Test
    public void Given_DataApi_When_CallMostPopular_Then_GetMediaUrl() {
        assertEquals("https://static01.nyt.com/images/2018/09/25/science/00GEDMATCH1/00GEDMATCH1-thumbStandard.jpg", article.getMedia().get(0).getMediaMetadata().get(0).getUrl());
        assertNotEquals("https://static01.nyt.com/images/2018/09/25/science/00GEDMATCH1/00GEDMATCH1-thumbStandard.jpg", article.getMedia().get(0).getMediaMetadata().get(1).getUrl());
    }
}
