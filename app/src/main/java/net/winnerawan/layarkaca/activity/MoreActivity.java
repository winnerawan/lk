package net.winnerawan.layarkaca.activity;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import net.winnerawan.layarkaca.R;
import net.winnerawan.layarkaca.helper.SQLiteHandler;
import net.winnerawan.layarkaca.helper.SessionManager;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MoreActivity extends AppCompatActivity {

    @Bind(R.id.img_close) ImageView img_close;
    @Bind(R.id.txt_my_profile) TextView txt_my_profile;
    @Bind(R.id.txt_live_tv) TextView txt_live_tv;
    @Bind(R.id.txt_term_condition) TextView txt_term_condition;
    @Bind(R.id.txt_privacy_policy) TextView txt_privacy_policy;
    @Bind(R.id.txt_sign_out) TextView txt_sign_out;

    SQLiteHandler db;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        ButterKnife.bind(this);

        session = new SessionManager(getApplicationContext());
        db = new SQLiteHandler(getApplicationContext());

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MoreActivity.this.finishAction();
            }
        });

        txt_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        txt_privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPrivacyPolicyActivity();
            }
        });

        txt_my_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProfileActivity();
            }
        });

        txt_term_condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToTermConditionActivity();
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

    private void signOut() {
        LoginManager.getInstance().logOut();
        FirebaseAuth.getInstance().signOut();
        ActivityCompat.finishAffinity(this);
        session.setLogin(false);
        db.deleteUsers();
        startActivity(new Intent(this, SplashActivity.class));
        finish();
    }

    private void goToPrivacyPolicyActivity() {
        startActivity(new Intent(this, PrivacyPolicyActivity.class));
    }

    private void goToProfileActivity() {
        startActivity(new Intent(this, MyProfileActivity.class));
    }

    private void goToTermConditionActivity()
    {
        startActivity(new Intent(this, TermConditionActivity.class));
    }
}

