/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.layarkaca.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import net.winnerawan.layarkaca.R;
import net.winnerawan.layarkaca.activity.DetailMovieActivity;
import net.winnerawan.layarkaca.adapter.MovieAdapter;
import net.winnerawan.layarkaca.adapter.RV_Adapter;
import net.winnerawan.layarkaca.helper.ItemClickSupport;
import net.winnerawan.layarkaca.helper.MyRequest;
import net.winnerawan.layarkaca.model.Movie;
import net.winnerawan.layarkaca.response.MovieResponse;
import net.winnerawan.layarkaca.service.ApiService;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by winnerawan on 12/12/16.
 */

public class PopularMovieFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private static final String TAG = PopularMovieFragment.class.getSimpleName();

    private ProgressBar pBar;
    private List<Movie> movies;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeLayout;
    private RV_Adapter adapter;

    public PopularMovieFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initLayout(view);
        swipeLayout.setColorSchemeResources(R.color.material_green);
        swipeLayout.setOnRefreshListener(this);
        getPopularMovies();
        //adapter = new MovieAdapter(movies, R.layout.adapter_home_conten, getActivity().getApplicationContext());
        return view;
    }

    private void initLayout(View view) {
        this.recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_home);
        this.swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        this.pBar = (ProgressBar) view.findViewById(R.id.loading);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        this.recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onRefresh() {
        pBar.setVisibility(View.VISIBLE);
        getPopularMovies();
    }

    private void getPopularMovies() {
        MyRequest request = new MyRequest();
        ApiService api = request.RequestMovie().create(ApiService.class);
        api.getPopularMovies(new Callback<MovieResponse>() {
            @Override
            public void success(MovieResponse movieResponse, Response response) {
                pBar.setVisibility(View.GONE);
                swipeLayout.setRefreshing(false);
                boolean error = movieResponse.getError();
                if (!error) {
                    movies = movieResponse.getMovies();
                    //adapter = new MovieAdapter(movies, R.layout.adapter_home_conten, getActivity().getApplicationContext());
                    //adapter.notifyDataSetChanged();
                    int listSize =movies.size();
                    int ITEM = 0;
                    int NATIVE_AD = 1;
                    int[] viewTypes = new int[listSize];
                    for (int i = 0; i < listSize; i++) {
                        //movies.add(new Movie());
                        //insert native ads once in five items
                        if (i > 1 && i % 3 == 0) {
                            viewTypes[i] = NATIVE_AD;
                        } else {
                            viewTypes[i] = ITEM;
                        }
                    }
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    recyclerView.setLayoutParams(params);
                    adapter = new RV_Adapter(movies, viewTypes);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    //adapter.notifyDataSetChanged();
                    //recyclerView.setAdapter(adapter(movies, R.layout.adapter_home_conten, getActivity().getApplicationContext()));
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), DetailMovieActivity.class);
                i.putExtra("id", movies.get(position).getId());
                i.putExtra("title", movies.get(position).getTitle());
                i.putExtra("image", movies.get(position).getImage());
                startActivity(i);
            }
        });
    }
}
