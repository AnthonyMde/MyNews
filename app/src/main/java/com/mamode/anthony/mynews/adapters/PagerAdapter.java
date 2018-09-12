package com.mamode.anthony.mynews.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mamode.anthony.mynews.fragments.SectionFragment;
import com.mamode.anthony.mynews.utils.SectionType;

public class PagerAdapter extends FragmentPagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    // Create new fragment according to its position.
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return SectionFragment.newInstance(SectionType.TOPSTORIES);
            case 1:
                return SectionFragment.newInstance(SectionType.MOSTPOPULAR);
            case 2:
                return SectionFragment.newInstance(SectionType.SCIENCE);
            case 3:
                return SectionFragment.newInstance(SectionType.HEALTH);
            case 4:
                return SectionFragment.newInstance(SectionType.WORLD);
            case 5:
                return SectionFragment.newInstance(SectionType.TECHNOLOGY);
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


