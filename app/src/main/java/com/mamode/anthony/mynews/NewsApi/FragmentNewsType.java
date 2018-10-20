package com.mamode.anthony.mynews.NewsApi;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class FragmentNewsType {
    public static final int TOPSTORIES = 0;
    public static final int MOSTPOPULAR = 1;
    public static final int SCIENCE = 2;
    public static final int HEALTH = 3;
    public static final int WORLD = 4;
    public static final int TECHNOLOGY = 5;
    public static final int SEARCH = 6;

    @IntDef({TOPSTORIES, MOSTPOPULAR, HEALTH, TECHNOLOGY,
    SCIENCE, WORLD, SEARCH})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FragmentType{}
}
