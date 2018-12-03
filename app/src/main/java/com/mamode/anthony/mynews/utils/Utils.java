package com.mamode.anthony.mynews.utils;

import android.content.Context;

import com.mamode.anthony.mynews.NewsRepository.NewsArticle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

public class Utils {

    public static int convertDipInPixel(Context context, float dips) {
        final float SCALE = context.getResources().getDisplayMetrics().density;
        return (int)(dips * SCALE + 0.5f);
    }
}
