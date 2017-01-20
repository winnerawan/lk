/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.ibod.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import net.winnerawan.ibod.R;
import net.winnerawan.ibod.app.AppController;
import net.winnerawan.ibod.model.Episode;

import java.util.List;

/**
 * Created by winnerawan on 12/10/16.
 */

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.MovieViewHolder> {

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    public List<Episode> episodes;
    private int rowLayout;
    private Context context;

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        LinearLayout episodeLayout;
        TextView txtTitle;
        TextView txtEpisode;
        NetworkImageView imageView;
        ImageView dlImg;

            public MovieViewHolder(View v) {
                super(v);
                episodeLayout = (LinearLayout) v.findViewById(R.id.swipe);
                txtTitle = (TextView) v.findViewById(R.id.txt_title);
                txtEpisode = (TextView) v.findViewById(R.id.txt_episode);
                imageView = (NetworkImageView) v.findViewById(R.id.img_content);
                dlImg = (ImageView) v.findViewById(R.id.img_add_to_my_list);

            }

        }

    public EpisodeAdapter(List<Episode> episodes, int rowLayout, Context context) {
            this.episodes=episodes;
            this.rowLayout=rowLayout;
            this.context=context;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, final int position) {
        holder.imageView.setImageUrl(episodes.get(position).getThumbnail(), imageLoader);
        holder.txtTitle.setText(episodes.get(position).getSubTitle());
        holder.txtEpisode.setText("Episode "+episodes.get(position).getEpisode());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        holder.episodeLayout.setLayoutParams(params);
        holder.dlImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(episodes.get(position).getLink()));
                view.getContext().startActivity(i);
                //Toast.makeText("Toast!!", )
            }
        });
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    public void setFilter(List<Episode> serialsList) {
        serialsList.addAll(episodes);
        notifyDataSetChanged();
    }

    public Object getItem(int location) {
        return episodes.get(location);
    }
}
