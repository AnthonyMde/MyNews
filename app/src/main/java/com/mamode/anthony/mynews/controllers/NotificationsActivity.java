package com.mamode.anthony.mynews.controllers;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mamode.anthony.mynews.R;
import com.mamode.anthony.mynews.fragments.NotificationsFragment;

public class NotificationsActivity extends AppCompatActivity {
    private NotificationsFragment notificationsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_notifications);
        notificationsFragment = (NotificationsFragment) getSupportFragmentManager().findFragmentById(R.id.frag_notif);
        this.configureToolbar();
    }

    private void configureToolbar(){
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.include_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if(ab != null)
            ab.setDisplayHomeAsUpEnabled(true);
    }

    // Method call from the fragment_search_and_notif layout file
    public void onCheckboxClicked(View view) {
        if (notificationsFragment != null)
            notificationsFragment.onCheckboxClicked(view);
    }
}
