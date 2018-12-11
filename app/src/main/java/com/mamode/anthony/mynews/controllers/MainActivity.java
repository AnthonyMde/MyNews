package com.mamode.anthony.mynews.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;

import com.mamode.anthony.mynews.R;
import com.mamode.anthony.mynews.adapters.PagerAdapter;
import com.mamode.anthony.mynews.fragments.SectionFragment;
import com.mamode.anthony.mynews.utils.BaseActivity;
import com.mamode.anthony.mynews.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements SectionFragment.SectionFragmentCallback{
    @BindView(R.id.toolbar) android.support.v7.widget.Toolbar mToolbar;
    @BindView(R.id.activity_main_viewpager) ViewPager mViewPager;
    @BindView(R.id.activity_main_tabs) TabLayout mTabLayout;
    @BindView(R.id.activity_main_drawer_layout) DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view) NavigationView mNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        this.configureToolbar();
        this.configurePageAdapterAndTabs();
        this.configureDrawerLayout();
    }

    //---------------------------------------------
    // TOOLBAR CONFIGURATION
    //---------------------------------------------

    /**
     * Set layout for our toolbar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_button, menu);
        return true;
    }

    /**
     * Launch our activities from the menu according to the ID
     * of the selected item
     */
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

    protected void configureToolbar() {
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
    // NAVIGATION DRAWER CONFIGURATION
    //---------------------------------------------

    /**
     *  Join the drawer with the toolbar to set the hamburger button.
     */
    private void configureDrawerLayout(){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener((@NonNull MenuItem menuItem) -> {
                switch (menuItem.getItemId()) {
                    case R.id.drawer_search_articles:
                        launchSearchActivity();
                        break;
                    case R.id.drawer_notifications:
                        launchNotificationsActivity();
                        break;
                    case R.id.drawer_top_stories_articles:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.drawer_most_pop_articles:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.drawer_science_articles:
                        mViewPager.setCurrentItem(2);
                        break;
                    case R.id.drawer_world_articles:
                        mViewPager.setCurrentItem(3);
                        break;
                    case R.id.drawer_health_articles:
                        mViewPager.setCurrentItem(4);
                        break;
                    case R.id.drawer_technology_articles:
                        mViewPager.setCurrentItem(5);
                        break;
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        );
        // Top stories is selected by default
        mNavigationView.setCheckedItem(R.id.drawer_top_stories_articles);
    }

    //---------------------------------------------
    // TABS and VIEWPAGER CONFIGURATION
    //---------------------------------------------
    private void configurePageAdapterAndTabs(){
        // Set adapter to viewpager.
        mViewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        // Set active the corresponding item in the navigation drawer.
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        mNavigationView.setCheckedItem(R.id.drawer_top_stories_articles);
                        break;
                    case 1:
                        mNavigationView.setCheckedItem(R.id.drawer_most_pop_articles);
                        break;
                    case 2:
                        mNavigationView.setCheckedItem(R.id.drawer_science_articles);
                        break;
                    case 3:
                        mNavigationView.setCheckedItem(R.id.drawer_world_articles);
                        break;
                    case 4:
                        mNavigationView.setCheckedItem(R.id.drawer_health_articles);
                        break;
                    case 5:
                        mNavigationView.setCheckedItem(R.id.drawer_technology_articles);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        // Glue the tabs with the viewpager.
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        // Set the same width for each tab.
        int paddingInPixels = Utils.convertDipInPixel(getBaseContext(), 52);
        mTabLayout.getChildAt(mTabLayout.getChildCount()-1).setPadding(paddingInPixels, 0 , paddingInPixels, 0);
    }

    /**
     * Callback from section fragment which allows to open
     * an article in the NewsWebView class.
     * @param url url of the article displayed in the webview.
     */
    @Override
    public void openUrl(String url) {
        Intent intent = new Intent(this, NewsWebView.class);
        intent.putExtra("url",url);
        startActivity(intent);
    }
}
