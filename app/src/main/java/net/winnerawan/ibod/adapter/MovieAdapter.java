/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.ibod.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import net.winnerawan.ibod.R;
import net.winnerawan.ibod.app.AppController;
import net.winnerawan.ibod.model.Movie;

import java.util.List;
/**
 * Created by winnerawan on 12/10/16.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    public List<Movie> movies;
    private int rowLayout;
    private Context context;
    int AD_TYPE = 0;
    int CONTENT_TYPE = 1;

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout movieLayout;
        TextView txtTitle;
        TextView txtSubTitle;
        TextView txtQuality;
        NetworkImageView imageView;

            public MovieViewHolder(View v) {
                super(v);
                    movieLayout = (RelativeLayout) v.findViewById(R.id.lyt_container_home_adapter);
                    txtTitle = (TextView) v.findViewById(R.id.txt_title);
                    txtSubTitle = (TextView) v.findViewById(R.id.txt_sub_title);
                    txtQuality = (TextView) v.findViewById(R.id.txt_episode);
                    imageView = (NetworkImageView) v.findViewById(R.id.img_content);

            }
        }

    public MovieAdapter(List<Movie> movies, int rowLayout, Context context) {
            this.movies=movies;
            this.rowLayout=rowLayout;
            this.context=context;

    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
        //return view;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

            holder.imageView.setImageUrl(movies.get(position).getImage(), imageLoader);
            holder.txtTitle.setText(movies.get(position).getTitle());
            holder.txtSubTitle.setText(movies.get(position).getGenre());
            holder.txtQuality.setText(movies.get(position).getQuality());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.movieLayout.setLayoutParams(params);


    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setFilter(List<Movie> moviesList) {
        moviesList.addAll(movies);
        notifyDataSetChanged();
    }

    public Object getItem(int location) {
        return movies.get(location);
    }

}
