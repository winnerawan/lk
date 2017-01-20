/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.ibod.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.winnerawan.ibod.R;
import net.winnerawan.ibod.adapter.MyAdapter;

/**
 * Created by winnerawan on 12/14/16.
 */

public class MovieFragment extends Fragment {

    private TabLayout tabs;
    private MyAdapter adapter;
    private ViewPager pager;
    private FragmentManager manager;
    private FragmentActivity myContext;

    private void initAction() {
        this.pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            public void onPageScrollStateChanged(int paramAnonymousInt) {}

            public void onPageScrolled(int paramAnonymousInt1, float paramAnonymousFloat, int paramAnonymousInt2) {}

            public void onPageSelected(int paramAnonymousInt)
            {
                FragmentActivity localFragmentActivity = MovieFragment.this.getActivity();
            }
        });
    }

    private void initViewPager(View paramView)
    {
        this.pager = ((ViewPager)paramView.findViewById(R.id.view_pager_feed));
        this.tabs = ((TabLayout)paramView.findViewById(R.id.tab_layout_feed));
        this.manager = this.myContext.getSupportFragmentManager();
        this.adapter = new MyAdapter(this.manager);
        this.pager.setAdapter(this.adapter);
        this.pager.setOffscreenPageLimit(3);
        this.tabs.setupWithViewPager(this.pager);
        this.pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(this.tabs));
        this.tabs.setTabsFromPagerAdapter(this.adapter);
    }

    @Override
    public void onAttach(Activity paramActivity)
    {
        this.myContext = ((FragmentActivity)paramActivity);
        super.onAttach(paramActivity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle paramBundle) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        initViewPager(view);
        initAction();
        return view;
    }
}
