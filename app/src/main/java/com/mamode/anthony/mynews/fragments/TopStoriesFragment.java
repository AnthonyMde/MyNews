package com.mamode.anthony.mynews.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mamode.anthony.mynews.R;
import com.mamode.anthony.mynews.adapters.TopStoriesAdapter;
import com.mamode.anthony.mynews.models.TopStories;
import com.mamode.anthony.mynews.utils.ArticleCalls;
import com.mamode.anthony.mynews.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopStoriesFragment extends Fragment implements ArticleCalls.Callbacks {
    private TopStoriesAdapter mAdapter;
    @BindView(R.id.main_recycler_view) RecyclerView mRecyclerView;

    public TopStoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_stories, container, false);
        ButterKnife.bind(this, view);
        ArticleCalls.fetchTopStories(this, Constants.API_KEY);
        return view;
    }


    public static TopStoriesFragment newInstance() {
        return new TopStoriesFragment();
    }

    //---------------------------------------------
    //RECYCLER VIEW CONFIGURATION
    //---------------------------------------------
    private void configureRecyclerView(TopStories articles) {
        mAdapter = new TopStoriesAdapter(articles.getArticles());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

    }


    @Override
    public void onResponse(@Nullable TopStories articles) {
        if (articles != null) {
            this.updateUIWithListOfArticles(articles);
        }
    }

    private void updateUIWithListOfArticles(TopStories articles) {
        this.configureRecyclerView(articles);
    }

    @Override
    public void onFailure() {
        Log.e("onFailure", "Inside");
    }
}
