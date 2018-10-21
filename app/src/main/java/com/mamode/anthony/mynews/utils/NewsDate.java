package com.mamode.anthony.mynews.utils;

import com.mamode.anthony.mynews.NewsRepository.NewsArticle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class NewsDate {
    public static String addZeroToDate(int date){
        if (date < 10)
            return "0" + date;
        else
            return String.valueOf(date);
    }

    public static float convertDateIntoMillis(String date) throws ParseException {
        return new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).parse(date).getTime();
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
        if (d.length == 3)
            return d[1] + "/" + d[0] + "/" + d[2];
        else return "";
    }
}
