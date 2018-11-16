package com.mamode.anthony.mynews.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.mamode.anthony.mynews.NewsApi.FragmentNewsType;
import com.mamode.anthony.mynews.R;
import com.mamode.anthony.mynews.fragments.SearchFragment;
import com.mamode.anthony.mynews.fragments.SectionFragment;

import java.util.HashMap;

public class SearchActivity extends AppCompatActivity implements SectionFragment.SectionFragmentCallback, SearchFragment.onResearchListener {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_articles);
        fragmentManager = getSupportFragmentManager();
        configureToolbar();
        addSearchFragment();
    }

    private void configureToolbar() {
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.include_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if(ab != null)
            ab.setDisplayHomeAsUpEnabled(true);
    }

    private void addSearchFragment() {
        SearchFragment searchFragment = SearchFragment.newInstance();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frag_search_container, searchFragment).commit();
    }

    // Method call from the fragment_search_and_notif layout file
    public void onCheckboxClicked(View view) {
        SearchFragment searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.frag_search_container);
        if (searchFragment != null)
            searchFragment.onCheckboxClicked(view);
    }

    @Override
    public void openUrl(String url) {
        Intent intent = new Intent(this, NewsWebView.class);
        intent.putExtra("url",url);
        startActivity(intent);
    }

    @Override
    public void displaySearchResults(HashMap<String, String> query) {
        Log.e("APITRY", "In the activity : " + query.get("api-key"));
        SectionFragment sectionFragment = SectionFragment.newInstance(FragmentNewsType.SEARCH, query);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag_search_container, sectionFragment).commit();
        fragmentTransaction.addToBackStack("Result Fragment");
    }
}
