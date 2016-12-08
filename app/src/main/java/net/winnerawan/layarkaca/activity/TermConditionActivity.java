package net.winnerawan.layarkaca.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.winnerawan.layarkaca.R;

public class TermConditionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_condition);
        overridePendingTransition(R.anim.anim_pop_left, R.anim.anim_push_left);

    }

    private void finishAction() {
        finish();
        overridePendingTransition(R.anim.anim_pop_right, R.anim.anim_push_right);
    }

    @Override
    public void onBackPressed() {
        finishAction();
    }
}
