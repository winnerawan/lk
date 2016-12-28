/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.wonderscreen.adapter;

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

import net.winnerawan.wonderscreen.R;
import net.winnerawan.wonderscreen.app.AppController;
import net.winnerawan.wonderscreen.model.Serial;

import java.util.List;

/**
 * Created by winnerawan on 12/10/16.
 */

public class SerialAdapter extends RecyclerView.Adapter<SerialAdapter.MovieViewHolder> {

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    public List<Serial> serials;
    private int rowLayout;
    private Context context;

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout movieLayout;
        TextView txtTitle;
        TextView txtSubTitle;
        TextView txtSeason;
        TextView txtQuality;
        NetworkImageView imageView;

            public MovieViewHolder(View v) {
                super(v);
                movieLayout = (RelativeLayout) v.findViewById(R.id.lyt_container_home_adapter);
                txtTitle = (TextView) v.findViewById(R.id.txt_title);
                txtSubTitle = (TextView) v.findViewById(R.id.txt_sub_title);
                txtSeason = (TextView) v.findViewById(R.id.txt_session);
                txtQuality = (TextView) v.findViewById(R.id.txt_episode);
                imageView = (NetworkImageView) v.findViewById(R.id.img_content);
            }
        }

    public SerialAdapter(List<Serial> serials, int rowLayout, Context context) {
            this.serials=serials;
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
        holder.imageView.setImageUrl(serials.get(position).getImage(), imageLoader);
        holder.txtTitle.setText(serials.get(position).getTitle());
        holder.txtSeason.setText("Season "+serials.get(position).getSeason());
        holder.txtSubTitle.setText(serials.get(position).getGenre());
        holder.txtQuality.setText(serials.get(position).getQuality());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        holder.movieLayout.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return serials.size();
    }

    public void setFilter(List<Serial> serialsList) {
        serialsList.addAll(serials);
        notifyDataSetChanged();
    }

    public Object getItem(int location) {
        return serials.get(location);
    }
}
