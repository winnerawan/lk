/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.ibod.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.winnerawan.ibod.fragment.AnimeFragment;

/**
 * Created by winnerawan on 12/15/16.
 */

public class ThirdAdapter extends FragmentPagerAdapter {
    private static final int NUM = 1;

    public ThirdAdapter(FragmentManager paramFragmentManager) {
        super(paramFragmentManager);
    }

    public int getCount() {
        return 1;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return new AnimeFragment();
            default:
                //return new FragmentMasakan();
                return new AnimeFragment();
        }
    }

    public int getItemPosition(Object paramObject) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return "Anime";
            default:
                return "Anime";
        }
    }
}
