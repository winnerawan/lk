/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.wonderscreen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import net.winnerawan.wonderscreen.R;
import net.winnerawan.wonderscreen.activity.AnimeListActivity;
import net.winnerawan.wonderscreen.activity.SerialMovieActivity;
import net.winnerawan.wonderscreen.adapter.SerialAdapter;
import net.winnerawan.wonderscreen.helper.ItemClickSupport;
import net.winnerawan.wonderscreen.helper.MyRequest;
import net.winnerawan.wonderscreen.model.Serial;
import net.winnerawan.wonderscreen.response.SerialResponse;
import net.winnerawan.wonderscreen.service.ApiService;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by winnerawan on 12/15/16.
 */

public class AnimeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = AnimeFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private ProgressBar pBar;
    private SwipeRefreshLayout swipeLayout;
    private List<Serial> serials;

    public AnimeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().overridePendingTransition(R.anim.anim_push_left, R.anim.anim_pop_left);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_home);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        recyclerView.setLayoutManager(layoutManager);
        swipeLayout.setColorSchemeResources(R.color.colorAccent);
        pBar = (ProgressBar) view.findViewById(R.id.loading);
        pBar.setVisibility(View.GONE);
        swipeLayout.setOnRefreshListener(this);
        getListAnime();

        return view;
    }
    private void getListAnime() {
        MyRequest request = new MyRequest();
        ApiService api = request.RequestMovie().create(ApiService.class);
        api.getListAnime(new Callback<SerialResponse>() {
            @Override
            public void success(SerialResponse serialResponse, Response response) {
                pBar.setVisibility(View.GONE);
                swipeLayout.setRefreshing(false);
                boolean err = serialResponse.getError();
                if (!err) {
                    serials = serialResponse.getSerial();
                    recyclerView.setAdapter(new SerialAdapter(serials, R.layout.adapter_home_conten, getActivity().getApplicationContext()));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                pBar.setVisibility(View.GONE);
                swipeLayout.setRefreshing(false);
            }
        });

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Serial s = serials.get(position);
                Intent i = new Intent(getActivity().getApplicationContext(), AnimeListActivity.class);
                i.putExtra("id", s.getId());
                i.putExtra("title", s.getTitle());
                i.putExtra("image", s.getImage());
                startActivity(i);
            }
        });
    }

    @Override
    public void onRefresh() {
        pBar.setVisibility(View.VISIBLE);
        getListAnime();
    }
}
