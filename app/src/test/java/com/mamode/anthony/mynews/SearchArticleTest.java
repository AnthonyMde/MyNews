package com.mamode.anthony.mynews;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SearchArticleTest {
    @Test
    private void Given_SearchInputAndThemesChecked_Then_AllowClick(){
        assertEquals(true, canResearch());
    }
    @Test
    private void Given_SearchInputAndThemesCheckedAndBeginDate_Then_AllowClick(){
        assertEquals(true, canResearch());
    }
    @Test
    private void Given_SearchInputAndThemesCheckedAndEndDate_Then_AllowClick(){
        assertEquals(true, canResearch());
    }
    @Test
    private void Given_SearchInputAndThemesCheckedAndBeginDateAndEndDate_Then_AllowClick(){
        assertEquals(true, canResearch());
    }
    @Test
    private void Given_NoSearchInputAndThemesCheckedAndBeginDate_Then_NotAllowClick(){
        assertNotEquals(true, canResearch());
    }
    @Test
    private void Given_SearchInputAndNoThemesChecked_Then_NotAllowClick(){
        assertNotEquals(true, canResearch());
    }
    @Test
    private void Given_NoSearchInputAndThemesChecked_Then_NotAllowClick(){
        assertNotEquals(true, canResearch());
    }
    @Test
    private void Given_NoSearchInputAndNoThemesChecked_Then_NotAllowClick(){
        assertNotEquals(true, canResearch());
    }
    @Test
    private void Given_SearchInputAndNoThemesCheckedAndBeginDate_Then_NotAllowClick(){
        assertNotEquals(true, canResearch());
    }
}
