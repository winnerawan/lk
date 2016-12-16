/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.layarkaca.adapter;

/**
 * Created by winnerawan on 12/14/16.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * Created by winnerawan on 12/7/16.
 */


public class HomeAdapter extends FragmentPagerAdapter {

    private static final int NUM = 3;
    //private HomePage homePage;

    public HomeAdapter(FragmentManager paramFragmentManager)
    {
        super(paramFragmentManager);
    }

    public int getCount()
    {
        return 3;
    }

    public Fragment getItem(int paramInt)
    {
        switch (paramInt)
        {
            default:
                return null;
            case 0:
                //return new FeedFragment();
            case 1:
                //return new ExploreFragment();
        }
        //return new ContinueWatchingFragment();
        return null;
    }

    public int getItemPosition(Object paramObject)
    {
        return -2;
    }
    /*
    public void refresh(HomePage paramHomePage) {
        this.homePage = paramHomePage;
        notifyDataSetChanged();
    }
    */
}