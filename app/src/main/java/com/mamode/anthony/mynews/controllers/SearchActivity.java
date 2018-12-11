package com.mamode.anthony.mynews.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.mamode.anthony.mynews.NewsApi.FragmentNewsType;
import com.mamode.anthony.mynews.R;
import com.mamode.anthony.mynews.fragments.SearchFragment;
import com.mamode.anthony.mynews.fragments.SectionFragment;
import com.mamode.anthony.mynews.utils.BaseActivity;

import java.util.HashMap;

public class SearchActivity extends BaseActivity implements SectionFragment.SectionFragmentCallback, SearchFragment.onResearchListener, SectionFragment.onResearchComeBack {
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    SectionFragment mSearchResultFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_articles);
        mFragmentManager = getSupportFragmentManager();
        configureToolbar();
        addSearchFragment();
    }

    /**
     * We need to add a SearchFragment instance when the user
     * arrived on the SearchActivity screen.
     */
    private void addSearchFragment() {
        SearchFragment searchFragment = SearchFragment.newInstance();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.frag_search_container, searchFragment).commit();
    }

    /**
     * Method called from the fragment_search_and_notif layout file,
     * when the user clicks on the checkboxes which implemented the onClick method.
     * The click is propagated through the search fragment.
     * @param view The checkbox that have been clicked.
     */
    public void onCheckboxClicked(View view) {
        SearchFragment searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.frag_search_container);
        if (searchFragment != null)
            searchFragment.onCheckboxClicked(view);
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

    /**
     * Replace SearchFragment instance by a SectionFragment instance (of search type)
     * to display the search results.
     * @param query the SectionFragment needs the query pass by the user to make the
     *              search request.
     */
    @Override
    public void displaySearchResults(HashMap<String, String> query) {
        mSearchResultFragment = SectionFragment.newInstance(FragmentNewsType.SEARCH, query);
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.frag_search_container, mSearchResultFragment).commit();
        mFragmentTransaction.addToBackStack("Search results fragment");
    }

    /**
     * Allow to get back to the SearchFragment if the user clicks on the
     * back navigation when he is on the SectionFragment search results.
     */
    @Override
    public void returnToSearchFragment() {
        mFragmentManager.popBackStack();
        SearchFragment searchFragment = SearchFragment.newInstance();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.frag_search_container, searchFragment).commit();
    }
}
