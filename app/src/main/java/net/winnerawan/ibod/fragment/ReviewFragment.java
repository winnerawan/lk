/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.ibod.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import net.winnerawan.ibod.R;
import net.winnerawan.ibod.helper.MyRequest;
import net.winnerawan.ibod.model.Movie;
import net.winnerawan.ibod.service.ApiService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by winnerawan on 12/14/16.
 */

public class ReviewFragment extends Fragment {

    private static final String TAG = ReviewFragment.class.getSimpleName();
    private WebView webreview;

    public ReviewFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rev, container, false);
        webreview = (WebView) view.findViewById(R.id.webreview);
        Bundle bundle = getActivity().getIntent().getExtras();
        int id = bundle.getInt("id");
        getReview(id);
        return view;
    }

    private void getReview(int id) {
        MyRequest request = new MyRequest();
        ApiService api = request.RequestMovie().create(ApiService.class);
        api.getMovie(id, new Callback<Movie>() {
            @Override
            public void success(Movie movie, Response response) {
                webreview.loadUrl(movie.getReview());
                //webreview.loadUrl("http://www.movgeeks.net/2016/08/suicide-squad-2016.html");
                WebSettings webSettings = webreview.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webreview.setWebViewClient(new WebViewClient());

            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }
}
