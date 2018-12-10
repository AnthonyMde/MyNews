package com.mamode.anthony.mynews.controllers;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.mamode.anthony.mynews.R;

/**
 * Abstract class used to factoring shared activity class code.
 */
public abstract class BaseActivity extends AppCompatActivity {

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
