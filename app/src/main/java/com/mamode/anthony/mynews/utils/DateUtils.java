package com.mamode.anthony.mynews.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static String addZeroToDate(int date){
        if (date < 10)
            return "0" + date;
        else
            return String.valueOf(date);
    }

    public static float convertDateIntoMillis(String date){
        try{
            return new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).parse(date).getTime();
        }catch (Exception e){
            Log.e("convertDateIntoMillis", "Error during parsing date");
            return new Date().getTime();
        }
    }
}
