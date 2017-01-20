/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.ibod.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.astuetz.PagerSlidingTabStrip;

import net.winnerawan.ibod.R;
import net.winnerawan.ibod.fragment.AboutAnimeFragment;
import net.winnerawan.ibod.fragment.WatchAnimeFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AnimeListActivity extends AppCompatActivity {

    public int serial_id;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.tab_layout_explorer) PagerSlidingTabStrip tabs;
    @Bind(R.id.view_pager_explorer) ViewPager viewPager;
    @Bind(R.id.right_btn_toolbar) ImageView cross;
    @Bind(R.id.left_btn_toolbar) ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.anim_pop_up, R.anim.anim_push_up);
        cross.setVisibility(View.GONE);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        serial_id = bundle.getInt("id");

        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager()));
        tabs.setViewPager(viewPager);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAction();
            }
        });

    }

    public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 2;

        public SampleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return "About";
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return "Watch & Download";
                default:
                    return "About";
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
                    return new AboutAnimeFragment();
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return new WatchAnimeFragment();
                default:
                    return new AboutAnimeFragment();
            }
        }
    }

    private void finishAction() {
        finish();
        overridePendingTransition(R.anim.anim_pop_down, R.anim.anim_push_down);
    }

    public void onBackPressed() {
        finishAction();
    }
}
