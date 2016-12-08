package net.winnerawan.layarkaca.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import net.winnerawan.layarkaca.R;
import net.winnerawan.layarkaca.component.NonSwipeableViewPager;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @Bind(R.id.pagerHome) NonSwipeableViewPager pagerHome;
    @Bind(R.id.f_btn_search) FrameLayout f_btn_search;
    @Bind(R.id.f_btn_more) FrameLayout f_btn_more;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        pagerHome.setOffscreenPageLimit(3);

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
    }
}
