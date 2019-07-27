package com.st18rai.moviesapp.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.st18rai.moviesapp.ui.fragments.TabFavoritesMoviesFragment;
import com.st18rai.moviesapp.ui.fragments.TabMoviesListFragment;

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public MainPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new TabMoviesListFragment();
            case 1:
                return new TabFavoritesMoviesFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
