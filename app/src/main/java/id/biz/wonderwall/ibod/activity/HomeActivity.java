package id.biz.wonderwall.ibod.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.google.firebase.messaging.FirebaseMessaging;

import butterknife.Bind;
import butterknife.ButterKnife;
import id.biz.wonderwall.ibod.R;
import id.biz.wonderwall.ibod.adapter.MyAdapter;
import id.biz.wonderwall.ibod.app.AppConfig;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    private MyAdapter homeAdapter;

    @Bind(R.id.pagerHome)
    ViewPager pagerHome;
    @Bind(R.id.f_btn_explorer) FrameLayout f_btn_explorer;
    @Bind(R.id.f_btn_continue_watching) FrameLayout f_btn_continue_watching;
    @Bind(R.id.f_btn_search) FrameLayout f_btn_search;
    @Bind(R.id.f_btn_more) FrameLayout f_btn_more;
    @Bind(R.id.f_btn_feeds) FrameLayout f_btn_feeds;
    @Bind(R.id.btn_explorer) ImageView btn_explorer;
    @Bind(R.id.btn_feeds) ImageView btn_feeds;
    @Bind(R.id.btn_continue_watching) ImageView btn_continue_watching;
    @Bind(R.id.lyt_add_to_your_list) LinearLayout lyt_add_to_your_list;
    @Bind(R.id.img_add_to_your_list) ImageView img_add_to_your_list;
    @Bind(R.id.txt_add_to_your_list) TextView txt_add_to_your_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        initAndSetAdapter();
        //pagerHome.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager()));
        // Give the PagerSlidingTabStrip the ViewPager
        final PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(pagerHome);
        //setupViewPager(pagerHome);
        //MobileAds.initialize(getApplicationContext(), getResources().getString(R.string.admob_app_id));
/*
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
        */

        f_btn_feeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initAndSetAdapter();
                homeAdapter.notifyDataSetChanged();
                tabsStrip.notifyDataSetChanged();
                HomeActivity.this.setNonActive();
                HomeActivity.this.setCurentItemHomePager(0);
                HomeActivity.this.btn_feeds.setImageResource(R.mipmap.ico_feeds_active);
            }
        });

        f_btn_explorer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.anim_pop_left, R.anim.anim_push_left);
                //transaction.replace(R.id.fm_home, new HentaiFragment());
                transaction.commit();
                //updateAdapter();
                homeAdapter.notifyDataSetChanged();
                tabsStrip.notifyDataSetChanged();
                HomeActivity.this.setNonActive();
                HomeActivity.this.setCurentItemHomePager(0);
                HomeActivity.this.btn_explorer.setImageResource(R.mipmap.ico_explorer_active);
            }
        });

        f_btn_continue_watching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.anim_pop_left, R.anim.anim_push_left);
                //transaction.replace(R.id.fm_home, new SemiFragment());
                transaction.commit();
                //reupdateAdapter();
                homeAdapter.notifyDataSetChanged();
                tabsStrip.notifyDataSetChanged();
                HomeActivity.this.setNonActive();
                HomeActivity.this.setCurentItemHomePager(0);
                HomeActivity.this.btn_continue_watching.setImageResource(R.mipmap.ico_continue_watching_active);

                //Toast.makeText(getApplicationContext(), "RESERVED FOR FUTURE IDEAS", Toast.LENGTH_SHORT).show();

            }
        });

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

    /*
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
    */

    private void initAndSetAdapter()
    {
        this.homeAdapter = new MyAdapter(getSupportFragmentManager());
        this.pagerHome.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_pop_right));
        this.pagerHome.setAdapter(this.homeAdapter);
        this.pagerHome.setOffscreenPageLimit(3);
    }
/*
    private void updateAdapter() {
        this.secondAdapter = new SecondAdapter(getSupportFragmentManager());
        this.pagerHome.setAdapter(this.secondAdapter);
    }

    private void reupdateAdapter() {
        this.thirdAdapter = new ThirdAdapter(getSupportFragmentManager());
        this.pagerHome.setAdapter(this.thirdAdapter);
    }
    */
}
