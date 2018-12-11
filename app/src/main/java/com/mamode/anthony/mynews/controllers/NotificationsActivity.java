package com.mamode.anthony.mynews.controllers;

import android.os.Bundle;
import android.view.View;

import com.mamode.anthony.mynews.R;
import com.mamode.anthony.mynews.fragments.NotificationsFragment;
import com.mamode.anthony.mynews.utils.BaseActivity;

/**
 * Class where the user can set a specific research to be
 * notified when new articles are available.
 */
public class NotificationsActivity extends BaseActivity {
    private NotificationsFragment mNotificationsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_notifications);
        mNotificationsFragment = (NotificationsFragment) getSupportFragmentManager().findFragmentById(R.id.frag_notif);
        configureToolbar();
    }

    /**
     * Method called from the fragment_search_and_notif layout file,
     * when the user clicks on the checkboxes which implemented the onClick method.
     * The click is propagated through the notification fragment.
     * @param view The checkbox that have been clicked.
     */
    public void onCheckboxClicked(View view) {
        if (mNotificationsFragment != null)
            mNotificationsFragment.onCheckboxClicked(view);
    }
}
