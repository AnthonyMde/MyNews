package com.mamode.anthony.mynews.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.mamode.anthony.mynews.R;
import com.mamode.anthony.mynews.adapters.PagerAdapter;

public class MainActivity extends AppCompatActivity {
    private android.support.v7.widget.Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureToolbar();
        this.configurePageAdapterAndTabs();
        this.configureDrawerLayout();
    }

    //---------------------------------------------
    //TOOLBAR CONFIGURATION
    //---------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_activity_main_menu_notifications:
                launchNotificationsActivity();
                return true;
            case R.id.menu_activity_main_menu_search:
                launchSearchActivity();
                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }

    private void configureToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }
    private void launchSearchActivity(){
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    private void launchNotificationsActivity(){
        Intent intent = new Intent(MainActivity.this, NotificationsActivity.class);
        startActivity(intent);
    }

    //---------------------------------------------
    //NAVIGATION DRAWER CONFIGURATION
    //---------------------------------------------
    private void configureDrawerLayout(){
        mDrawerLayout = findViewById(R.id.activity_main_drawer_layout);
        //Join the drawer with the toolbar to set the hamburger button
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    //---------------------------------------------
    //TABS and VIEWPAGER CONFIGURATION
    //---------------------------------------------
    private void configurePageAdapterAndTabs(){
        //Set adapter to viewpager
        mViewPager = findViewById(R.id.activity_main_viewpager);
        mViewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));

        //Glue the tabs with the viewpager + tabs had the same width
        mTabLayout = findViewById(R.id.activity_main_tabs);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }
}
