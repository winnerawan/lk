package net.winnerawan.layarkaca.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import net.winnerawan.layarkaca.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {

    @Bind(R.id.img_close) ImageView img_close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.anim_pop_down, R.anim.anim_push_down);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchActivity.this.finishAction();
            }
        });
    }

    private void finishAction() {
        finish();
        overridePendingTransition(R.anim.anim_pop_up, R.anim.anim_push_up);
    }

    public void onBackPressed() {
        finishAction();
    }
}
