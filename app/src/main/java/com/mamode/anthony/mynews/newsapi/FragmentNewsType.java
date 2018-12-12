package com.mamode.anthony.mynews.newsapi;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * All type of SectionFragment are declared here.
 */
public class FragmentNewsType {
    public static final int TOPSTORIES = 0;
    public static final int MOSTPOPULAR = 1;
    public static final int SCIENCE = 2;
    public static final int HEALTH = 3;
    public static final int WORLD = 4;
    public static final int TECHNOLOGY = 5;
    public static final int SEARCH = 6;

    /**
     * IntDef is a great alternative to have the same behavior as
     * enum but with much better performances in android.
     */
    @IntDef({TOPSTORIES, MOSTPOPULAR, HEALTH, TECHNOLOGY,
    SCIENCE, WORLD, SEARCH})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FragmentType{}
}
