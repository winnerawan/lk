/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.ibod.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import net.winnerawan.ibod.R;
import net.winnerawan.ibod.app.AppController;
import net.winnerawan.ibod.helper.MyRequest;
import net.winnerawan.ibod.model.Movie;
import net.winnerawan.ibod.service.ApiService;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by winnerawan on 12/10/16.
 */

public class AboutSemiMovieFragment extends Fragment {

    private static final String TAG = AboutSemiMovieFragment.class.getSimpleName();
    private Movie movie;
    private NetworkImageView imageView;
    private TextView txtTitle;
    private TextView txtSynopsis;
    private TextView txtYear;
    private TextView txtRating;
    private TextView txtDesc;
    ProgressBar pBar;
    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public AboutSemiMovieFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_program_about, container, false);
        initLayout(view);
        Bundle bundle = getActivity().getIntent().getExtras();
        int id = bundle.getInt("id");
        getMovie(id);
        Log.e(TAG, "movid : "+id);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void getMovie(int id) {
        MyRequest request = new MyRequest();
        ApiService api = request.RequestMovie().create(ApiService.class);
        api.getSemiMovie(id, new Callback<Movie>() {
            @Override
            public void success(Movie movie, Response response) {
                pBar.setVisibility(View.GONE);
                txtTitle.setText(movie.getTitle());
                txtRating.setText(String.valueOf(movie.getImdbRating()));
                txtSynopsis.setText("Synopsis");
                txtDesc.setText(movie.getSynopsis());
                String humanTime = movie.getRelease();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date convertedDate = new Date();
                try {
                    convertedDate = dateFormat.parse(humanTime);
                }catch (ParseException e) {
                    e.printStackTrace();
                }
                PrettyTime p = new PrettyTime();
                String time = p.format(convertedDate);
                txtYear.setText(time);
                imageView.setImageUrl(movie.getImage(), imageLoader);
                Log.e(TAG, movie.getTitle());
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private void initLayout(View view) {
        this.imageView = (NetworkImageView) view.findViewById(R.id.img_content);
        this.txtTitle = (TextView) view.findViewById(R.id.txt_title);
        this.txtRating = (TextView) view.findViewById(R.id.txt_rating);
        this.txtSynopsis = (TextView) view.findViewById(R.id.txt_synopsis);
        this.txtYear = (TextView) view.findViewById(R.id.txt_year);
        this.txtDesc = (TextView) view.findViewById(R.id.txt_description);
        this.pBar = (ProgressBar) view.findViewById(R.id.loading);
    }
}

