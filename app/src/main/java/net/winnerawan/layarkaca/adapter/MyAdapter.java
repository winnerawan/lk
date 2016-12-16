/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.layarkaca.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.winnerawan.layarkaca.R;
import net.winnerawan.layarkaca.fragment.NewMovieFragment;
import net.winnerawan.layarkaca.fragment.PopularMovieFragment;
import net.winnerawan.layarkaca.fragment.RestrictedFragment;

/**
 * Created by winnerawan on 12/14/16.
 */

public class MyAdapter extends FragmentPagerAdapter {
    private static final int NUM = 3;

    public MyAdapter(FragmentManager paramFragmentManager)
    {
        super(paramFragmentManager);
    }

    public int getCount()
    {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return new NewMovieFragment();
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return new PopularMovieFragment();
            case 2:
                //return new FragmentKueBasah();
            case 3:
                return new RestrictedFragment();
            default:
                //return new FragmentMasakan();
                return new NewMovieFragment();
        }
    }

    public int getItemPosition(Object paramObject)
    {
        return -2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: // Fragment # 0 - This will show FirstFragment
                return "New Movies";
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return "Popular Movies";
            case 2: // Fragment # 0 - This will show FirstFragment different title
                return "18+";
            default:
                return "New Movies";
        }
    }
}
