/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.layarkaca.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.astuetz.PagerSlidingTabStrip;

import net.winnerawan.layarkaca.R;
import net.winnerawan.layarkaca.fragment.AboutMovieFragment;
import net.winnerawan.layarkaca.fragment.AboutSemiMovieFragment;
import net.winnerawan.layarkaca.fragment.DownloadFragment;
import net.winnerawan.layarkaca.fragment.DownloadSemiMovieFragment;
import net.winnerawan.layarkaca.fragment.ReviewFragment;
import net.winnerawan.layarkaca.fragment.TrailerFragment;
import net.winnerawan.layarkaca.fragment.WatchMovieFragment;
import net.winnerawan.layarkaca.fragment.WatchSemiMovieFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SemiMovieDetailActivity extends AppCompatActivity {

    private static final String TAG = DetailMovieActivity.class.getSimpleName();
    public int movie_id;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tab_layout_explorer)
    PagerSlidingTabStrip tabs;
    @Bind(R.id.view_pager_explorer)
    ViewPager viewPager;
    @Bind(R.id.right_btn_toolbar)
    ImageView cross;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.anim_pop_up, R.anim.anim_push_up);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        movie_id = bundle.getInt("id");
        Log.e(TAG, "id = "+movie_id);

        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager()));
        tabs.setViewPager(viewPager);
    }

    public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 3;

        public SampleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return getResources().getString(R.string.about_movies);
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return getResources().getString(R.string.watch_movies);
                case 2: // Fragment # 0 - This will show FirstFragment different title
                    return getResources().getString(R.string.download);
                default:
                    return getResources().getString(R.string.about_movies);
            }
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return new AboutSemiMovieFragment();
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return new WatchSemiMovieFragment();
                case 2:
                    return new DownloadSemiMovieFragment();
                default:
                    //return new FragmentMasakan();
                    return new AboutSemiMovieFragment();
            }
        }
    }
}
