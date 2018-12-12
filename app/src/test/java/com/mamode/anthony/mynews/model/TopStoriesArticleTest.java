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
 * Test our getters on the TopStories API articles
 */
public class TopStoriesArticleTest {

    private static NewsArticle article;

    /**
     * This method is called only one time before all the unit tests.
     */
    @BeforeClass
    public static void setUp() {
        article = GsonParser.parseJson("top_stories_article.json", NewsArticle.class);
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

    @Test
    public void Given_DataApi_When_CallTopStories_Then_GetMultimedia() {
        List<Multimedia> multimedia = article.getMultimedia();
        assert(!multimedia.isEmpty());
    }

    @Test
    public void Given_DataApi_When_CallTopStories_Then_GetMultimediaUrl() {
        assertEquals("https://static01.nyt.com/images/2018/09/18/science/18SCI-SPARROWS1/merlin_141234075_fafe4100-5370-49a3-9fe0-97330cf98cd9-thumbStandard.jpg", article.getMultimedia().get(0).getUrl());
        assertNotEquals("https://static01.nyt.com/images/2018/09/18/science/18SCI-SPARROWS1/merlin_141234075_fafe4100-5370-49a3-9fe0-97330cf98cd9-thumbLarge.jpg", article.getMultimedia().get(1).getUrl());
    }
}