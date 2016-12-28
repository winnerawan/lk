/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.wonderscreen.fragment;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import net.winnerawan.wonderscreen.R;
import net.winnerawan.wonderscreen.app.AppConfig;
import net.winnerawan.wonderscreen.app.AppController;
import net.winnerawan.wonderscreen.helper.MyRequest;
import net.winnerawan.wonderscreen.model.Movie;
import net.winnerawan.wonderscreen.service.ApiService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by winnerawan on 12/11/16.
 */

public class TrailerFragment extends Fragment {

    private static final String TAG = TrailerFragment.class.getSimpleName();

    private Dialog pDialog;
    private NetworkImageView imageView;
    ProgressBar pBar;
    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private TextView txtRating;
    private TextView txtTitle;
    private YouTubePlayerSupportFragment youTubeView;
    private static final int RECOVERY_REQUEST = 1;

    public TrailerFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_program_trailer, container, false);
        initLayout(view);
        Bundle bundle = getActivity().getIntent().getExtras();
        int id = bundle.getInt("id");
        getMovie(id);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    private void initLayout(View view) {
        this.imageView = (NetworkImageView) view.findViewById(R.id.img_content);
        this.txtTitle = (TextView) view.findViewById(R.id.txt_title);
        this.txtRating = (TextView) view.findViewById(R.id.txt_rating);
        this.pBar = (ProgressBar) view.findViewById(R.id.loading);
        this.youTubeView =YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.trailer_view, youTubeView).commit();
    }

    public void getMovie(int id) {
        MyRequest request = new MyRequest();
        ApiService api = request.RequestMovie().create(ApiService.class);
        api.getMovie(id, new Callback<Movie>() {
            @Override
            public void success(final Movie movie, Response response) {
                pBar.setVisibility(View.GONE);
                txtTitle.setText(movie.getTitle());
                txtRating.setText(String.valueOf(movie.getImdbRating()));
                imageView.setImageUrl(movie.getImage(), imageLoader);

                youTubeView.initialize(AppConfig.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                        if (!b) {
                            youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                            youTubePlayer.loadVideo(movie.getTrailer());
                        }
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
                Log.e(TAG, "error"+movie.getTitle());
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

}
