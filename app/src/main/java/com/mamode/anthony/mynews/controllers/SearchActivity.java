package com.mamode.anthony.mynews.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mamode.anthony.mynews.NewsApi.FragmentNewsType;
import com.mamode.anthony.mynews.R;
import com.mamode.anthony.mynews.fragments.SearchFragment;
import com.mamode.anthony.mynews.fragments.SectionFragment;

import java.util.HashMap;

public class SearchActivity extends BaseActivity implements SectionFragment.SectionFragmentCallback, SearchFragment.onResearchListener {
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_articles);
        mFragmentManager = getSupportFragmentManager();
        configureToolbar();
        addSearchFragment();
    }

    private void addSearchFragment() {
        SearchFragment searchFragment = SearchFragment.newInstance();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.frag_search_container, searchFragment).commit();
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
        SectionFragment sectionFragment = SectionFragment.newInstance(FragmentNewsType.SEARCH, query);
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.frag_search_container, sectionFragment).commit();
        mFragmentTransaction.addToBackStack("Search results fragment");
    }
}
