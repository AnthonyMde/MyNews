package com.mamode.anthony.mynews.controllers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mamode.anthony.mynews.R;
import com.mamode.anthony.mynews.fragments.SectionFragment;

/**
 * Abstract class used to factoring shared activity class code.
 * This class is also responsible to watch internet connectivity and
 * notify the user when some changes happened.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private boolean isSnackBarShown = false;

    /**
     * Our broadcast receiver is listening for connectivity changes.
     * When we lose wifi and mobile data, a indefinite snackbar is displayed.
     * As soon as the connection is recovered, we notify the user by an other snackbar.
     */
    private BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            View rootView = findViewById(android.R.id.content);

            if (!isOnline(context)) {
                Snackbar.make(rootView, R.string.no_internet_connection, Snackbar.LENGTH_INDEFINITE)
                        .addCallback(new Snackbar.Callback() {
                            @Override
                            public void onShown(Snackbar sb) {
                                super.onShown(sb);
                                isSnackBarShown = true;
                            }
                        })
                        .show();
            } else if (isSnackBarShown){
                    Snackbar.make(rootView, R.string.internet_connection_getback, Snackbar.LENGTH_SHORT)
                            .show();
                    isSnackBarShown = false;
            }
        }
    };

    /**
     * We register for a broadcast receiver which is listening
     * for connectivity action.
     */
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, intentFilter);
    }

    /**
     * We unregister our network-change listener when the application is paused.
     */
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkChangeReceiver);
    }

    /**
     * Allow us to know if the mobile user have a internet connection available.
     * @return true if there is wifi or mobile data connected.
     */
    private boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm != null ? cm.getActiveNetworkInfo() : null;
        // We check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }

    /**
     * Method used by child classes to set their toolbar up with
     * back button enabled to go back to home page.
     */
    protected void configureToolbar() {
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.include_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if(ab != null)
            ab.setDisplayHomeAsUpEnabled(true);
    }
}
