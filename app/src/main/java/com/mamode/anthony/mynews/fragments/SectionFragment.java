package com.mamode.anthony.mynews.fragments;


import android.content.Context;
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
import com.mamode.anthony.mynews.NewsRepository.NewsArticle;
import com.mamode.anthony.mynews.NewsRepository.NewsArticles;
import com.mamode.anthony.mynews.NewsApi.ArticleCalls;
import com.mamode.anthony.mynews.model.Constants;
import com.mamode.anthony.mynews.NewsApi.FragmentNewsType;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SectionFragment extends Fragment implements ArticleCalls.ArticleCallsCallback, RecyclerViewAdapter.OnItemClickListener {
    @BindView(R.id.main_recycler_view)
    RecyclerView mRecyclerView;

    private SectionFragmentCallback callback;
    private static final String FRAGMENT_TYPE = "FRAGMENT-TYPE";
    private int mFragmentType;

    public SectionFragment() {
        // Required empty public constructor
    }
    public static SectionFragment newInstance(@FragmentNewsType.FragmentType int type) {
        SectionFragment fragment = new SectionFragment();
        Bundle args = new Bundle();
        args.putInt(FRAGMENT_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }


    public static SectionFragment newInstance(List<NewsArticle> articles) {
        SectionFragment fragment = new SectionFragment();
        Bundle args = new Bundle();
        args.putInt(FRAGMENT_TYPE, 0);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        mFragmentType = getArguments().getInt(FRAGMENT_TYPE);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_section, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //NYT api call.
        ArticleCalls.fetchNews(this, Constants.API_KEY, mFragmentType);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getActivity() != null)
            callback = (SectionFragmentCallback) getActivity();
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
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(articles.getArticles(), this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);
    }

    //Open webView on recyclerView item clicked.
    @Override
    public void onItemClick(NewsArticle article) {
        if (callback != null)
            callback.openUrl(article.getUrl());
    }

    public interface SectionFragmentCallback {
        void openUrl(String url);
    }
}
