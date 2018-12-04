package com.mamode.anthony.mynews.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {
    /**
     * Allows to know if the user have a internet connection available.
     * @return true if there is wifi or mobile data available.
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm != null ? cm.getActiveNetworkInfo() : null;
        // We check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }
}
