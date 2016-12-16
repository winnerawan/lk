/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.layarkaca.fragment;

import android.app.Dialog;
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

import net.winnerawan.layarkaca.R;
import net.winnerawan.layarkaca.app.AppController;
import net.winnerawan.layarkaca.helper.MyRequest;
import net.winnerawan.layarkaca.model.Movie;
import net.winnerawan.layarkaca.model.Serial;
import net.winnerawan.layarkaca.service.ApiService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by winnerawan on 12/10/16.
 */

public class AboutAnimeFragment extends Fragment {

    private static final String TAG = AboutAnimeFragment.class.getSimpleName();
    private Dialog pDialog;
    private Movie movie;
    private NetworkImageView imageView;
    private TextView txtTitle;
    private TextView txtSynopsis;
    private TextView txtYear;
    private TextView txtRating;
    private TextView txtDesc;
    ProgressBar pBar;
    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public AboutAnimeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_program_about, container, false);
        initLayout(view);
        Bundle bundle = getActivity().getIntent().getExtras();
        int id = bundle.getInt("id");
        getSerial(id);
        Log.e(TAG, "anime : "+id);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //TODO --> change to get anime by id
    private void getSerial(int id) {
        MyRequest req = new MyRequest();
        ApiService api = req.RequestMovie().create(ApiService.class);
        api.getSerial(id, new Callback<Serial>() {
            @Override
            public void success(Serial serial, Response response) {
                pBar.setVisibility(View.GONE);
                imageView.setImageUrl(serial.getImage(), imageLoader);
                txtTitle.setText(serial.getTitle());
                txtSynopsis.setText("Synopsis");
                txtDesc.setText(serial.getSynopsis());
                txtRating.setText(String.valueOf(serial.getImdbRating()));
            }

            @Override
            public void failure(RetrofitError error) {
                pBar.setVisibility(View.GONE);
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

