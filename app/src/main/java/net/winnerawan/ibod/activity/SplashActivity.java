/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.ibod.activity;

/**
 * Created by winnerawan on 12/17/16.
 */


import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import net.winnerawan.ibod.R;
import net.winnerawan.ibod.helper.SessionManager;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    public static int WAKTU_TUNGGU = 3000;

    SessionManager session;
    @Bind(R.id.btn_get_started) Button start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        session = new SessionManager(getApplicationContext());

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        if(session.isLoggedIn()) {
            ActivityCompat.finishAffinity(this);
            startActivity(new Intent(this, HomeActivity.class));
        }
    }
}