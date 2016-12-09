package net.winnerawan.layarkaca.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.google.firebase.messaging.FirebaseMessaging;

import net.winnerawan.layarkaca.R;
import net.winnerawan.layarkaca.adapter.HomeAdapter;
import net.winnerawan.layarkaca.app.AppConfig;
import net.winnerawan.layarkaca.component.NonSwipeableViewPager;
import net.winnerawan.layarkaca.fragment.NewMovieFragment;
import net.winnerawan.layarkaca.fragment.RestrictedFragment;
import net.winnerawan.layarkaca.helper.SQLiteHandler;
import net.winnerawan.layarkaca.util.NotificationUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private HomeAdapter homeAdapter;

    private String AGE;
    private SQLiteHandler db;

    @Bind(R.id.pagerHome) ViewPager pagerHome;
    @Bind(R.id.f_btn_explorer) FrameLayout f_btn_explorer;
    @Bind(R.id.f_btn_continue_watching) FrameLayout f_btn_continue_watching;
    @Bind(R.id.f_btn_search) FrameLayout f_btn_search;
    @Bind(R.id.f_btn_more) FrameLayout f_btn_more;
    @Bind(R.id.f_btn_feeds) FrameLayout f_btn_feeds;
    @Bind(R.id.btn_explorer) ImageView btn_explorer;
    @Bind(R.id.btn_feeds) ImageView btn_feeds;
    @Bind(R.id.btn_continue_watching) ImageView btn_continue_watching;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.e("TAG", "HOME_ACTIVITY");
        ButterKnife.bind(this);
        //initAndSetAdapter();
        //pagerHome.setOffscreenPageLimit(3);

        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        AGE = user.get("age");

        pagerHome.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager()));
        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(pagerHome);
        //setupViewPager(pagerHome);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(AppConfig.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(AppConfig.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(AppConfig.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                }
            }
        };

        displayFirebaseRegId();

        f_btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(i);
            }
        });

        f_btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, MoreActivity.class);
                startActivity(i);
            }
        });

        f_btn_feeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.this.setNonActive();
                HomeActivity.this.setCurentItemHomePager(0);
                HomeActivity.this.btn_feeds.setImageResource(R.mipmap.ico_feeds_active);
            }
        });

        f_btn_explorer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, SessionMovieActivity.class);
                startActivity(i);
                finish();
                HomeActivity.this.setNonActive();
                HomeActivity.this.setCurentItemHomePager(0);
                HomeActivity.this.btn_feeds.setImageResource(R.mipmap.ico_explorer_active);
            }
        });

    }

    private void initAndSetAdapter() {
        this.homeAdapter = new HomeAdapter(getSupportFragmentManager());
        this.pagerHome.setAdapter(this.homeAdapter);
        this.pagerHome.setOffscreenPageLimit(3);
    }

    private void setNonActive() {
        //ImageView img_feeds =
        this.btn_feeds.setImageResource(R.mipmap.ico_feeds);
        this.btn_explorer.setImageResource(R.mipmap.ico_explorer);
        this.btn_continue_watching.setImageResource(R.mipmap.ico_continue_watching);
    }

    private void setCurentItemHomePager(int paramInt) {
        this.pagerHome.setCurrentItem(paramInt);
    }

    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(AppConfig.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(AppConfig.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(AppConfig.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 3;

        public SampleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0: // Fragment # 0 - This will show FirstFragment
                    return getResources().getString(R.string.new_movies);
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return getResources().getString(R.string.populer_movies);
                case 2: // Fragment # 0 - This will show FirstFragment different title
                    return "18+";
                default:
                    return getResources().getString(R.string.new_movies);
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
                    return new NewMovieFragment();
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    //return new FragmentAyam();
                case 2:
                    //return new FragmentKueBasah();
                case 3:
                    return new RestrictedFragment();
                default:
                    //return new FragmentMasakan();
                    return new NewMovieFragment();
            }
        }
    }

}
