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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mamode.anthony.mynews.R;
import com.mamode.anthony.mynews.adapters.RecyclerViewAdapter;
import com.mamode.anthony.mynews.NewsRepository.NewsArticle;
import com.mamode.anthony.mynews.NewsRepository.NewsArticles;
import com.mamode.anthony.mynews.NewsApi.ArticleCalls;
import com.mamode.anthony.mynews.NewsApi.FragmentNewsType;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SectionFragment extends Fragment implements ArticleCalls.onAPIResponseListener, RecyclerViewAdapter.OnItemClickListener {
    @BindView(R.id.main_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.indeterminateBar)
    ProgressBar mProgressBar;
    @BindView(R.id.section_frag_no_article_text)
    TextView mNoArticleFoundText;

    private SectionFragmentCallback mCallback;
    private static final String FRAGMENT_TYPE = "FRAGMENT-TYPE";
    private int mFragmentType = 0;
    private HashMap<String, String> mSearchQuery = new HashMap<>();

    public SectionFragment() {
        // Required empty public constructor
    }
    public interface SectionFragmentCallback {
        void openUrl(String url);
    }

    public static SectionFragment newInstance(@FragmentNewsType.FragmentType int type) {
        SectionFragment fragment = new SectionFragment();
        Bundle args = new Bundle();
        args.putInt(FRAGMENT_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    public static SectionFragment newInstance(@FragmentNewsType.FragmentType int type, HashMap<String, String> query) {
        SectionFragment fragment = new SectionFragment();
        Bundle args = new Bundle();
        args.putInt(FRAGMENT_TYPE, type);
        args.putSerializable("QueryHashMap", query);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null)
            mCallback = (SectionFragmentCallback) getActivity();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFragmentType = getArguments().getInt(FRAGMENT_TYPE);
            mSearchQuery = (HashMap<String, String>) getArguments().getSerializable("QueryHashMap");
        }
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
        // NYT api call.
        mProgressBar.setVisibility(View.VISIBLE);
        if (mSearchQuery != null && !mSearchQuery.isEmpty())
            ArticleCalls.fetchNews(this, mFragmentType, mSearchQuery);
        else
            ArticleCalls.fetchNews(this, mFragmentType, null);
    }

    /**
     * Method call if NYT API call is successful.
     * @param articles list of articles fetched from the NYT API.
     */
    @Override
    public void onResponse(@Nullable NewsArticles articles) {
        if (articles != null) {
            this.configureRecyclerView(articles);
        }
        mProgressBar.setVisibility(View.GONE);
    }

    /**
     * Method call if NYT API call failed.
     */
    @Override
    public void onFailure() {
        Log.e("ArticleCalls-onFailure", "Can not reach NYT data API");
        mProgressBar.setVisibility(View.GONE);
        mNoArticleFoundText.setVisibility(View.VISIBLE);
    }

    private void configureRecyclerView(NewsArticles articles) {
        if(articles.getArticles() != null) {
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(articles.getArticles(), this);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.setAdapter(adapter);
        } else if (articles.getSearchArticles() != null) {
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(articles.getSearchArticles(), this);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.setAdapter(adapter);
        }

    }

    //Open webView on recyclerView item clicked.
    @Override
    public void onItemClick(NewsArticle article) {
        if (mCallback != null)
            mCallback.openUrl(article.getUrl());
    }
}
