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
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import net.winnerawan.wonderscreen.R;
import net.winnerawan.wonderscreen.app.AppController;
import net.winnerawan.wonderscreen.model.TV;

import java.util.List;

/**
 * Created by winnerawan on 12/14/16.
 */

public class LiveTvAdapter extends RecyclerView.Adapter<LiveTvAdapter.LiveTvViewHolder> {

    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private int rowLayout;
    private List<TV> tvList;
    private Context context;

    public static class LiveTvViewHolder extends RecyclerView.ViewHolder {
        FrameLayout tvLayout;
        NetworkImageView imageView;

        public LiveTvViewHolder(View v) {
            super(v);
            tvLayout = (FrameLayout) v.findViewById(R.id.tv);
            imageView = (NetworkImageView) v.findViewById(R.id.img_content);
        }
    }
    public LiveTvAdapter(List<TV> tvList, int rowLayout, Context context) {
        this.tvList=tvList;
        this.rowLayout=rowLayout;
        this.context=context;
    }

    @Override
    public LiveTvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new LiveTvViewHolder(view);
    }
    @Override
    public void onBindViewHolder(LiveTvViewHolder holder, int position) {
        holder.imageView.setImageUrl(tvList.get(position).getImage(), imageLoader);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        holder.tvLayout.setLayoutParams(params);
    }
    @Override
    public int getItemCount() {
        return tvList.size();
    }
}
