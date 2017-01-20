/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.ibod.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;

import net.winnerawan.ibod.R;
import net.winnerawan.ibod.app.AppController;
import net.winnerawan.ibod.helper.SQLiteHandler;
import net.winnerawan.ibod.model.Content;
import net.winnerawan.ibod.model.Movie;
import net.winnerawan.ibod.model.Video;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by winnerawan on 12/16/16.
 */

public class RV_Adapter extends RecyclerView.Adapter<ViewHolder> {

    private static final int ITEM = 0;
    private static final int NATIVE_AD = 1;

    SQLiteHandler db;
    int[] viewTypes;
    String AGE;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    List<Movie> list = Collections.emptyList();
    List<Content> contents = Collections.emptyList();
    List<Video> videos = Collections.emptyList();

    public RV_Adapter(List<Video> videos, int[] viewTypes) {
        this.videos = videos;
        this.viewTypes = viewTypes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        db = new SQLiteHandler(parent.getContext().getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        AGE = user.get("age");
        View v;
        if (viewType == ITEM) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_home_conten, parent, false);
            ViewHolder holder = new ItemViewHolder(v);
            return holder;
        } else if (viewType == NATIVE_AD) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.re_ads, parent, false);
            ViewHolder holder = new AdViewHolder(v);
            return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        if (viewHolder.getItemViewType() == ITEM) {
            ItemViewHolder holder = (ItemViewHolder) viewHolder;
            //populate the RecyclerView
            //holder.title.setText(list.get(position).getTitle());
            //holder.description.setText(list.get(position).getDescription());
            holder.txtTitle.setText(videos.get(position).getTitle());
            holder.imageView.setImageUrl(videos.get(position).getUrlImage(), imageLoader);
            holder.txtQuality.setText(videos.get(position).getSeries());
            holder.txtSubTitle.setText(videos.get(position).getCreateAt());
        } else if (viewHolder.getItemViewType() == NATIVE_AD) {
            AdViewHolder holder = (AdViewHolder) viewHolder;

            //Load the Ad
            AdRequest request = new AdRequest.Builder()
                    .build();
            holder.adView.loadAd(request);
        }
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemViewType(int position) {
        return viewTypes[position];
    }
}

class ViewHolder extends RecyclerView.ViewHolder {
    public ViewHolder(View v) {
        super(v);
    }
}

class ItemViewHolder extends ViewHolder {

    TextView txtTitle;
    TextView txtSubTitle;
    TextView txtQuality;
    NetworkImageView imageView;

    ItemViewHolder(View itemView) {
        super(itemView);
        txtTitle = (TextView) itemView.findViewById(R.id.txt_title);
        txtSubTitle = (TextView) itemView.findViewById(R.id.txt_sub_title);
        txtQuality = (TextView) itemView.findViewById(R.id.txt_episode);
        imageView = (NetworkImageView) itemView.findViewById(R.id.img_content);
    }
}

class AdViewHolder extends ViewHolder {
    NativeExpressAdView adView;

    public AdViewHolder(View v) {
        super(v);
        adView = (NativeExpressAdView) v.findViewById(R.id.nativeAdsView);
    }
}
