/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.ibod.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import net.winnerawan.ibod.R;
import net.winnerawan.ibod.adapter.LiveTvAdapter;
import net.winnerawan.ibod.helper.ItemClickSupport;
import net.winnerawan.ibod.helper.MyRequest;
import net.winnerawan.ibod.model.TV;
import net.winnerawan.ibod.response.TVResponse;
import net.winnerawan.ibod.service.ApiService;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TVStreamActivity extends AppCompatActivity {

    private static final String TAG = TVStreamActivity.class.getSimpleName();

    private List<TV> tvs;

    @Bind(R.id.recyclerview_live_tv) RecyclerView recyclerView;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.left_btn_toolbar) ImageView back;
    @Bind(R.id.right_btn_toolbar) ImageView back2;
    @Bind(R.id.txt_title) TextView title_tb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_tv);
        ButterKnife.bind(this);
        back2.setVisibility(View.GONE);
        overridePendingTransition(R.anim.anim_pop_left, R.anim.anim_push_left);
        setSupportActionBar(toolbar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //toolbar.setTitle("Live TV");
        title_tb.setText("Live TV");
        listTV();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(TVStreamActivity.this, R.anim.image_click));
                TVStreamActivity.this.finishAction();
            }
        });
    }

    private void finishAction() {
        finish();
        overridePendingTransition(R.anim.anim_pop_right, R.anim.anim_push_right);
    }

    @Override
    public void onBackPressed() {
        finishAction();
    }

    private void listTV() {
        MyRequest request = new MyRequest();
        ApiService api = request.RequestMovie().create(ApiService.class);
        api.getListTV(new Callback<TVResponse>() {
            @Override
            public void success(TVResponse tvResponse, Response response) {
                boolean err = tvResponse.getError();
                if (!err) {
                    tvs = tvResponse.getTv();
                    recyclerView.setAdapter(new LiveTvAdapter(tvs,R.layout.adapter_live_tv, getApplicationContext()));
                    Log.e(TAG, "ssc ->"+tvResponse.toString());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "error -> "+error.getResponse()+" - "+error.getMessage());
            }
        });

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                TV tv = tvs.get(position);
                Intent i = new Intent(TVStreamActivity.this, StreamActivity.class);
                i.putExtra("link", tv.getLink());
                startActivity(i);
            }
        });
    }
}
