package com.mamode.anthony.mynews.utils;

import android.content.Context;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
}
