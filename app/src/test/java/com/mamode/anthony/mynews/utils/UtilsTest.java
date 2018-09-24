package com.mamode.anthony.mynews.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class UtilsTest {

    @Test
    public void Given_DayNumber_When_Nothing_Then_AddZeroToDateIfMinorTen() {
        assertEquals("08", Utils.addZeroToDate(8));
        assertEquals("12", Utils.addZeroToDate(12));
    }
}
