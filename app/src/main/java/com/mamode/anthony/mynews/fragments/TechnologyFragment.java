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
import android.widget.TextView;

import com.mamode.anthony.mynews.R;
import com.mamode.anthony.mynews.adapters.RecyclerViewAdapter;
import com.mamode.anthony.mynews.controllers.NewsWebView;
import com.mamode.anthony.mynews.models.NewsArticle;
import com.mamode.anthony.mynews.models.NewsArticles;
import com.mamode.anthony.mynews.utils.ArticleCalls;
import com.mamode.anthony.mynews.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TechnologyFragment extends Fragment implements ArticleCalls.Callbacks, RecyclerViewAdapter.OnItemClickListener {
    @BindView(R.id.main_recycler_view)
    RecyclerView mRecyclerView;

    public TechnologyFragment() {
        // Required empty public constructor
    }
    public static TechnologyFragment newInstance() {
        return new TechnologyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_stories, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArticleCalls.fetchNews(this, Constants.API_KEY, "Technology");
    }

    @Override
    public void onResponse(@Nullable NewsArticles articles) {
        if (articles != null) {
            this.configureRecyclerView(articles);
        }
    }
    @Override
    public void onFailure() {
        Log.e("onFailure", "Inside");
    }

    private void configureRecyclerView(NewsArticles articles) {
        RecyclerViewAdapter mAdapter = new RecyclerViewAdapter(articles.getArticles(), this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClik(NewsArticle article) {
        Intent intent = new Intent(getActivity(), NewsWebView.class);
        intent.putExtra("url", article.getUrl());
        getActivity().startActivity(intent);
    }
}

