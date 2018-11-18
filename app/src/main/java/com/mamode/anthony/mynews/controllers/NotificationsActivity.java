package com.mamode.anthony.mynews.controllers;

import android.os.Bundle;
import android.view.View;

import com.mamode.anthony.mynews.R;
import com.mamode.anthony.mynews.fragments.NotificationsFragment;

public class NotificationsActivity extends BaseActivity {
    private NotificationsFragment mNotificationsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_notifications);
        mNotificationsFragment = (NotificationsFragment) getSupportFragmentManager().findFragmentById(R.id.frag_notif);
        configureToolbar();
    }

    // Method call from the fragment_search_and_notif layout file
    public void onCheckboxClicked(View view) {
        if (mNotificationsFragment != null)
            mNotificationsFragment.onCheckboxClicked(view);
    }
}
