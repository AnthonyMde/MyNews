package com.mamode.anthony.mynews.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mamode.anthony.mynews.R;
import com.mamode.anthony.mynews.adapters.RecyclerViewAdapter;
import com.mamode.anthony.mynews.controllers.NewsWebView;
import com.mamode.anthony.mynews.models.NewsArticle;
import com.mamode.anthony.mynews.models.NewsArticles;
import com.mamode.anthony.mynews.utils.ArticleCalls;
import com.mamode.anthony.mynews.models.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScienceFragment extends Fragment implements ArticleCalls.Callbacks, RecyclerViewAdapter.OnItemClickListener {
    @BindView(R.id.main_recycler_view)
    RecyclerView mRecyclerView;

    public ScienceFragment() {
        // Required empty public constructor
    }
    public static ScienceFragment newInstance() {
        return new ScienceFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_stories, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //NYT api call.
        ArticleCalls.fetchNews(this, Constants.API_KEY, "Science");
    }

    //If data are received from the NYT api.
    @Override
    public void onResponse(@Nullable NewsArticles articles) {
        if (articles != null) {
            this.configureRecyclerView(articles);
        }
    }
    //If fetching NYT data api has failed.
    @Override
    public void onFailure() {
        Log.e("ArticleCalls-onFailure", "Can not reach NYT data API");
    }

    private void configureRecyclerView(NewsArticles articles) {
        RecyclerViewAdapter mAdapter = new RecyclerViewAdapter(articles.getArticles(), this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }

    //Open webView on recyclerView item clicked.
    @Override
    public void onItemClick(NewsArticle article) {
        Intent intent = new Intent(getActivity(), NewsWebView.class);
        intent.putExtra("url", article.getUrl());
        getActivity().startActivity(intent);
    }
}
