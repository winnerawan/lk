package net.winnerawan.layarkaca.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.TabLayoutOnPageChangeListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import net.winnerawan.layarkaca.R;
import net.winnerawan.layarkaca.activity.SplashActivity;
import net.winnerawan.layarkaca.adapter.FeedAdapter;

/**
 * Created by winnerawan on 12/7/16.
 */

public class FeedFragment extends Fragment {

    private FeedAdapter adapter;
    private FragmentManager manager;
    private FragmentActivity myContext;
    private ViewPager pager;
    private TabLayout tabLayout;

    private void initAction() {
        this.pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int paramAnonymousInt) {}

            public void onPageScrolled(int paramAnonymousInt1, float paramAnonymousFloat, int paramAnonymousInt2) {}

            public void onPageSelected(int paramAnonymousInt)
            {
                FragmentActivity localFragmentActivity = FeedFragment.this.getActivity();
                if ((localFragmentActivity instanceof SplashActivity)) {
                    //((SplashActivity)localFragmentActivity).onResumes();
                }
            }
        });
    }

    private void initViewPager(View paramView)
    {
        this.pager = ((ViewPager)paramView.findViewById(R.id.view_pager_feed));
        this.tabLayout = ((TabLayout)paramView.findViewById(R.id.tab_layout_feed));
        this.manager = this.myContext.getSupportFragmentManager();
        this.adapter = new FeedAdapter(this.manager);
        this.pager.setAdapter(this.adapter);
        this.pager.setOffscreenPageLimit(6);
        this.tabLayout.setupWithViewPager(this.pager);
        this.pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(this.tabLayout));
        this.tabLayout.setTabsFromPagerAdapter(this.adapter);
    }

    @Override
    public void onAttach(Activity paramActivity)
    {
        this.myContext = ((FragmentActivity)paramActivity);
        super.onAttach(paramActivity);
    }

    @Override
    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
    {
        View view = paramLayoutInflater.inflate(R.layout.fragment_feed, paramViewGroup, false);
        initViewPager(view);
        initAction();
        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.e("Debug", "Resume Fragment 1");
    }
}
