/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.wonderscreen.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import net.winnerawan.wonderscreen.R;
import net.winnerawan.wonderscreen.app.AppController;
import net.winnerawan.wonderscreen.model.Movie;

import java.util.List;

/**
 * Created by winnerawan on 12/16/16.
 */

public class ReAdsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    int AD_TYPE = 0;
    int CONTENT_TYPE = 1;
    private LayoutInflater inflater;
    List<Movie> movies;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private Activity mainActivity;

    public ReAdsAdapter(Context context, List<Movie> movies) {
        this.inflater = LayoutInflater.from(context);
        this.movies = movies;
        this.mainActivity = ((Activity)context);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        TextView txtSubTitle;
        TextView txtQuality;
        NetworkImageView imageView;

        public ViewHolder(View view) {
            super(view);
            if (!(this.itemView instanceof AdView)) {
                txtTitle = (TextView) view.findViewById(R.id.txt_title);
                txtSubTitle = (TextView) view.findViewById(R.id.txt_sub_title);
                txtQuality = (TextView) view.findViewById(R.id.txt_episode);
                imageView = (NetworkImageView) view.findViewById(R.id.img_content);
            }
        }
    }


    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup container, int paramInt) {
        if (paramInt == this.AD_TYPE) {
            container = new AdView(this.mainActivity);
            container = new AdView(this.mainActivity);
            //container.setAdSize(AdSize.BANNER);
            //container.setAdUnitId(this.mainActivity.getString(R.string.ad_unit_banner));
            float f = this.mainActivity.getResources().getDisplayMetrics().density;
            container.setLayoutParams(new AbsListView.LayoutParams(-1, Math.round(AdSize.BANNER.getHeight() * f)));
            //container.loadAd(new AdRequest.Builder().build());
            return new ViewHolder(container);
        }
        return new ViewHolder(this.inflater.inflate(R.layout.adapter_home_conten, container, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position % 6 != 3) {
            //holder.imageView.setImageUrl(this.movies.get(position).getImage(), imageLoader);
            //holder.

        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public int getItemViewType(int paramInt) {
        if (paramInt % 6 == 3) {
            return this.AD_TYPE;
        }
        return this.CONTENT_TYPE;
    }
}
