package com.mamode.anthony.mynews;

import android.app.Activity;
import android.content.Context;
import android.support.test.rule.ActivityTestRule
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SearchFragmentInstrumentationTest {
    @Rule
    public Activity rule = ActivityTestRule()

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.mamode.anthony.mynews", appContext.getPackageName());
    }
}
