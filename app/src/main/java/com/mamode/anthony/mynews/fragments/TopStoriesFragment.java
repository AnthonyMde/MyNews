package com.mamode.anthony.mynews.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mamode.anthony.mynews.R;
import com.mamode.anthony.mynews.adapters.MainRecyclerViewAdapter;
import com.mamode.anthony.mynews.controllers.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopStoriesFragment extends Fragment {
    private MainRecyclerViewAdapter mAdapter;
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
        this.configureRecyclerView();
        return view;
    }


    public static TopStoriesFragment newInstance() {
        return new TopStoriesFragment();
    }

    //---------------------------------------------
    //RECYCLER VIEW CONFIGURATION
    //---------------------------------------------
    private void configureRecyclerView() {
        mAdapter = new MainRecyclerViewAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

    }

}
