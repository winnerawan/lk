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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MovieViewHolder> {

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    public List<Movie> movies;
    private int rowLayout;
    private Context context;

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        FrameLayout searchLayout;
        TextView txtTitle;
        TextView txtGenre;
        TextView txtTime;
        NetworkImageView imageView;

            public MovieViewHolder(View v) {
                super(v);
                searchLayout = (FrameLayout) v.findViewById(R.id.lyt_search_adapter);
                txtTitle = (TextView) v.findViewById(R.id.txt_title);
                txtGenre = (TextView) v.findViewById(R.id.txt_episode);
                txtTime = (TextView) v.findViewById(R.id.txt_time);
                imageView = (NetworkImageView) v.findViewById(R.id.img_content);
            }
        }

    public SearchAdapter(List<Movie> movies, int rowLayout, Context context) {
            this.movies=movies;
            this.rowLayout=rowLayout;
            this.context=context;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.imageView.setImageUrl(movies.get(position).getImage(), imageLoader);
        holder.txtTitle.setText(movies.get(position).getTitle());
        holder.txtGenre.setText(movies.get(position).getGenre());
        holder.txtTime.setText(movies.get(position).getDuration());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        holder.searchLayout.setLayoutParams(params);
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

    /**
     * Filter Logic
     **/
    public void animateTo(List<Movie> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);

    }

    private void applyAndAnimateRemovals(List<Movie> newModels) {

        for (int i = movies.size() - 1; i >= 0; i--) {
            final Movie model = movies.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Movie> newModels) {

        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Movie model = newModels.get(i);
            if (!movies.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Movie> newModels) {

        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Movie model = newModels.get(toPosition);
            final int fromPosition = movies.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public Movie removeItem(int position) {
        final Movie model = movies.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, Movie model) {
        movies.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Movie model = movies.remove(fromPosition);
        movies.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }
}
