/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.ibod.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import net.winnerawan.ibod.R;
import net.winnerawan.ibod.activity.WatchMovieActivity;
import net.winnerawan.ibod.app.AppController;
import net.winnerawan.ibod.helper.MyRequest;
import net.winnerawan.ibod.model.Movie;
import net.winnerawan.ibod.service.ApiService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by winnerawan on 12/11/16.
 */

public class WatchMovieFragment extends Fragment {

    private static final String TAG = WatchMovieFragment.class.getSimpleName();

    private Dialog pDialog;
    private ProgressBar pBar;
    private NetworkImageView imageView;
    private NetworkImageView imageView2;
    private TextView txtTitle;
    private TextView txtTitle2;
    private TextView txtRating;
    private TextView txtTime;
    private RelativeLayout rytPlay;
    private String LINK;
    private String TITLE;
    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();



    public WatchMovieFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_detail_movie, container, false);
        initLayout(view);
        Bundle bundle = getActivity().getIntent().getExtras();
        final int id = bundle.getInt("id");
        // set the ad unit ID


        getMovie(id);

        rytPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(), WatchMovieActivity.class);
                i.putExtra("movie_id", id);
                i.putExtra("link", LINK);
                i.putExtra("title", TITLE);
                startActivity(i);
            }
        });
        return view;
    }

    public void getMovie(int id) {
        MyRequest request = new MyRequest();
        ApiService api = request.RequestMovie().create(ApiService.class);
        api.getMovie(id, new Callback<Movie>() {
            @Override
            public void success(Movie movie, Response response) {
                pBar.setVisibility(View.GONE);
                imageView.setImageUrl(movie.getImage(), imageLoader);
                txtTime.setText(movie.getDuration());
                txtTitle.setText(movie.getTitle());
                txtRating.setText(movie.getImdbRating().toString());
                txtTitle2.setText(movie.getTitle());
                imageView2.setImageUrl(movie.getImage(), imageLoader);
                TITLE = movie.getTitle();
                LINK = movie.getLink480();
                /*
                try {
                    MediaController mediaController = new MediaController(getActivity());
                    mediaController.setAnchorView(videoView);
                    Uri mov = Uri.parse(movie.getLink480().replaceAll("&amp;", "&"));
                    videoView.setMediaController(mediaController);
                    videoView.setVideoURI(mov);
                    videoView.start();
                }catch (Exception e) {
                    Log.e(TAG, "eerr" +e.getMessage());
                    e.printStackTrace();
                }
                */
                Log.e(TAG, movie.getTitle());
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        //startActivity(new Intent(this.getActivity().getApplicationContext(), WatchMovieActivity.class));
        //this.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        /*
        this.getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
         */
    }

    private void initLayout(View view) {
        this.imageView = (NetworkImageView) view.findViewById(R.id.img_content);
        this.txtTitle = (TextView) view.findViewById(R.id.txt_title);
        this.txtRating = (TextView) view.findViewById(R.id.txt_rating);
        this.txtTime = (TextView) view.findViewById(R.id.txt_episode);
        this.pBar = (ProgressBar) view.findViewById(R.id.loading);
        this.txtTitle2 = (TextView) view.findViewById(R.id.txt_title2);
        this.imageView2 = (NetworkImageView) view.findViewById(R.id.img_content2);
        this.rytPlay = (RelativeLayout) view.findViewById(R.id.ryt_play);
    }
}
