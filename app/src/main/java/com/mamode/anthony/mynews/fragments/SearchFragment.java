package com.mamode.anthony.mynews.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mamode.anthony.mynews.R;
import com.mamode.anthony.mynews.models.NewsArticles;
import com.mamode.anthony.mynews.utils.ArticleCalls;

public class SearchFragment extends Fragment implements ArticleCalls.ArticleCallsCallback{

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_and_notif, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // TODO: ArticleCalls.fetchSearchArticles(this, );
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResponse(@Nullable NewsArticles articles) {

    }

    @Override
    public void onFailure() {

    }
}
