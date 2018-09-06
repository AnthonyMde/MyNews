package com.mamode.anthony.mynews.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mamode.anthony.mynews.fragments.HealthFragment;
import com.mamode.anthony.mynews.fragments.ScienceFragment;
import com.mamode.anthony.mynews.fragments.MostPopularFragment;
import com.mamode.anthony.mynews.fragments.TechnologyFragment;
import com.mamode.anthony.mynews.fragments.TopStoriesFragment;
import com.mamode.anthony.mynews.fragments.WorldFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    // Create new fragment according to its position.
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return TopStoriesFragment.newInstance();
            case 1:
                return MostPopularFragment.newInstance();
            case 2:
                return ScienceFragment.newInstance();
            case 3:
                return WorldFragment.newInstance();
            case 4:
                return HealthFragment.newInstance();
            case 5:
                return TechnologyFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Top Stories";
            case 1:
                return "Most Popular";
            case 2:
                return "Science";
            case 3:
                return "World";
            case 4:
                return "Health";
            case 5:
                return "Technology";
            default:
                return null;
        }
    }
}


