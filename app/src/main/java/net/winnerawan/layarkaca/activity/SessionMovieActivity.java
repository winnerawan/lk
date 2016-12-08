package net.winnerawan.layarkaca.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import net.winnerawan.layarkaca.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SessionMovieActivity extends AppCompatActivity {

    @Bind(R.id.f_btn_feeds) FrameLayout f_btn_feeds;
    @Bind(R.id.f_btn_explorer) FrameLayout f_btn_explorer;
    @Bind(R.id.f_btn_more) FrameLayout f_btn_more;
    @Bind(R.id.f_btn_search) FrameLayout f_btn_search;
    @Bind(R.id.btn_explorer) ImageView btn_explorer;
    @Bind(R.id.btn_feeds) ImageView btn_feeds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.e("TAG", "SESSION_MOVIE_ACTIVITY");
        ButterKnife.bind(this);
        btn_explorer.setImageResource(R.mipmap.ico_explorer_active);
        btn_feeds.setImageResource(R.mipmap.ico_feeds);
        overridePendingTransition(R.anim.anim_push_left, R.anim.anim_pop_left);

        f_btn_feeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToHomeActivity();
            }
        });
        f_btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SessionMovieActivity.this, SearchActivity.class);
                startActivity(i);
            }
        });

        f_btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SessionMovieActivity.this, MoreActivity.class);
                startActivity(i);
            }
        });

    }

    private void goToHomeActivity() {
        startActivity(new Intent(this, HomeActivity.class));
        overridePendingTransition(R.anim.anim_push_right, R.anim.anim_pop_right);
    }

    @Override
    public void onBackPressed() {
        super.finish();
    }
}
