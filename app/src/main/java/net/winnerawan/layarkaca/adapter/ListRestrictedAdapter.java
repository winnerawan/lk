/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.layarkaca.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import net.winnerawan.layarkaca.R;
import net.winnerawan.layarkaca.app.AppController;
import net.winnerawan.layarkaca.fragment.NewMovieFragment;
import net.winnerawan.layarkaca.fragment.RestrictedFragment;
import net.winnerawan.layarkaca.helper.SQLiteHandler;
import net.winnerawan.layarkaca.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by winnerawan on 12/9/16.
 */

public class ListRestrictedAdapter extends BaseAdapter {

    private SQLiteHandler db;
    private String AGE;
    RestrictedFragment fragment;
    private LayoutInflater inflater;
    List<Movie> listMovie = new ArrayList<Movie>();
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public ListRestrictedAdapter(RestrictedFragment fragment, List<Movie> listMovie) {
        this.fragment = fragment;
        this.listMovie = listMovie;
    }

    @Override
    public int getCount() {
        return listMovie.size();
    }

    @Override
    public Object getItem(int location) {
        return listMovie.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) fragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null)
            view = inflater.inflate(R.layout.adapter_home_conten, null);
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();


        NetworkImageView imageView = (NetworkImageView) view.findViewById(R.id.img_content);
        TextView txtTitle = (TextView) view.findViewById(R.id.txt_title);
        TextView txtSubTitle = (TextView) view.findViewById(R.id.txt_sub_title);
        TextView txtQuality = (TextView) view.findViewById(R.id.txt_episode);
        FrameLayout qualityBG = (FrameLayout) view.findViewById(R.id.lyt_episode);

        Movie movie = listMovie.get(position);
        String q = listMovie.get(position).getQuality();
        if (q.equals("HD")) {
            qualityBG.setBackgroundResource(R.drawable.quality_hd);
        } else if (q.equals("CAM")) {
            qualityBG.setBackgroundResource(R.drawable.quality_cam);
        } else if (q.equals("SD")) {
            qualityBG.setBackgroundResource(R.drawable.quality_sd);
        }
        imageView.setImageUrl(movie.getImage(), imageLoader);
        txtTitle.setText(movie.getTitle());
        txtSubTitle.setText(movie.getGenre());
        txtQuality.setText(movie.getQuality());

        return view;
    }
}