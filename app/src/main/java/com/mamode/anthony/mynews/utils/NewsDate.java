package com.mamode.anthony.mynews.utils;

import com.mamode.anthony.mynews.NewsRepository.NewsArticle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class NewsDate {
    static String addZeroToDate(int date){
        if (date < 10)
            return "0" + date;
        else
            return String.valueOf(date);
    }

    public static float convertDateIntoMillis(String date) throws ParseException {
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        formatDate.setTimeZone(TimeZone.getTimeZone("GMT"));
        return formatDate.parse(date).getTime();
    }

    // Convert the data api date in usable String.
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

    // Use to format the date displayed in our DatePicker (SearchFragment).
    public static String formatDate(int year, int month, int dayOfMonth) {
        return addZeroToDate(dayOfMonth)+"/"+ addZeroToDate(month+1)+"/"+year;
    }

    // Date format required by the search API.
    public static String setQueryDateFormat(int year, int month, int dayOfMonth) {
        return "" + year + (addZeroToDate(month+1)) + addZeroToDate(dayOfMonth);
    }

    // Format display to the user in the RecyclerView.
    public static String setFrenchDateFormat(String date) {
        String[] d = date.split("/");
        if (d.length == 3)
            return d[1] + "/" + d[0] + "/" + d[2];
        else return "";
    }
}
