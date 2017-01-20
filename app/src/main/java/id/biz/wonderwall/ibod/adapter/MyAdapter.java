/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package id.biz.wonderwall.ibod.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import id.biz.wonderwall.ibod.fragment.IGOFragment;

/**
 * Created by winnerawan on 12/14/16.
 */

public class MyAdapter extends FragmentPagerAdapter {
    private static final int NUM = 4;

    public MyAdapter(FragmentManager paramFragmentManager)
    {
        super(paramFragmentManager);
    }

    public int getCount()
    {
        return 4;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return new IGOFragment();
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return new IGOFragment();
            case 2: // Fragment # 0 - This will show FirstFragment different title
                return new IGOFragment();
            case 3: // Fragment # 0 - This will show FirstFragment different title
                return new IGOFragment();
            default:
                //return new FragmentMasakan();
                return new IGOFragment();
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
                return "New IGO";
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return "All IGO";
            case 2: // Fragment # 0 - This will show FirstFragment different title
                return "Barat";
            case 3: // Fragment # 0 - This will show FirstFragment different title
                return "Asia";
            default:
                return "New IGO";
        }
    }
}
