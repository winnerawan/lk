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

import com.astuetz.PagerSlidingTabStrip;

import net.winnerawan.layarkaca.R;
import net.winnerawan.layarkaca.fragment.AboutMovieFragment;
import net.winnerawan.layarkaca.fragment.NewMovieFragment;
import net.winnerawan.layarkaca.fragment.RestrictedFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailMovieActivity extends AppCompatActivity {

    private static final String TAG = DetailMovieActivity.class.getSimpleName();
    public int movie_id;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.tab_layout_explorer) PagerSlidingTabStrip tabs;
    @Bind(R.id.view_pager_explorer) ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        movie_id = bundle.getInt("id");
        bundle.putString("movie_id", String.valueOf(movie_id));
        AboutMovieFragment fragment = new AboutMovieFragment();
        fragment.setArguments(bundle);

        String title = bundle.getString("title");
        String image = bundle.getString("image");

        Log.e(TAG, "id = "+movie_id);

        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager()));
        tabs.setViewPager(viewPager);
    }

    public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 4;

        public SampleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return getResources().getString(R.string.about_movies);
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return getResources().getString(R.string.trailer_movies);
                case 2: // Fragment # 0 - This will show FirstFragment different title
                    return getResources().getString(R.string.review_movies);
                case 3: // Fragment # 0 - This will show FirstFragment different title
                    return getResources().getString(R.string.watch_movies);
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
                    return new AboutMovieFragment();
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    //return new FragmentAyam();
                case 2:
                    //return new FragmentKueBasah();
                case 3:
                    return new RestrictedFragment();
                default:
                    //return new FragmentMasakan();
                    return new AboutMovieFragment();
            }
        }
    }
}
