package com.mamode.anthony.mynews.controllers;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.mamode.anthony.mynews.NewsApi.FragmentNewsType;
import com.mamode.anthony.mynews.R;
import com.mamode.anthony.mynews.fragments.SearchFragment;
import com.mamode.anthony.mynews.fragments.SectionFragment;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_articles);
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
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frag_search_container, searchFragment).commit();
    }

    public void replaceWithResearchResultsFragment() {
        SectionFragment sectionFragment = SectionFragment.newInstance(FragmentNewsType.SCIENCE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frag_search_container, sectionFragment).commit();
    }
    // Method call from the fragment_search_and_notif layout file
    public void onCheckboxClicked(View view) {
        SearchFragment searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.frag_search_container);
        if (searchFragment != null)
            searchFragment.onCheckboxClicked(view);
    }
}
