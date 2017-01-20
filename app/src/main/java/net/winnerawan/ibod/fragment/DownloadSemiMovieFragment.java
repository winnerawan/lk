/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.ibod.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import net.winnerawan.ibod.R;
import net.winnerawan.ibod.app.AppController;
import net.winnerawan.ibod.helper.MyRequest;
import net.winnerawan.ibod.model.Movie;
import net.winnerawan.ibod.service.ApiService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by winnerawan on 12/13/16.
 */

public class DownloadSemiMovieFragment extends Fragment {

    private static final String TAG = DownloadSemiMovieFragment.class.getSimpleName();

    private NetworkImageView imageView;
    private TextView txtTitle;
    private TextView txtRating;
    private Button bDownload;
    private ProgressBar pBar;
    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public DownloadSemiMovieFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_program_review, container, false);
        initLayout(view);
        Bundle bundle = getActivity().getIntent().getExtras();
        int id = bundle.getInt("id");
        getDownloadLink(id);
        return view;
    }

    private void initLayout(View view) {
        this.txtTitle = (TextView) view.findViewById(R.id.txt_title);
        this.txtRating = (TextView) view.findViewById(R.id.txt_rating);
        this.imageView = (NetworkImageView) view.findViewById(R.id.img_content);
        this.bDownload = (Button) view.findViewById(R.id.btn_review);
        this.pBar = (ProgressBar) view.findViewById(R.id.loading);
    }

    private void getDownloadLink(int id) {
        MyRequest request = new MyRequest();
        ApiService api = request.RequestMovie().create(ApiService.class);
        api.getSemiMovie(id, new Callback<Movie>() {
            @Override
            public void success(final Movie movie, Response response) {
                pBar.setVisibility(View.GONE);
                txtRating.setText(movie.getImdbRating().toString());
                txtTitle.setText(movie.getTitle());
                imageView.setImageUrl(movie.getImage(), imageLoader);

                bDownload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(movie.getLink720()));
                        startActivity(i);
                    }
                });

            }

            @Override
            public void failure(RetrofitError error) {
                pBar.setVisibility(View.GONE);
                error.printStackTrace();
            }
        });
    }
}
