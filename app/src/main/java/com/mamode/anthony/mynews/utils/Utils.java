package com.mamode.anthony.mynews.utils;

import android.content.Context;
import android.os.Build;

import com.mamode.anthony.mynews.NewsRepository.NewsArticle;
import com.mamode.anthony.mynews.R;

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

    public static String concatenateQueryThemes(HashMap<String, String> themesQueryMap) {
        StringBuilder sb = new StringBuilder();
        for(String key : themesQueryMap.keySet()) {
            sb.append(String.format("\"%s\" ", key));
        }
        return String.format("news_desk(%s)", sb);
    }

    /**
     * Use for AlertDialog compatibility
     * @return the theme according to the android version.
     */
    public static int styleCompatWithLollipopAndBelow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return android.R.style.Theme_Material_Dialog_Alert;
        }
        return R.style.Theme_AppCompat_Dialog_Alert;
    }
}
