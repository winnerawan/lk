/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.ibod.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.winnerawan.ibod.R;
import net.winnerawan.ibod.activity.SemiMovieDetailActivity;
import net.winnerawan.ibod.adapter.MovieAdapter;
import net.winnerawan.ibod.adapter.RV_Adapter;
import net.winnerawan.ibod.app.AppConfig;
import net.winnerawan.ibod.helper.ItemClickSupport;
import net.winnerawan.ibod.helper.MyRequest;
import net.winnerawan.ibod.helper.SQLiteHandler;
import net.winnerawan.ibod.model.Movie;
import net.winnerawan.ibod.response.MovieResponse;
import net.winnerawan.ibod.service.ApiService;

import java.util.HashMap;
import java.util.List;

import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * Created by winnerawan on 12/9/16.
 */

public class RestrictedFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = RestrictedFragment.class.getSimpleName();

    private SQLiteHandler db;
    private String S_AGE;
    private int AGE;
    private List<Movie> movies;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeLayout;
    private ProgressBar pBar;
    RV_Adapter adapter;

    public RestrictedFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initLayout(view);
        db = new SQLiteHandler(getActivity().getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        S_AGE = user.get("age");
        AGE = Integer.parseInt(S_AGE);
        chooseContent();
        swipeLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void getRestrictedContent() {
        MyRequest request = new MyRequest();
        ApiService api = request.RequestMovie().create(ApiService.class);
        api.getSemiMovies(new Callback<MovieResponse>() {
            @Override
            public void success(MovieResponse movieResponse, retrofit.client.Response response) {
                pBar.setVisibility(View.GONE);
                boolean error = movieResponse.getError();
                if (!error) {
                    movies = movieResponse.getMovies();
                    //recyclerView.setAdapter(new MovieAdapter(movies, R.layout.adapter_home_conten, getActivity().getApplicationContext()));
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
                    //adapter = new RV_Adapter(movies, viewTypes);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                pBar.setVisibility(View.GONE);
            }
        });
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), SemiMovieDetailActivity.class);
                i.putExtra("id", movies.get(position).getId());
                i.putExtra("title", movies.get(position).getTitle());
                i.putExtra("image", movies.get(position).getImage());
                startActivity(i);
            }
        });
    }

    private void initLayout(View v) {
        this.swipeLayout=(SwipeRefreshLayout) v.findViewById(R.id.swipe);
        this.pBar=(ProgressBar) v.findViewById(R.id.loading);
        this.recyclerView=(RecyclerView) v.findViewById(R.id.recyclerview_home);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        this.recyclerView.setLayoutManager(layoutManager);
    }

    private void NOTAUTHORIZED() {
        MyRequest request = new MyRequest();
        ApiService api = request.RequestMovie().create(ApiService.class);
        api.getSemiMovies(new Callback<MovieResponse>() {
            @Override
            public void success(MovieResponse movieResponse, retrofit.client.Response response) {
                pBar.setVisibility(View.GONE);
                boolean error = movieResponse.getError();
                if (!error) {
                    movies = movieResponse.getMovies();
                    for (int i=0; i<movies.size(); i++) {
                        String link1 = movies.get(i).getImage();
                        String title = movies.get(i).getTitle();
                        String genre = movies.get(i).getGenre();
                        link1 = link1.replace(link1, AppConfig.RESTRICTED_CONTENT);
                        title = title.replace(title, "");
                        genre = genre.replace(genre, "");
                        movies.get(i).setImage(link1);
                        movies.get(i).setPoster(link1);
                        movies.get(i).setTitle(title);
                        movies.get(i).setGenre(genre);

                    }
                    recyclerView.setAdapter(new MovieAdapter(movies, R.layout.adapter_home_conten, getActivity().getApplicationContext()));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                pBar.setVisibility(View.GONE);
                error.printStackTrace();
            }
        });

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Toast.makeText(getActivity().getApplicationContext(), "Not Authorized!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onRefresh() {
        swipeLayout.setRefreshing(false);
        chooseContent();
    }

    private void chooseContent() {
        if (AGE<=18) {
            NOTAUTHORIZED();
        } else if (AGE>18){
            getRestrictedContent();
        }
    }

}
