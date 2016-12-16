/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.layarkaca.adapter;

/**
 * Created by winnerawan on 12/16/16.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import net.winnerawan.layarkaca.R;
import net.winnerawan.layarkaca.app.AppController;
import net.winnerawan.layarkaca.model.Movie;

import java.util.List;

public class RecyclerViewAdopter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private List<Movie> mList;
    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public RecyclerViewAdopter(Context mContext, List<Movie> mList) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        TextView txtSubTitle;
        TextView txtQuality;
        NetworkImageView imageView;
        public MyViewHolder(View view) {
            super(view);
            txtTitle = (TextView) view.findViewById(R.id.txt_title);
            txtSubTitle = (TextView) view.findViewById(R.id.txt_sub_title);
            txtQuality = (TextView) view.findViewById(R.id.txt_episode);
            imageView = (NetworkImageView) view.findViewById(R.id.img_content);
        }
    }

    public static class ViewHolderAdMob extends RecyclerView.ViewHolder {
        public AdView mAdView;
        public ViewHolderAdMob(View view) {
            super(view);
            mAdView = (AdView) view.findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();
            mAdView.loadAd(adRequest);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch(viewType){
            case 1:{
                View v = inflater.inflate(R.layout.adapter_home_conten, parent, false);
                viewHolder = new MyViewHolder(v);
                break;
            }
            case 2:{
                View v = inflater.inflate(R.layout.ads_banner, parent, false);
                viewHolder = new ViewHolderAdMob(v);
                break;
            }
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        Movie model = mList.get(holder.getAdapterPosition());

        switch(holder.getItemViewType()){
            case 1:{
                MyViewHolder viewHolder = (MyViewHolder) holder;
                viewHolder.txtTitle.setText(model.getTitle());
                viewHolder.imageView.setImageUrl(model.getImage(), imageLoader);
                viewHolder.txtQuality.setText(model.getQuality());
                viewHolder.txtSubTitle.setText(model.getGenre());
                break;
            }
            case 2:{
                break;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}