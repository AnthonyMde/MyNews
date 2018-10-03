package com.mamode.anthony.mynews.utils;

import android.content.Context;
import android.util.Log;

import com.mamode.anthony.mynews.models.NewsArticle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Utils {
    public static String addZeroToDate(int date){
        if (date < 10)
            return "0" + date;
        else
            return String.valueOf(date);
    }

    public static float convertDateIntoMillis(String date) throws ParseException{
            return new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).parse(date).getTime();
    }

    public static int convertDipInPixel(Context context, float dips) {
        final float SCALE = context.getResources().getDisplayMetrics().density;
        return (int)(dips * SCALE + 0.5f);
    }

    //Convert the data api date in usable String.
    public static String parseDate(NewsArticle article){
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));   // This line converts the given date into UTC time zone
        java.util.Date dateObj;
        try {
            dateObj = sdf.parse(article.getPublishedDate());
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
        return new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(dateObj);
    }

    public static String setFrenchDateFormat(String date) {
        String[] d = date.split("/");
        return d[1] + "/" + d[0] + "/" + d[2];
    }
}
