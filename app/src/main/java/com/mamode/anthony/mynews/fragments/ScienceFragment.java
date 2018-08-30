package com.mamode.anthony.mynews.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mamode.anthony.mynews.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScienceFragment extends Fragment {


    public ScienceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_science, container, false);
    }

    public static ScienceFragment newInstance() {
        return new ScienceFragment();
    }


}
